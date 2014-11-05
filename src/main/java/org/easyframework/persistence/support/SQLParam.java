/**
 */
package org.easyframework.persistence.support;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL语句参数
 * @author zhoupuyue
 * @date 2014-3-5 下午9:19:34
 */
public class SQLParam {

	private Map<String,Object> paramItemMap = new HashMap<String,Object>();
	
	public SQLParam(){
		
	}
	
	/**
	 * 添加SQL参数
	 * @param name
	 * @param value
	 * @return
	 */
	public SQLParam add(String name, Object value){
		this.paramItemMap.put(name, value);
		return this;
	}
	
	public Map<String,Object> getParamMap(){
		return this.paramItemMap;
	}
	
}
