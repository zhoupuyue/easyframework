/**
 * 
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * 扫描注释异常
 * @author zhoupuyue
 * @date 2013-8-30
 */
public class EasyMappingScannerException extends EasyException {

	private static final long serialVersionUID = -6006049801668840294L;

	public EasyMappingScannerException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyMappingScannerException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyMappingScannerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyMappingScannerException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyMappingScannerException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyMappingScannerException(Throwable cause) {
		super(cause);
	}

}
