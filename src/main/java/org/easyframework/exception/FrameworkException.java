/**
 * 
 */
package org.easyframework.exception;

/**
 * 基本异常类
 * @author zhoupuyue
 * @date 2013-8-27
 */
public class FrameworkException extends RuntimeException {

	private static final long serialVersionUID = -4288140906846751923L;

	public FrameworkException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FrameworkException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FrameworkException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FrameworkException(Throwable cause) {
		super(cause);
	}
	
}
