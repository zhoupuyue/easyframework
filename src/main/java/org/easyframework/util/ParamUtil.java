package org.easyframework.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;


/**
 * 参数检查工具类
 * @author zhoupuyue
 * @date 2013-3-15
 */
public class ParamUtil {
	
	public static final Log log = LogFactory.getLog(ParamUtil.class);

	/**
	 * 检测value是否为空
	 * @param value
	 * @return
	 */
	public static boolean empty(String value){
		return value == null || value.trim().equals("");
	}
	
	/**
	 * 检测集合是否为空
	 * @param collect
	 * @return
	 */
	public static boolean empty(Collection<?> collect){
		return collect == null || collect.size() == 0;
	}
	
	/**
	 * 检测Map是否为空
	 * @param collect
	 * @return
	 */
	public static boolean empty(Map<?,?> map){
		return map == null || map.size() == 0;
	}
	
	/**
	 * 检查参数值是否为空
	 * @param keys
	 * @param params
	 * @return
	 */
	public static void checkEmpty(String[] keys ,String ... params){
		if(keys == null){
			return ;
		}
		for(int i = 0 ; i < keys.length ; i++){
			if(empty(params[i])){
				throw new EasyException("非法的参数,参数[{1}]不允许为空！", keys[i]);
			}
		}
	}
	
	/**
	 * 检查参数值是否为空
	 * @param keys
	 * @param params
	 * @return
	 */
	public static void checkEmpty(String[] keys ,Map<String,String> params){
		if(keys == null){
			return ;
		}
		for(int i = 0 ; i < keys.length ; i++){
			if(empty(params.get(keys[i]))){
				throw new EasyException("非法的参数,参数[{1}]不允许为空！", keys[i]);
			}
		}
	}
	
	/**
	 * 检查对象属性是否为空
	 * @param keys
	 * @param obj
	 */
	public static void checkEmpty(String[] keys, Object obj){
		if(keys == null){
			return ;
		}
		for(int i = 0 ; i < keys.length ; i++){
			String field = keys[i];
			String method = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
			try {
				Method m = obj.getClass().getMethod(method);
				Object value = m.invoke(obj);
				if(value == null){
					throw new EasyException("非法的参数,参数[{1}]不允许为空！", keys[i]);
				}
				if(value instanceof java.lang.String){
					String s = (String)value;
					if(empty(s)) throw new EasyException("非法的参数,参数[{1}]不允许为空！", keys[i]);
				}
			} catch(NoSuchMethodException e){ 
				log.error("非法的参数，方法" + method + "不存在！", e);
				throw new EasyException("非法的参数，方法{1}不存在！", method);
			} catch (Exception e) {
				throw new EasyException("服务器异常！");
			} 
		}
	}
	
}
