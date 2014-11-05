/**
 * 
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * 请求异常
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class EasyRequestException extends EasyException {

	private static final long serialVersionUID = -8262215346649439738L;

	public EasyRequestException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyRequestException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyRequestException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyRequestException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyRequestException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyRequestException(Throwable cause) {
		super(cause);
	}
	
}
