/**
 * 
 */
package org.easyframework.web.mvc.exception;

import org.easyframework.exception.EasyException;

/**
 * 映射配置异常
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class EasyMappingConfigException extends EasyException {

	private static final long serialVersionUID = -5122010016368074527L;

	public EasyMappingConfigException() {
		super();
	}

	/**
	 * @param message
	 * @param params
	 */
	public EasyMappingConfigException(String message, String... params) {
		super(message, params);
	}

	/**
	 * @param message
	 */
	public EasyMappingConfigException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param message
	 * @param params
	 */
	public EasyMappingConfigException(Throwable cause, String message,
			String... params) {
		super(cause, message, params);
	}

	/**
	 * @param cause
	 * @param message
	 */
	public EasyMappingConfigException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * @param cause
	 */
	public EasyMappingConfigException(Throwable cause) {
		super(cause);
	}

}
