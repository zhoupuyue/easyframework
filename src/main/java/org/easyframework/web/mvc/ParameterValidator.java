/**
 * 
 */
package org.easyframework.web.mvc;

import java.lang.reflect.Field;

/**
 * 参数验证器接口
 * @author zhoupuyue
 * @date 2013-9-20
 */
public interface ParameterValidator {

	/**
	 * 验证对象属性值,验证通过返回true,验证失败返回false
	 * @param value : 参数值
	 * @param field : 字段
	 * @return
	 */
	public boolean validate(Field field, String value);
	
	/**
	 * 顺序,值小的优先执行
	 * @return
	 */
	public int order();
	
}
