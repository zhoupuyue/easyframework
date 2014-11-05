/**
 * 
 */
package org.easyframework.web.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制器方法请求路径映射Annotation
 * @author zhoupuyue
 * @date 2013-8-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {
	
	String value();
	
	boolean ajax() default false;
	
}
