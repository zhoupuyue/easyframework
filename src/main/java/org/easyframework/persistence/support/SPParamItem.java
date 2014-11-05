/**
 */
package org.easyframework.persistence.support;


/**
 * 存储过程参数项
 * @author zhoupuyue
 * @date 2014-3-5 下午9:04:16
 */
public class SPParamItem {

	/**
	 * 参数类型,{in,out}
	 */
	private SPParamType paramType;
	
	/**
	 * 参数名称
	 */
	private String name;
	
	/**
	 * Out参数类型
	 */
	private int type;
	
	/**
	 * 参数值
	 */
	private Object value;
	
	public SPParamItem(SPParamType paramType, String name, Object value){
		this.paramType = paramType;
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 参数默认是OUT类型
	 * @param name
	 * @param type
	 */
	public SPParamItem(String name, int type){
		this.paramType = SPParamType.OUT;
		this.name = name;
		this.type = type;
	}
	
	/**
	 * 参数默认是IN类型
	 * @param name
	 * @param value
	 */
	public SPParamItem(String name, Object value){
		this.paramType = SPParamType.IN;
		this.name = name;
		this.value = value;
	}
	
	public SPParamType getParamType() {
		return paramType;
	}

	public void setParamType(SPParamType paramType) {
		this.paramType = paramType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
