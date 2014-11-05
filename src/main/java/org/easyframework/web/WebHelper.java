/**
 * 
 */
package org.easyframework.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * HTTP请求帮助类
 * @author zhoupuyue
 * @date 2013-5-8
 */
public class WebHelper {
	
	/**
	 * 设置Cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param age
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int age){
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(age);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 删除Cookie
	 * @param request
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name){
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		
		return ip;
		
	}
	
	/**
	 * 获取所有Cookie值
	 * @param request
	 * @return
	 */
	public static Map<String,String> getCookies(HttpServletRequest request){
		
		Map<String,String> map = new HashMap<String,String>();
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null || cookies.length == 0) return map;
		
		for(Cookie cookie : cookies){
			map.put(cookie.getName(), cookie.getValue());
		}
		
		return map;
		
	}
	
	/**
	 * 获取Cookie值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name){
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null || cookies.length == 0) return null;
		
		String value = null;
		
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(name)){
				value = cookie.getValue();
				break; 
			}
		}
		
		return value;
		
	}
	
	/**
	 * 将字符串输出到客户端
	 * @param response
	 * @param text
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, String text) throws IOException{
		
		PrintWriter pw = null;
		
		try{
			pw = response.getWriter();
			pw.write(text);
			pw.flush();
		}finally{
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
}
