/**
 * 
 */
package org.easyframework.persistence.config;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL配置缓存
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasySqlMapConfig {

	/**
	 * sql配置集合，键为{namespace.id}，值为EasySQLHolder
	 */
	private static Map<String,EasySQLHolder> sqlMap = new HashMap<String,EasySQLHolder>();
	
	/**
	 * 添加SQL配置信息
	 * @param map
	 */
	public static void addSqlConfig(String id, EasySQLHolder sqlHolder){
		sqlMap.put(id, sqlHolder);
	}
	
	/**
	 * 获取SQL配置信息
	 * @param id
	 * @return
	 */
	public static EasySQLHolder getSqlConfig(String id){
		return sqlMap.get(id);
	}
	
}
