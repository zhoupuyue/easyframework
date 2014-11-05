/**
 * 
 */
package org.easyframework.web.mvc.result;

import java.io.Serializable;

/**
 * 返回Json结果
 * @author zhoupuyue
 * @date 2013-5-27
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 5675156345148115666L;
	
	public static final JsonResult SUCCESSFUL = new JsonResult(ResultCode.SUCCESS, "ok");
	
	public static final JsonResult FAILURE = new JsonResult(ResultCode.FAILED, "error");
	
	public static final JsonResult PERMISSION = new JsonResult(ResultCode.NO_PERMISSION, "permission forbidden");
	
	public static final ResultCode SUCCESS = ResultCode.SUCCESS;
	
	public static final ResultCode FAILED = ResultCode.FAILED;
	
	public static final ResultCode NO_PERMISSION = ResultCode.NO_PERMISSION;
	
	private ResultCode retcode;
	
	/**
	 * 错误消息
	 */
	private String message;
	
	/**
	 * 结果数据
	 */
	private Object data;
	
	public JsonResult(ResultCode retcode){
		this.retcode = retcode;
	}
	
	public JsonResult(ResultCode retcode, String message){
		this.retcode = retcode;
		this.message = message;
	}
	
	public JsonResult(ResultCode retcode, Object data){
		this.retcode = retcode;
		this.data = data;
	}
	
	public JsonResult(ResultCode retcode, Object data, String message){
		this.retcode = retcode;
		this.data = data;
		this.message = message;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the retcode
	 */
	public int getRetcode() {
		return this.retcode.getValue();
	}
	
	public void setRetcode(ResultCode retcode){
		this.retcode = retcode;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
}
