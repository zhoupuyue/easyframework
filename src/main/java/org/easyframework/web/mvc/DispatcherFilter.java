/**
 * 
 */
package org.easyframework.web.mvc;

import static org.easyframework.util.ParamUtil.empty;
import static org.easyframework.web.WebHelper.write;
import static org.easyframework.web.mvc.MvcHelper.forward;
import static org.easyframework.web.mvc.MvcHelper.redirect;
import static org.easyframework.web.mvc.MvcHelper.writeJson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.FrameworkException;
import org.easyframework.web.mvc.container.ControllerMappingContainer;
import org.easyframework.web.mvc.container.ExceptionResolverMappingContainer;
import org.easyframework.web.mvc.container.InterceptorMappingContainer;
import org.easyframework.web.mvc.context.ApplicationContext;
import org.easyframework.web.mvc.context.RequestContext;
import org.easyframework.web.mvc.exception.EasyConfigException;
import org.easyframework.web.mvc.exception.EasyDispatcherException;
import org.easyframework.web.mvc.exception.EasyMappingConfigException;
import org.easyframework.web.mvc.mapping.ControllerMapping;
import org.easyframework.web.mvc.mapping.ExceptionMapping;
import org.easyframework.web.mvc.mapping.InterceptorMapping;
import org.easyframework.web.mvc.mapping.ViewPathMapping;
import org.easyframework.web.mvc.parser.ControllerAnnotationParser;
import org.easyframework.web.mvc.parser.ExceptionAnnotationParser;
import org.easyframework.web.mvc.parser.InterceptorAnnotationParser;
import org.easyframework.web.mvc.parser.ValidatorAnnotationParser;
import org.easyframework.web.mvc.result.JsonResult;
import org.easyframework.web.mvc.result.Redirect;
import org.easyframework.web.mvc.result.View;

import com.alibaba.fastjson.JSONObject;


/**
 * 业务请求转发器
 * @author zhoupuyue
 * @date 2013-5-8
 */
public class DispatcherFilter implements Filter{
	
	public static final Log log = LogFactory.getLog(DispatcherFilter.class);
	
	/**
	 * 扫描内部注解基础包
	 */
	public static final String INTERNAL_SCAN_PACKAGE = "org.easyframework.web.mvc";

	/**
	 * 控制器容器
	 */
	private final MappingContainer<ControllerMapping> controllerContainer = new ControllerMappingContainer();
	
	/**
	 * 拦截器容器
	 */
	private final MappingContainer<InterceptorMapping> interceptorContainer = new InterceptorMappingContainer();
	
	/**
	 * 异常处理器容器
	 */
	private final MappingContainer<ExceptionMapping> exceptionResolverContainer = new ExceptionResolverMappingContainer();
	
	/**
	 * 上传文件处理器
	 */
	private MultipartFileResolver multipartFileResolver = new MultipartFileResolver();
	
	/**
	 * 静态资源文件路径
	 */
	private final List<String> resourcePathList = new ArrayList<String>();
	
	/**
	 * 扫描注解基础包
	 */
	private final List<String> basePackageList = new ArrayList<String>();
	
	/**
	 * 对象工厂
	 */
	private static ObjectFactory objectFactory;
	
	/**
	 * 初始化过滤器，解析注解配置信息
	 */
	public void init(FilterConfig config) throws ServletException {
		
		/**
		 * 编码
		 */
		if(!empty(config.getInitParameter("encoding"))){
			ApplicationContext.REQUEST_ENCODING = config.getInitParameter("encoding");
		}
		
		/**
		 * 初始化静态资源路径
		 */
		initResourcePath(config);
		
		/**
		 * 初始化扫描注解基础包
		 */
		initBasePackage(config);
		
		/**
		 * 初始化视图路径
		 */
		initViewPath(config);
		
		/**
		 * 初始化对象工厂
		 */
		initObjectFactory();
		
		/**
		 * 扫描注解映射信息
		 */
		build(config);
		
		/**
		 * 实例化
		 */
		instantiate();
		
	}
	
