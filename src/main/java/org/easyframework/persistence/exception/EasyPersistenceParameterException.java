/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 参数异常
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyPersistenceParameterException extends EasyException {

	private static final long serialVersionUID = -4531259447455127334L;

	/**
	 * 
	 */
	public EasyPersistenceParameterException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyPersistenceParameterException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyPersistenceParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyPersistenceParameterException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyPersistenceParameterException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyPersistenceParameterException(Throwable cause) {
		super(cause);
	}
	
}
