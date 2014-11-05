package org.easyframework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;

/**
 * 读取配置文件工具类
 * @author zhoupuyue
 * @date 2013-2-18
 */
public class ConfigUtil {
	
	public static final Log log = LogFactory.getLog(ConfigUtil.class);
	
	private static Properties properties;
	
	public static void load(String config) {
		
		properties = new Properties();
		
		try {
			properties.load(ConfigUtil.class.getResourceAsStream(config));
		} catch (FileNotFoundException e) {
			log.error("配置文件{" + config + "}不存在！", e);
			throw new EasyException("配置文件({1})不存在！", config);
		} catch (IOException e) {
			log.error("读取配置文件异常！", e);
			throw new EasyException("读取配置文件({1})异常！", config);
		}
		
	}
	
	/**
	 * 获取配置文件属性值
	 * @param key 
	 * @return
	 */
	public static String getProperty(String key){
		if(properties != null){
			return properties.getProperty(key);
		}
		return null;
	}
	
}
