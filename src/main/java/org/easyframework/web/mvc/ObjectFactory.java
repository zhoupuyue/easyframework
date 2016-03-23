/**
 * 
 */
package org.easyframework.web.mvc;

/**
 * 组件工厂接口
 * 
 * @author zhoupuyue
 * @date 2013-8-30
 */
public interface ObjectFactory {

	/**
	 * 获取组件对象
	 * 
	 * @param clazz
	 *            : 组件实现类
	 * @return
	 */
	public Object getInstance(Class<?> clazz);

}
