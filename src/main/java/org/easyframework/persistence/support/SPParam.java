/**
 */
package org.easyframework.persistence.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储过程参数
 * @author zhoupuyue
 * @date 2014-3-5 下午8:58:10
 */
public class SPParam {

	/**
	 * 存储过程参数
	 */
	private Map<String,SPParamItem> params = new HashMap<String,SPParamItem>();
	
	/**
	 * 存储过程是否返回结果集
	 */
	private boolean withResultSet;
	
	/**
	 * 存储过程返回结果集类型
	 */
	private Class<?> resultSetType;
	
	public SPParam(){
		
	}
	
	public SPParam(boolean withResultSet, Class<?> resultSetType){
		this.withResultSet = withResultSet;
		this.resultSetType = resultSetType; 
	}
	
	/**
	 * 添加'OUT'类型参数
	 * @param type
	 * @param value
	 * @return
	 */
	public SPParam addOutParam(String name, int type){
		this.params.put(name, new SPParamItem(name, type));
		return this;
	}
	
	/**
	 * 添加'IN'类型参数
	 * @param value
	 * @return
	 */
	public SPParam addInParam(String name, Object value){
		this.params.put(name, new SPParamItem(name, value));
		return this;
	}
	
	/**
	 * 获取存储过程参数
	 * @return
	 */
	public Map<String,SPParamItem> getParams(){
		return this.params;
	}
	
	/**
	 * 获取存储过程参数
	 * @param paramName
	 * @return
	 */
	public SPParamItem getParam(String paramName){
		return this.params.get(paramName);
	}
	
	/**
	 * 获取存储过程参数个数
	 * @return
	 */
	public int size(){
		return this.params.size();
	}
	
	/**
	 * 存储过程是否返回结果集
	 * @return
	 */
	public boolean isWithResultSet(){
		return this.withResultSet;
	}

	/**
	 * 存储过程返回结果集类型
	 * @return
	 */
	public Class<?> getResultSetType() {
		return resultSetType;
	}
	
}
