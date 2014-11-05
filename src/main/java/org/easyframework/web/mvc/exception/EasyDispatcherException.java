/**
 * 
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * 转发请求异常
 * @author zhoupuyue
 * @date 2013-8-29
 */
public class EasyDispatcherException extends EasyException {

	private static final long serialVersionUID = -7144834596361493363L;

	/**
	 * 
	 */
	public EasyDispatcherException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyDispatcherException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyDispatcherException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyDispatcherException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyDispatcherException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyDispatcherException(Throwable cause) {
		super(cause);
	}
	
}
