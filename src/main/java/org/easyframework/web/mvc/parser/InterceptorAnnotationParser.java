/**
 * 
 */
package org.easyframework.web.mvc.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.AnnotationParser;
import org.easyframework.web.mvc.HandlerInterceptor;
import org.easyframework.web.mvc.MappingContainer;
import org.easyframework.web.mvc.AnnotationParserChain;
import org.easyframework.web.mvc.annotation.Interceptor;
import org.easyframework.web.mvc.mapping.InterceptorMapping;

/**
 * 解析拦截器注释
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class InterceptorAnnotationParser implements AnnotationParserChain {

	public static final Log log = LogFactory.getLog(InterceptorAnnotationParser.class);
	
	/**
	 * 拦截器映射配置容器
	 */
	private MappingContainer<InterceptorMapping> container;
	
	public InterceptorAnnotationParser(MappingContainer<InterceptorMapping> container) {
		this.container = container;
	}
	
	/**
	 * 下一个解析器
	 */
	private AnnotationParser next;
	
	/**
	 * 解析拦截器注释
	 */
	@Override
	public void parseAnnotation(Class<?> clazz) {
		
		if(clazz == null) return;
		
		if(!HandlerInterceptor.class.isAssignableFrom(clazz)) {
			if(this.next != null) next.parseAnnotation(clazz);
			return;
		}
		
		Interceptor interceptor = clazz.getAnnotation(Interceptor.class);
		
		if(interceptor == null) return;
		
		String[] paths = interceptor.value();
		
		if(paths == null || paths.length == 0) return;
		
		for(int i = 0 ; i < paths.length ; i++ ){
			
			InterceptorMapping mapping = new InterceptorMapping();
			mapping.setClazz(clazz);
			String path = paths[i];
			
			if(!path.endsWith("/")) path = path + "/";
			
			mapping.setPath(path);
			mapping.setOrder(interceptor.order());
			
			container.addMapping(mapping);
			
			log.warn("扫描拦截器映射信息:path=" + mapping.getPath() + ",class=" + clazz.getCanonicalName() + ",order=" + mapping.getOrder());
			
		}
		
	}
	
	@Override
	public void setNext(AnnotationParser parser) {
		this.next = parser;
	}
	
	@Override
	public AnnotationParser getNext() {
		return next;
	}

}