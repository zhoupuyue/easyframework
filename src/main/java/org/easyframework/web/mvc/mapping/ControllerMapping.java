/**
 * 
 */
package org.easyframework.web.mvc.mapping;

import java.lang.reflect.Method;

/**
 * 控制器映射配置类
 * @author zhoupuyue
 * @date 2013-5-8
 */
public class ControllerMapping {
	
	/**
	 * HTTP请求的路径
	 */
	private String path;
	
	/**
	 * HTTP请求对应的类
	 */
	private Class<?> clazz;
	
	/**
	 * HTTP请求对应的方法
	 */
	private Method method;
	
	/**
	 * 是否是ajax请求
	 */
	private boolean ajax;
	
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	/**
	 * @return the ajax
	 */
	public boolean isAjax() {
		return ajax;
	}
	/**
	 * @param ajax the ajax to set
	 */
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	
}
