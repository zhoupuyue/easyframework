/**
 * 
 */
package org.easyframework.util;

import org.apache.commons.logging.Log;

/**
 * 监控执行时间
 * @author zhoupuyue
 * @date 2013-5-7
 */
public class TimeMonitor {
	
	private Log log;
	private long begin;
	private String msg;
	
	public TimeMonitor(){
		this.begin = System.currentTimeMillis();
	}
	
	public TimeMonitor(Log log, String msg){
		this.log = log;
		this.msg = msg;
		this.begin = System.currentTimeMillis();
	}
	
	/**
	 * 记录执行时间到日志文件(毫秒)
	 */
	public void logUseTimeMillis(){
		if(log.isInfoEnabled()){
			long now = System.currentTimeMillis();
			long times = now - begin;
			begin = now;
			log.info(this.msg + " , using " + String.valueOf(times) + " ms ");
		}
	}
	
	/**
	 * 记录执行时间
	 * @param name
	 */
	public void logUseTimeMillis(String name){
		if(log.isInfoEnabled()){
			long now = System.currentTimeMillis();
			long times = now - begin;
			begin = now;
			log.info(name + " : using " + String.valueOf(times) + " ms ");
		}
	}
	
	/**
	 * 记录执行时间
	 * @param name
	 */
	public void warnUseTimeMillis(String name){
		if(log.isWarnEnabled()){
			long now = System.currentTimeMillis();
			long times = now - begin;
			begin = now;
			log.warn(name + " : using " + String.valueOf(times) + " ms ");
		}
	}
	
	/**
	 * 打印执行时间到标准输出(毫秒)
	 */
	public void printUseTimeMillis(){
		long now = System.currentTimeMillis();
		long times = now - begin;
		begin = now;
		System.out.println(this.msg + ", using " + String.valueOf(times) + " ms ");
	}
	
	/**
	 * 打印执行时间
	 * @param name
	 */
	public void printUseTimeMillis(String name){
		long now = System.currentTimeMillis();
		long times = now - begin;
		begin = now;
		System.out.println(name + " : using " + String.valueOf(times) + " ms ");
	}
	
	/**
	 * 返回执行时间
	 * @return
	 */
	public long getUseTimeMillis(){
		long now = System.currentTimeMillis();
		return now - begin;
	}
	
}
