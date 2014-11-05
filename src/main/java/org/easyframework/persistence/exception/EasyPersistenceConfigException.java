/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 配置异常
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyPersistenceConfigException extends EasyException {

	private static final long serialVersionUID = -9188601127884080269L;

	public EasyPersistenceConfigException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyPersistenceConfigException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyPersistenceConfigException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyPersistenceConfigException(Throwable cause, String message, String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyPersistenceConfigException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyPersistenceConfigException(Throwable cause) {
		super(cause);
	}

}
