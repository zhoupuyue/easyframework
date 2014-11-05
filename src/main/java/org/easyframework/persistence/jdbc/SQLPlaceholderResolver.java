/**
 * 
 */
package org.easyframework.persistence.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.persistence.config.EasySQLHolder;
import org.easyframework.persistence.exception.EasyPersistenceParameterException;
import org.easyframework.persistence.support.SPParam;
import org.easyframework.persistence.support.SPParamItem;

/**
 * SQL语句占位符处理器
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class SQLPlaceholderResolver {
	
	public static final Log log = LogFactory.getLog(SQLPlaceholderResolver.class);
	
	/**
	 * 解析SQL中的${name}变量占位符，并替换成参数值；将参数值组装到parameterList中
	 * @param sqlHolder : SQL配置
	 * @param o : 参数
	 * @return : 能被数据库引擎编译的SQL语句
	 */
	public static String resolve(EasySQLHolder sqlHolder, Object o, List<Object> parameterList){
		
		if(o == null){
			if(sqlHolder.getParameterCount() == 1){
				if(sqlHolder.hasVariable()){
					String sql = sqlHolder.getCompileSql();
					sql = sql.replace("${" + sqlHolder.getSingleVariable() + "}", null);
					return sql;
				}else{
					parameterList.add(null);
					return sqlHolder.getCompileSql();
				}
			}else{
				log.error("参数为null\nid：" + sqlHolder.getId());
				throw new EasyPersistenceParameterException("参数为null！");
			}
		}
		
		//o是java内置类型
		if(o instanceof String || o instanceof Number || o instanceof java.util.Date){
			
			if(sqlHolder.hasVariable()){
				
				if(sqlHolder.getVariableCount() > 1) {
					log.error("参数错误，参数个数小于变量占位符数量！\nid：" + sqlHolder.getId() + "，参数：" + o.toString());
					throw new EasyPersistenceParameterException("参数错误，参数个数小于变量占位符数量！");
				}
				
				String sql = sqlHolder.getCompileSql();
				sql = sql.replace("${" + sqlHolder.getSingleVariable() + "}", o.toString());
				
				return sql;
				
			}
			
			if(sqlHolder.getParameterCount() > 1){
				log.error("参数错误，参数个数小于参数占位符数量！\nid：" + sqlHolder.getId() + "，参数：" + o.toString());
				throw new EasyPersistenceParameterException("参数错误，参数个数小于参数占位符数量！");
			}
			
			parameterList.add(o);
			
			return sqlHolder.getCompileSql();
			
		}
		
		//编译后的SQL语句
		String sql = sqlHolder.getCompileSql();
		
		//用变量值代替${name}类型的占位符，用于动态表名等情况
		if(sqlHolder.getVariableCount() > 0){
			
			Set<String> variablePlaceHolders = sqlHolder.getVariablePlaceHolders();
			
			for(String variableName : variablePlaceHolders){
				
				Object fieldValue = PersistenceParameterResolver.getObjectFieldValue(o, variableName);
				sql = sql.replace("${" + variableName + "}", fieldValue.toString());
				
				/*
				if(fieldValue == null){
					log.error("参数错误，参数" + variableName + "的值为null！\nid：" + sqlHolder.getId() + "，参数：" + o.toString());
					throw new EasyPersistenceParameterException("参数错误，参数变量{1}的值为null！", variableName);
				}else{
					sql = sql.replace("${" + variableName + "}", fieldValue.toString());
				}
				*/
				
			}
			
		}
		
		//将参数组装到List列表中
		if(sqlHolder.getParameterCount() > 0){
			
			List<String> paramPlaceHolderList = sqlHolder.getParamPlaceHolderList();
			
			for(String paramName : paramPlaceHolderList){
				
				Object value = PersistenceParameterResolver.getObjectFieldValue(o, paramName);
				
				/*
				if(value == null){
					log.error("参数错误，参数" + paramName + "的值为null！\nid：" + sqlHolder.getId() + "，参数：" + o.toString());
					throw new EasyPersistenceParameterException("参数错误，参数{1}的值为null！", paramName);
				}
				*/
				
				parameterList.add(value);
				
			}
			
		}
		
		return sql;
		
	}
	
	/**
	 * 解析SQL中的${name}变量占位符，并替换成参数值；将参数值组装到parameterList中
	 * @param sqlHolder : SQL配置
	 * @param o : 参数
	 * @return : 能被数据库引擎编译的SQL语句
	 */
	public static String resolve(EasySQLHolder sqlHolder, Map<String,Object> map, List<Object> parameterList){
		
		//编译后的SQL语句
		String sql = sqlHolder.getCompileSql();
		
		//用变量值代替${name}类型的占位符，用于动态表名等情况
		if(sqlHolder.getVariableCount() > 0){
			
			Set<String> variablePlaceHolders = sqlHolder.getVariablePlaceHolders();
			
			for(String variableName : variablePlaceHolders){
				
				Object fieldValue = map.get(variableName);
				//sql = sql.replace("${" + variableName + "}", fieldValue.toString());
				
				if(fieldValue == null && !map.containsKey(variableName)){
					log.error("参数错误，参数" + variableName + "不存在！\nid：" + sqlHolder.getId() + "，参数：" + map.toString());
					throw new EasyPersistenceParameterException("参数错误，参数{1}不存在！", variableName);
				}else{
					sql = sql.replace("${" + variableName + "}", fieldValue.toString());
				}
				
			}
			
		}
		
		//将参数组装到List列表中
		if(sqlHolder.getParameterCount() > 0){
			
			List<String> paramPlaceHolderList = sqlHolder.getParamPlaceHolderList();
			
			for(String paramName : paramPlaceHolderList){
				
				Object value = map.get(paramName);
				
				if(value == null && !map.containsKey(paramName)){
					log.error("参数错误，参数" + paramName + "不存在！\nid：" + sqlHolder.getId() + "，参数：" + map.toString());
					throw new EasyPersistenceParameterException("参数错误，参数{1}不存在！", paramName);
				}
				
				parameterList.add(value);
				
			}
			
		}
		
		return sql;
		
	}
	
	/**
	 * 将参数值组装到parameterList中；只支持#{name}占位符参数变量
	 * @param sqlHolder : SQL配置
	 * @param params : 参数
	 * @return : 能被数据库引擎编译的SQL语句
	 */
	public static String resolve(EasySQLHolder sqlHolder, List<Object> parameterList, Object ... params){
		
		int size = sqlHolder.getParamPlaceHolderList().size();
		
		if((params == null && size != 0) || params.length != size){
			log.error("参数错误，参数个数与SQL不匹配！id：" + sqlHolder.getId() + "，参数：" + params);
			throw new EasyPersistenceParameterException("参数错误，参数个数与SQL不匹配！");
		}
		
		parameterList.addAll(Arrays.asList(params));
		
		return sqlHolder.getCompileSql();
		
	}
	
	/**
	 * 解析存储过程参数
	 * @param sqlHolder
	 * @param paramMap
	 * @param parameterList
	 * @return
	 */
	public static String resolveSp(EasySQLHolder sqlHolder, SPParam param, List<SPParamItem> parameterList){
		
		List<String> paramPlaceHolderList = sqlHolder.getParamPlaceHolderList();
		
		if(paramPlaceHolderList.size() != param.size()){
			log.error("参数错误，参数个数与SQL不匹配！id：" + sqlHolder.getId() + "，参数：" + param);
			throw new EasyPersistenceParameterException("参数错误，参数个数与SQL不匹配！");
		}
		
		for(String paramName : paramPlaceHolderList){
			
			SPParamItem paramItem = param.getParam(paramName);
			
			if(paramItem == null){
				log.error("参数错误，参数{" + paramName + "}不存在！id：" + sqlHolder.getId());
				throw new EasyPersistenceParameterException("参数错误，参数{1}不存在！", paramName);
			}
			
			parameterList.add(paramItem);
			
		}
		
		return sqlHolder.getCompileSql();
		
	}
	
}
