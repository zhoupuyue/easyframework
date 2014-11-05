/**
 * 
 */
package org.easyframework.exception;

import static org.easyframework.util.StringUtil.merge;

/**
 * 自定义异常
 * @author zhoupuyue
 * @date 2013-7-19
 */
public class EasyException extends FrameworkException {

	private static final long serialVersionUID = 6172920365200327677L;

	public EasyException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EasyException(Throwable cause, String message) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public EasyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EasyException(Throwable cause) {
		super(cause);
	}
	
	public EasyException(String message, String ... params){
		this(merge(message, params));
	}
	
	public EasyException(Throwable cause, String message, String ...params){
		this(cause, merge(message, params));
	}
	
}
