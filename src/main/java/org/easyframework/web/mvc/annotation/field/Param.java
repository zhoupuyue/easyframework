/**
 * 
 */
package org.easyframework.web.mvc.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注解
 * @author zhoupuyue
 * @date 2013-9-3
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
	
	String value();
	
	String msg() default "";
	
}
