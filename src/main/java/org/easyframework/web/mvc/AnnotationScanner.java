/**
 * 
 */
package org.easyframework.web.mvc;

/**
 * 注解扫描接口
 * @author zhoupuyue
 * @date 2013-8-30
 */
public interface AnnotationScanner {

	/**
	 * 扫描基础包下所有类
	 * @param basepackage
	 */
	public void scan();
	
}
