/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 反射获取目标方法异常
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyGetClassMethodException extends EasyException {

	private static final long serialVersionUID = 1854713292269164996L;

	public EasyGetClassMethodException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyGetClassMethodException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyGetClassMethodException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyGetClassMethodException(Throwable cause, String message, String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyGetClassMethodException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyGetClassMethodException(Throwable cause) {
		super(cause);
	}
	
}
