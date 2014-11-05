package org.easyframework.persistence.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.easyframework.persistence.exception.EasyPersistenceParameterException;

/**
 * 
 * @author zhoupuyue
 * @date 2014-3-1 上午11:46:02
 * SQL配置包装类
 */
public class EasySQLHolder {
	
	private String id;
	
	/**
	 * 配置文件中的原SQL语句
	 */
	private String sql;
	
	/**
	 * 编译后的SQL语句
	 */
	private String compileSql;
	
	/**
	 * SQL参数变量占位符，#{name}形式，提交给数据库引擎编译
	 */
	private List<String> paramPlaceHolderList = new ArrayList<String>();
	
	/**
	 * SQL变量占位符，${name}形式，提交给数据库引擎编译前直接替换
	 */
	private Set<String> variablePlaceHolders = new LinkedHashSet<String>();
	
	public EasySQLHolder(String sql){
		this.sql = sql;
	}
	
	public EasySQLHolder(String id, String sql){
		this.id = id;
		this.sql = sql;
	}
	
	/**
	 * 编译SQL语句
	 * 将变量名称装入variablePlaceHolderList中；
	 * 将参数名称装入paramPlaceHolderList中，并将参数名称用"?"代替
	 */
	public void compile(){

		int index = 0;
		
		//解析${name}类型的占位符，用于动态表名等情况
		while(sql.indexOf("${", index) != -1){
			
			int begin = sql.indexOf("${", index);
			int end = sql.indexOf("}", begin);
			
			String variableName = sql.substring(begin + 2, end).trim();
			
			variablePlaceHolders.add(variableName);
			
			index = end;
			
		}
		
		//将#{name}占位符替换成?,并将参数名组装到List列表中
		while(sql.indexOf("#{") != -1){
			
			int begin = sql.indexOf("#{");
			int end = sql.indexOf("}", begin);
			
			String paramName = sql.substring(begin + 2, end).trim();
			paramPlaceHolderList.add(paramName);
			
			sql = sql.substring(0, begin) + "?" + sql.substring(end + 1);
			
		}
		
		this.compileSql = sql;
		
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 查看是否有需要替换的变量
	 * @return
	 */
	public boolean hasVariable(){
		return this.variablePlaceHolders.size() > 0;
	}
	
	/**
	 * 返回变量占位符的数量
	 * @return
	 */
	public int getVariableCount(){
		return this.variablePlaceHolders.size();
	}
	
	public int getParameterCount(){
		return this.paramPlaceHolderList.size();
	}
	
	public String getSql(){
		return this.sql;
	}
	
	public String getCompileSql(){
		return this.compileSql;
	}

	public List<String> getParamPlaceHolderList() {
		return paramPlaceHolderList;
	}

	public Set<String> getVariablePlaceHolders() {
		return variablePlaceHolders;
	}
	
	/**
	 * 获取唯一的参数
	 * @return
	 */
	public String getSingleParameter() {
		
		if(paramPlaceHolderList.size() == 0) throw new EasyPersistenceParameterException("找不到参数!");
		
		return paramPlaceHolderList.get(0);
		
	}
	
	/**
	 * 获取唯一的变量
	 * @return
	 */
	public String getSingleVariable() {
		
		if(variablePlaceHolders.size() == 0) throw new EasyPersistenceParameterException("找不到变量!");
		
		Iterator<String> iterator = variablePlaceHolders.iterator();
		
		if(iterator.hasNext()) {
			return iterator.next();
		}
		
		return null;
		
	}
	
}