	/**
	 * 处理请求
	 * @param : req
	 * @param : resp
	 * @param : chain
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//过滤静态资源请求
		if(isResourceRequest(request)){
			chain.doFilter(req, resp);
			return;
		}
		
		//判断是否需要拦截该请求
		String path = request.getServletPath();
		
		if(path.endsWith("/") && !"/".equals(path)) path = path.substring(0, path.length() - 1);
		
		ControllerMapping mapper = controllerContainer.getMapping(path);
		
		if(mapper == null){
			chain.doFilter(req, resp);
			return;
		}
		
		//处理请求
		try{
			this.invoke(request, response);
		}catch(Exception e){
			
			log.error("服务器异常！", e);
			
			response.setContentType("text/html;charset=" + response.getCharacterEncoding());
			
			if(empty(e.getMessage())){
				write(response, "服务器异常！");
			}else{
				write(response, e.getMessage());
			}
			
		}finally{
			RequestContext.clear();
		}
		
	}
	
	/**
	 * 调用目标方法
	 * @param method
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//将request,response对象保存到请求上下文中
		RequestContext.setRequest(request);
		RequestContext.setResponse(response);
		
		String path = request.getServletPath();
		if(path.endsWith("/") && !"/".equals(path)) path = path.substring(0, path.length() - 1);
		
		//获取该请求相关的配置
		ControllerMapping mapper = controllerContainer.getMapping(path);
		if(mapper == null){
			log.error("请求URL未配置，path=" + path);
			processException(new EasyDispatcherException("请求的URL不存在！"), false);
			return;
		}
		
		//获取请求处理类
		Class<?> clazz = mapper.getClazz();
		if(clazz == null){
			log.error("找不到请求URL的处理类，path=" + path);
			processException(new EasyMappingConfigException("配置错误！"), false);
			return;
		}
		
		//是否是ajax请求
		boolean isAjax = mapper.isAjax();
		RequestContext.setIsAjax(isAjax);
		
		//获取处理请求的方法
		Method method = mapper.getMethod();
		if(method == null){
			log.error("请求的方法不存在，path=" + path + ",type=" + clazz.getCanonicalName());
			processException(new EasyDispatcherException("请求的URL不存在！"), isAjax);
			return;
		}
		
		//将表单参数保存到Map中
		Map<String,String> params = new HashMap<String,String>();
		Enumeration<String> paramNames = request.getParameterNames();
		
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement();
			String value = request.getParameter(param);
			params.put(param, value);
		}
		
		//处理上传文件
		MultipartFile multipartFile = multipartFileResolver.resolve(request, params);
		RequestContext.setMultipartFile(multipartFile);
		
		//获取方法参数
		List<Object> parameters = null;
		try{
			parameters = ParameterResolver.getParameters(method.getParameterTypes(), method.getParameterAnnotations(), params);
		}catch(Exception e){
			processException(e, isAjax);
			return;
		}
		
		//执行控制器方法前执行拦截器before方法
		List<InterceptorMapping> interceptorMapperList = interceptorContainer.getMappings(path);
		
		try{
			
			for(int i = 0 ; i < interceptorMapperList.size() ; i++ ){
				
				InterceptorMapping interceptorMapper = interceptorMapperList.get(i);
				HandlerInterceptor interceptor = (HandlerInterceptor)objectFactory.getInstance(interceptorMapper.getClazz());
				boolean b = interceptor.before(request, response);
				
				if(!b) return;
				
			}
			
		}catch(Throwable t){
			processException(t, isAjax);
			return;
		}
		
		Object result = null;
		
		//调用目标方法
		try {
			result = method.invoke(objectFactory.getInstance(clazz), parameters.toArray());
		} catch (Throwable t) {
			if(t.getCause() == null){
				processException(t, isAjax);
			}else{
				processException(t.getCause(), isAjax);
			}
			return;
		}
		
		//执行控制器方法后执行拦截器after方法
		try{
			
			for(int i = interceptorMapperList.size() - 1 ; i >= 0 ; i-- ){
				
				InterceptorMapping interceptorMapper = interceptorMapperList.get(i);
				HandlerInterceptor interceptor = (HandlerInterceptor)objectFactory.getInstance(interceptorMapper.getClazz());
				boolean b = interceptor.after(request, response);
				
				if(!b) return;
				
			}
			
		}catch(Throwable t){
			processException(t, isAjax);
			return;
		}
		
		//处理返回结果
		if(result != null){
			if(isAjax){
				if(result instanceof java.lang.String){
					writeJson((String)result);
				}else{
					writeJson(JSONObject.toJSONString(result));
				}
			}else{
				if(result instanceof View){
					forward(((View)result).getPath());
				}else if(result instanceof Redirect){
					redirect(((Redirect)result).getUrl());
				}else{
					forward((String)result);
				}
			}
		}
		
	}
	
	public void destroy() {}
	
	/**
	 * 处理异常
	 * @param t
	 * @param isAjax
	 * @throws ServletException
	 * @throws IOException
	 */
	public void processException(Throwable t, boolean isAjax) throws ServletException, IOException {
		
		Class<?> clazz = exceptionResolverContainer.getMapping(t.getClass());
		HttpServletResponse response = RequestContext.getResponse();
		
		if(clazz == null){
			
			log.error("内部异常！", t);
			
			if(isAjax){
				if(t instanceof FrameworkException){
					writeJson(JSONObject.toJSONString(new JsonResult(JsonResult.FAILED, t.getMessage())));
				}else{
					if(empty(t.getMessage())){
						writeJson(JSONObject.toJSONString(new JsonResult(JsonResult.FAILED, "服务器异常！")));
					}else{
						writeJson(JSONObject.toJSONString(new JsonResult(JsonResult.FAILED, t.getMessage())));
					}
				}
			}else{
				response.setContentType("text/html;charset=" + response.getCharacterEncoding());
				if(t instanceof FrameworkException){
					write(RequestContext.getResponse(), t.getMessage());
				}else{
					if(empty(t.getMessage())){
						write(RequestContext.getResponse(), "服务器异常！");
					}else{
						write(RequestContext.getResponse(), t.getMessage());
					}
				}
			}
			
			return;
			
		}
		
		ExceptionResolver resolver = (ExceptionResolver)objectFactory.getInstance(clazz);
		
		if(resolver == null){
			
			log.error("创建异常处理器实例异常！class=" + clazz.getCanonicalName(), t);
			
			if(isAjax){
				
				if(t instanceof FrameworkException){
					writeJson(JSONObject.toJSONString(new JsonResult(JsonResult.FAILED, t.getMessage())));
				}else{
					writeJson(JSONObject.toJSONString(new JsonResult(JsonResult.FAILED, "服务器异常！")));
				}
				
			}else{
				
				response.setContentType("text/html;charset=" + response.getCharacterEncoding());
				
				if(t instanceof FrameworkException){
					write(RequestContext.getResponse(), t.getMessage());
				}else{
					write(RequestContext.getResponse(), "服务器异常！");
				}
				
			}
			
			return;
			
		}
			
		Object o = resolver.resolve(RequestContext.getRequest(), RequestContext.getResponse(), t, isAjax);
		
		if(o == null){
			log.warn("异常处理器返回结果为null，该请求结果将被忽略！", t);
			return;
		}
		
		if(isAjax){
			if(o instanceof String){
				writeJson((String)o);
			}else{
				writeJson(JSONObject.toJSONString(o));
			}
		}else{
			if(o instanceof View){
				forward(((View)o).getPath());
			}else{
				forward((String)o);
			}
		}
		
	}
	
