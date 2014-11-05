package org.easyframework.web.mvc.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.AnnotationParser;
import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.AnnotationParserChain;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.context.ApplicationContext;
import org.easyframework.web.mvc.exception.EasyParameterException;

/**
 * 参数验证器Annotation配置解析器
 * @author zhoupuyue
 * @date 2013-5-8
 */
public class ValidatorAnnotationParser implements AnnotationParserChain {
	
	public static final Log log = LogFactory.getLog(ValidatorAnnotationParser.class);

	/**
	 * 下一个解析器
	 */
	private AnnotationParser next;
	
	/**
	 * 解析Annotation参数验证器配置
	 */
	@Override
	public void parseAnnotation(Class<?> clazz) {
		
		if(clazz == null) return;
		
		if(!ParameterValidator.class.isAssignableFrom(clazz)) {
			if(this.next != null) next.parseAnnotation(clazz);
			return;
		}
		
		Validator validator = clazz.getAnnotation(Validator.class);
		
		if(validator == null) return;
		
		ParameterValidator parameterValidator = null;
		try {
			parameterValidator = (ParameterValidator) clazz.newInstance();
		} catch (InstantiationException e) {
			log.error("创建参数验证器对象异常！", e);
			throw new EasyParameterException(e, "创建参数验证器对象异常！");
		} catch (IllegalAccessException e) {
			log.error("没有访问对象权限！", e);
			throw new EasyParameterException(e, "没有访问对象权限！");
		}
		
		if(parameterValidator == null) return; 
		
		ApplicationContext.addValidator(parameterValidator);
		
		log.warn("扫描参数验证器，class=" + parameterValidator.getClass().getCanonicalName());
		
	}
	
	/**
	 * 设置下一个解析器
	 * @param parser
	 */
	@Override
	public void setNext(AnnotationParser parser) {
		this.next = parser;
	}
	
	@Override
	public AnnotationParser getNext() {
		return next;
	}

}
