/**
 * 
 */
package org.easyframework.web.mvc.mapping;

/**
 * 视图路径配置
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class ViewPathMapping {

	/**
	 * 路径前缀
	 */
	private String prefix;
	
	/**
	 * 路径后缀
	 */
	private String suffix;
	
	public ViewPathMapping(String prefix, String suffix){
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