	/**
	 * 构建解析链，并解析xml和annotation
	 * @param config
	 */
	private void build(FilterConfig config){
		
		//控制器解析器
		AnnotationParserChain controllerParser = new ControllerAnnotationParser(this.controllerContainer);
		
		//拦截器解析器
		AnnotationParserChain interceptorParser = new InterceptorAnnotationParser(this.interceptorContainer);
		controllerParser.setNext(interceptorParser);
		
		//异常处理器解析器
		AnnotationParserChain exceptionResolverParser = new ExceptionAnnotationParser(this.exceptionResolverContainer);
		interceptorParser.setNext(exceptionResolverParser);
		
		//参数验证解析器
		AnnotationParserChain validatorParser = new ValidatorAnnotationParser();
		exceptionResolverParser.setNext(validatorParser);
		
		AnnotationScanner scanner = new ClassPathAnnotationScanner(this.basePackageList, controllerParser);
		scanner.scan();
		
		//解析完成后,对拦截器和异常处理器排序
		this.interceptorContainer.sort();
		this.exceptionResolverContainer.sort();
		ApplicationContext.sortValidators();
		
	}
	
	/**
	 * 实例化控制器,拦截器,异常处理器
	 */
	public void instantiate(){
		
		//实例化控制器
		List<ControllerMapping> controllerMappings = this.controllerContainer.getMappings();
		if(controllerMappings != null && controllerMappings.size() > 0){
			for(ControllerMapping mapping : controllerMappings){
				objectFactory.getInstance(mapping.getClass());
			}
		}
		
		//实例化异常处理器
		List<ExceptionMapping> exceptionMappings = this.exceptionResolverContainer.getMappings();
		if(exceptionMappings != null && exceptionMappings.size() > 0){
			for(ExceptionMapping mapping : exceptionMappings){
				objectFactory.getInstance(mapping.getClass());
			}
		}
		
		//实例化拦截器
		List<InterceptorMapping> interceptorMappings = this.interceptorContainer.getMappings();
		if(interceptorMappings != null && interceptorMappings.size() > 0){
			for(InterceptorMapping mapping : interceptorMappings){
				objectFactory.getInstance(mapping.getClass());
			}
		}
		
	}
	
