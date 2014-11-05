/**
 * 
 */
package org.easyframework.web.mvc.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.AnnotationParser;
import org.easyframework.web.mvc.ExceptionResolver;
import org.easyframework.web.mvc.MappingContainer;
import org.easyframework.web.mvc.AnnotationParserChain;
import org.easyframework.web.mvc.annotation.ExceptionHandler;
import org.easyframework.web.mvc.mapping.ExceptionMapping;

/**
 * 异常处理注释解析器
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class ExceptionAnnotationParser implements AnnotationParserChain {
	
	public static final Log log = LogFactory.getLog(ExceptionAnnotationParser.class);
	
	/**
	 * 异常处理器映射配置容器
	 */
	private MappingContainer<ExceptionMapping> container;
	
	public ExceptionAnnotationParser(MappingContainer<ExceptionMapping> container) {
		this.container = container;
	}
	
	/**
	 * 下一个解析器
	 */
	private AnnotationParser next;

	/**
	 * 解析异常注释
	 */
	@Override
	public void parseAnnotation(Class<?> clazz) {
		
		if(clazz == null) return;
		
		if(!ExceptionResolver.class.isAssignableFrom(clazz)){
			if(this.next != null) next.parseAnnotation(clazz);
			return;
		}
		
		ExceptionHandler exception = clazz.getAnnotation(ExceptionHandler.class);
		
		if(exception == null) return;
		
		ExceptionMapping mapping = new ExceptionMapping();
		mapping.setResolver(clazz);
		mapping.setException(exception.value());
		
		container.addMapping(mapping);
		
		log.warn("扫描异常处理器配置信息:class=" + clazz.getCanonicalName() + ",exception=" + exception.value().getCanonicalName());
		
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
