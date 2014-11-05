/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 创建对象异常
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyInstantiationException extends EasyException {

	private static final long serialVersionUID = -5288673509632053559L;

	/**
	 * 
	 */
	public EasyInstantiationException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyInstantiationException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyInstantiationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyInstantiationException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyInstantiationException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyInstantiationException(Throwable cause) {
		super(cause);
	}

}
