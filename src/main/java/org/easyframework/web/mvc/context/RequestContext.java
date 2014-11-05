/**
 * 
 */
package org.easyframework.web.mvc.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyframework.web.mvc.MultipartFile;

/**
 * 请求上下文
 * @author zhoupuyue
 * @date 2013-8-26
 */
public class RequestContext {

	/**
	 * 当前线程Request
	 */
	private static final ThreadLocal<HttpServletRequest> Request = new ThreadLocal<HttpServletRequest>();
	
	/**
	 * 当前线程Response
	 */
	private static final ThreadLocal<HttpServletResponse> Response = new ThreadLocal<HttpServletResponse>();
	
	/**
	 * 当前线程的请求是否是ajax请求
	 */
	private static final ThreadLocal<Boolean> IsAjax = new ThreadLocal<Boolean>();
	
	/**
	 * 当前线程上传的文件
	 */
	private static final ThreadLocal<MultipartFile> MultipartFile = new ThreadLocal<MultipartFile>();
	
	
	public static void setRequest(HttpServletRequest request){
		Request.set(request);
	}
	
	public static HttpServletRequest getRequest(){
		return Request.get();
	}
	
	public static void setResponse(HttpServletResponse response){
		Response.set(response);
	}
	
	public static HttpServletResponse getResponse(){
		return Response.get();
	}
	
	public static void setIsAjax(boolean isAjax){
		IsAjax.set(isAjax);
	}
	
	public static boolean getIsAjax(){
		return IsAjax.get();
	}
	
	public static MultipartFile getMultipartFile(){
		return MultipartFile.get();
	}
	
	public static void setMultipartFile(MultipartFile multipartFile){
		MultipartFile.set(multipartFile);
	}
	
	public static void clear(){
		Request.set(null);
		Response.set(null);
		IsAjax.set(null);
		MultipartFile.set(null);
	}
	
}
