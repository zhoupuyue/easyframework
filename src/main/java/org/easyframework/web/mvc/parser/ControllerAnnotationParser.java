/**
 * 
 */
package org.easyframework.web.mvc.parser;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.AnnotationParser;
import org.easyframework.web.mvc.MappingContainer;
import org.easyframework.web.mvc.AnnotationParserChain;
import org.easyframework.web.mvc.annotation.Controller;
import org.easyframework.web.mvc.annotation.Request;
import org.easyframework.web.mvc.mapping.ControllerMapping;

/**
 * 控制器注解解析器
 * @author zhoupuyue
 * @date 2013-8-26
 */
public class ControllerAnnotationParser implements AnnotationParserChain {
	
	public static final Log log = LogFactory.getLog(ControllerAnnotationParser.class);
	
	/**
	 * 控制器映射配置容器
	 */
	private MappingContainer<ControllerMapping> container;
	
	/**
	 * 下一个解析器
	 */
	private AnnotationParser next;
	
	public ControllerAnnotationParser(MappingContainer<ControllerMapping> container){
		this.container = container;
	}
	
	/**
	 * 解析控制器注解
	 * @param clazz 
	 */
	@Override
	public void parseAnnotation(Class<?> clazz) {
		
		if(clazz == null) return;
		
		Controller controller = clazz.getAnnotation(Controller.class);
		
		if(controller == null) {
			if(this.next != null) next.parseAnnotation(clazz);
			return;
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		
		for(int i = 0 ; i < methods.length ; i++){
			
			Method method = methods[i];
			Request request = method.getAnnotation(Request.class);
			
			if(request == null) continue;
			
			ControllerMapping mapping = new ControllerMapping();
			
			mapping.setClazz(clazz);
			mapping.setMethod(method);
			String path = request.value();
			
			if(path.endsWith("/") && !"/".equals(path)) path = path.substring(0, path.length() - 1);
			
			mapping.setPath(path);
			mapping.setAjax(request.ajax());
			
			container.addMapping(mapping);
			
			log.warn("扫描控制器映射信息:path=" + mapping.getPath() + ",class=" + clazz.getCanonicalName());
			
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
