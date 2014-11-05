/**
 * 
 */
package org.easyframework.web.mvc.annotation.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数不允许为空注释
 * @author zhoupuyue
 * @date 2013-8-27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Require {
	
	/**
	 * 异常提示消息
	 * @return
	 */
	String msg() default "";
	
	/**
	 * 是否允许空字符串
	 * @return
	 */
	boolean empty() default true;
	
}
