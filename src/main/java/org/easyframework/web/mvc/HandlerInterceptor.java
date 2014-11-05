/**
 * 
 */
package org.easyframework.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器接口
 * @author zhoupuyue
 * @date 2013-8-27
 */
public interface HandlerInterceptor {

	/**
	 * 在Controller之前执行，true继续往下执行，false中断流程，返回结果
	 * @param request
	 * @param response
	 */
	public boolean before(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 在Controller之后执行，true继续往下执行，false中断流程，返回结果
	 * @param request
	 * @param response
	 */
	public boolean after(HttpServletRequest request, HttpServletResponse response);
	
}
