/**
 * 
 */
package org.easyframework.web.mvc.mapping;

/**
 * 异常处理器配置
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class ExceptionMapping {
	
	/**
	 * 异常处理器类
	 */
	private Class<?> resolver;
	
	/**
	 * 目标异常
	 */
	private Class<?> exception;
	
	/**
	 * @return the resolver
	 */
	public Class<?> getResolver() {
		return resolver;
	}
	/**
	 * @param resolver the resolver to set
	 */
	public void setResolver(Class<?> resolver) {
		this.resolver = resolver;
	}
	/**
	 * @return the exception
	 */
	public Class<?> getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(Class<?> exception) {
		this.exception = exception;
	}
	
}
