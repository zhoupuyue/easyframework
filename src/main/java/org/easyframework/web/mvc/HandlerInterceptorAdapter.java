/**
 * 
 */
package org.easyframework.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 拦截器适配器
 * @author zhoupuyue
 * @date 2013-9-2
 */
public abstract class HandlerInterceptorAdapter implements HandlerInterceptor {

	@Override
	public boolean before(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public boolean after(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	/**
	 * 转发请求
	 */
	public void forward(String path){
		MvcHelper.forward(path);
	}
	
	/**
	 * 重定向请求
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	public void redirect(String url){
		MvcHelper.redirect(url);
	}
	
	/**
	 * 输出JSON
	 * @param json
	 */
	public void writeJson(Object json){
		MvcHelper.writeJson(json);
	}
	
}
