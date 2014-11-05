/**
 * 
 */
package org.easyframework.web.mvc.result;

import java.io.Serializable;

/**
 * 重定向请求
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class Redirect implements Serializable {

	private static final long serialVersionUID = -7840544351703890819L;
	
	private String url;
	
	public Redirect(String url){
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
