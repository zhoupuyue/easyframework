/**
 * 
 */
package org.easyframework.util;

/**
 * 字符串工具类
 * @author zhoupuyue
 * @date 2013-8-5
 */
public class StringUtil {

	/**
	 * 将信息与动态参数合并
	 * @param message
	 * @param params
	 * @return
	 */
	public static String merge(String message, String... params){
		if(params == null){
			return message; 
		}
		for(int i = 0 ; i < params.length ; i++ ){
			message = message.replace("{" + (i+1) + "}", params[i]);
		}
		return message;
	}
	
	/**
	 * 检测params数组中是否包含param字符串
	 * @param param
	 * @param params
	 * @return
	 */
	public static boolean include(String param, String[] params){
		if(params == null || params.length == 0) return false;
		for(String p : params){
			if(p.equals(param)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将字符串数组转换成字符串
	 * @param arr
	 * @return
	 */
	public static String arrayToString(String[] arr){
		if(arr == null || arr.length == 0) return "";
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0 ; i < arr.length ; i++){
			if(i == 0){
				sb.append(arr[i]);
			}else{
				sb.append(",").append(arr[i]);
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
}
