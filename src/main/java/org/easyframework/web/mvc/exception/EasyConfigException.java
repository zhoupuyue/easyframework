/**
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * @author zhoupuyue
 * @date 2014-3-2 下午1:19:10
 * 参数配置错误异常
 */
public class EasyConfigException extends EasyException {

	private static final long serialVersionUID = -7779958123494260645L;

	/**
	 * 
	 */
	public EasyConfigException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyConfigException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyConfigException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyConfigException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyConfigException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyConfigException(Throwable cause) {
		super(cause);
	}

}
