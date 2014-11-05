/**
 * 
 */
package org.easyframework.persistence.exception;

import org.easyframework.exception.EasyException;

/**
 * 查询数据库异常
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyQueryException extends EasyException {

	private static final long serialVersionUID = 4577582993768119173L;

	public EasyQueryException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyQueryException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyQueryException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyQueryException(Throwable cause, String message, String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyQueryException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyQueryException(Throwable cause) {
		super(cause);
	}

}
