/**
 * 
 */
package org.easyframework.web.mvc.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数必须为数字注释
 * @author zhoupuyue
 * @date 2013-8-27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Digit {

	String msg() default "";
	
}
