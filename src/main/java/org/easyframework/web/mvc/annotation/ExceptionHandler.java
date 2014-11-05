/**
 * 
 */
package org.easyframework.web.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 异常处理器注释
 * @author zhoupuyue
 * @date 2013-8-28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {

	Class<?> value();
	
}
