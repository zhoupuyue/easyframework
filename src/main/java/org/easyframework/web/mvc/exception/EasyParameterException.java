/**
 * 
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * 参数异常
 * @author zhoupuyue
 * @date 2013-9-4
 */
public class EasyParameterException extends EasyException {

	private static final long serialVersionUID = 8012598863520368672L;

	public EasyParameterException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyParameterException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyParameterException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyParameterException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyParameterException(Throwable cause) {
		super(cause);
	}

}