	/**
	 * 检查是否是静态资源路径请求
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean isResourceRequest(HttpServletRequest request){
		
		String servletPath = request.getServletPath();
		if(!servletPath.endsWith("/")) servletPath = servletPath + "/";
		
		for(String path : this.resourcePathList){
			if(servletPath.startsWith(path)) return true;
		}
		
		return false;
		
	}
	
	/**
	 * 初始化扫描注解基础包
	 * @param config
	 */
	public void initBasePackage(FilterConfig config){
		
		this.basePackageList.add(INTERNAL_SCAN_PACKAGE);
		
		String basePackage = config.getInitParameter("base-package");
		
		if(empty(basePackage)){
			throw new EasyConfigException("配置错误，扫描注解包路径参数{basePackage}未设置！");
		}
		
		String[] packages = basePackage.split(",");
		
		for(String pack : packages){
			pack = pack.trim();
			this.basePackageList.add(pack);
		}
		
	}
	
	/**
	 * 初始化视图路径
	 * @param config
	 */
	public void initViewPath(FilterConfig config){
		
		String viewPrefix = config.getInitParameter("view-prefix");
		String viewSuffix = config.getInitParameter("view-suffix");
		
		if(empty(viewPrefix)){
			viewPrefix = "/WEB-INF/";
			log.warn("视图路径前缀未设置，采用默认路径：" + viewPrefix);
		}
		
		if(empty(viewSuffix)){
			viewSuffix = ".jsp";
			log.warn("视图后缀未指定，采用默认后缀：" + viewSuffix);
		}
		
		viewPrefix = viewPrefix.replace("\\", "/");
		
		if(!viewPrefix.endsWith("/")){
			viewPrefix = viewPrefix + "/";
		}
		
		ViewPathMapping mapping = new ViewPathMapping(viewPrefix, viewSuffix);
		ApplicationContext.setViewPathMapping(mapping);
		
	}
	
	/**
	 * 初始化静态资源文件路径
	 * @param config
	 */
	public void initResourcePath(FilterConfig config){
		
		String resourcePath = config.getInitParameter("resouce-path");
		
		if(!empty(resourcePath)){
			
			String[] paths = resourcePath.split(",");
			
			for(String path : paths){
				path = path.replace("\\", "/");
				path = path.trim();
				if(!path.endsWith("/")); path = path + "/";
				this.resourcePathList.add(path);
			}
			
		}
		
	}
	
	/**
	 * 初始化对象工厂
	 */
	public void initObjectFactory(){
		if(objectFactory == null) 
			objectFactory = new DefaultObjectFactory();
	}
	
	/**
	 * @param objectFactory the objectFactory to set
	 */
	public static void setObjectFactory(ObjectFactory objectFactory) {
		DispatcherFilter.objectFactory = objectFactory;
	}
	
}
