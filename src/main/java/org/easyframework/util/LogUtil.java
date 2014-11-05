package org.easyframework.util;

import org.apache.commons.logging.Log;

import static org.easyframework.util.StringUtil.merge;


/**
 * 记录Log工具类
 * @author zhoupuyue
 * @date 2013-3-21
 */
public class LogUtil {
	
	/**
	 * 记录错误级别日志
	 * @param log
	 * @param t
	 * @param message
	 * @param params
	 */
	public static void logError(Log log, Throwable t, String message, String ... params){
		log.error(merge(message, params), t);
	}
	
	/**
	 * 记录错误级别日志
	 * @param log
	 * @param t
	 * @param message
	 * @param params
	 */
	public static void logError(Log log, String message, String ... params){
		log.error(merge(message, params));
	}
	
	/**
	 * 记录警告级别日志
	 * @param log
	 * @param t
	 * @param message
	 * @param params
	 */
	public static void logWarn(Log log, String message, String ... params){
		log.warn(merge(message, params));
	}
	
	/**
	 * 记录警告级别日志
	 * @param log
	 * @param t
	 * @param message
	 * @param params
	 */
	public static void logWarn(Log log, Exception e, String message, String ... params){
		log.warn(merge(message, params), e);
	}
	
	/**
	 * 记录信息级别日志
	 * @param log
	 * @param t
	 * @param message
	 * @param params
	 */
	public static void logInfo(Log log, String message, String ... params){
		log.info(merge(message, params));
	}
	
}
