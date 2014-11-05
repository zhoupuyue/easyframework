/**
 * 
 */
package org.easyframework.web.mvc.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数只允许指定的值
 * @author zhoupuyue
 * @date 2013-8-27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Enums {

	String[] value();
	
	String msg() default "";
	
}
