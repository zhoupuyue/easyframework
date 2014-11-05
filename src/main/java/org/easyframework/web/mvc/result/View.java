/**
 * 
 */
package org.easyframework.web.mvc.result;

import java.io.Serializable;

/**
 * 返回结果视图
 * @author zhoupuyue
 * @date 2013-8-29
 */
public class View implements Serializable {

	private static final long serialVersionUID = -880996191109914794L;

	private String path;
	
	public View(String path){
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
