/**
 * 
 */
package org.easyframework.util;

import static org.easyframework.util.ParamUtil.empty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.easyframework.exception.EasyException;

/**
 * 时间日期工具类
 * @author zhoupuyue
 * @date 2013-7-19
 */
public class DateUtil {

	/**
	 * 格式化日期时间
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 格式化日期时间
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date, String format){
		if(date == null) return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(java.sql.Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(java.sql.Date date, String format){
		if(date == null) return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date, String format){
		if(date == null) return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date){
		
		if(date == null){
			return "";
		}
		
		return new SimpleDateFormat("HH:mm:ss").format(date);
		
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date, String format){
		
		if(date == null){
			return "";
		}
		
		return new SimpleDateFormat(format).format(date);
		
	}
	
	/**
	 * 将日期时间字符串解析成java.util.Date对象
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date parseDateTime(String s){
		
		if(empty(s)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = null;
		
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			throw new EasyException(e, "解析时间日期异常！");
		}
		
		return date;
		
	}
	
	/**
	 * 将日期字符串解析成java.sql.Date对象
	 * @param s
	 * @return
	 */
	public static java.sql.Date parseDate(String s){
		
		if(empty(s)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			throw new EasyException(e, "解析日期异常！");
		}
		
		return new java.sql.Date(date.getTime());
		
	}
	
	/**
	 * 将日期字符串按format格式解析成java.sql.Date对象
	 * @param s
	 * @return
	 */
	public static java.sql.Date parseDate(String s, String format){
		
		if(empty(s)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = null;
		
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			throw new EasyException(e, "解析日期异常！");
		}
		
		return new java.sql.Date(date.getTime());
		
	}
	
	/**
	 * 将日期时间字符串按format格式解析成java.util.Date对象
	 * @param s
	 * @param format
	 * @return
	 */
	public static java.util.Date parseDateTime(String s, String format){
		
		if(empty(s)) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = null;
		
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			throw new EasyException(e, "解析时间日期异常！");
		}
		
		return date;
		
	}
	
}
