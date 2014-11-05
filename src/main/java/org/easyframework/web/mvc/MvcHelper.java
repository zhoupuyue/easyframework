/**
 * 
 */
package org.easyframework.web.mvc;

import static org.easyframework.web.WebHelper.write;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyframework.web.mvc.context.ApplicationContext;
import org.easyframework.web.mvc.context.RequestContext;
import org.easyframework.web.mvc.exception.EasyDispatcherException;
import org.easyframework.web.mvc.mapping.ViewPathMapping;

import com.alibaba.fastjson.JSONObject;

/**
 * MVC帮助类
 * @author zhoupuyue
 * @date 2013-9-3
 */
public class MvcHelper {

	/**
	 * 转发请求
	 */
	public static void forward(String path){
		
		HttpServletRequest request = RequestContext.getRequest();
		HttpServletResponse response = RequestContext.getResponse();
		ViewPathMapping viewPathMapping = ApplicationContext.getViewPathMapping();
		
		if(path.startsWith("/")) path = path.substring(1);
		
		try {
			request.getRequestDispatcher(viewPathMapping.getPrefix() + path + viewPathMapping.getSuffix()).forward(request, response);
		} catch (Exception e) {
			throw new EasyDispatcherException(e, "拦截器转发请求异常!");
		}
	
	}
	
	/**
	 * 重定向请求
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void redirect(String url){
		HttpServletResponse response = RequestContext.getResponse();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			throw new EasyDispatcherException(e, "拦截器重定向请求异常!");
		}
	}
	
	/**
	 * 输出JSON
	 * @param json
	 */
	public static void writeJson(Object json){
		
		if(json == null) return;
		
		try{
			if(json instanceof java.lang.String){
				write(RequestContext.getResponse(), (String)json);
			}else{
				write(RequestContext.getResponse(), JSONObject.toJSONString(json));
			}
		}catch(Exception e){
			throw new EasyDispatcherException(e, "向客户端输出JSON异常!");
		}
		
	}
	
}
