/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 反射方式调用方法异常
 * @author zhoupuyue
 * @date 2013-9-16
 */
public class EasyInvokeMethodException extends EasyException {

	private static final long serialVersionUID = -813728132024526364L;

	/**
	 * 
	 */
	public EasyInvokeMethodException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyInvokeMethodException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyInvokeMethodException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyInvokeMethodException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyInvokeMethodException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyInvokeMethodException(Throwable cause) {
		super(cause);
	}

}
