/**
 * 
 */
package org.easyframework.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * @author zhoupuyue
 * @date 2013-8-28
 */
public interface ExceptionResolver {

	/**
	 * 接口方法，处理异常
	 * @param request
	 * @param response
	 * @param e
	 */
	public Object resolve(HttpServletRequest request, HttpServletResponse response, Throwable t, boolean isAjax);
	
}
