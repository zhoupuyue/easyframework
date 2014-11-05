/**
 */
package org.easyframework.web.mvc;

/**
 * @author zhoupuyue
 * @date 2014-3-2 上午11:53:54
 * 注解解析器
 */
public interface AnnotationParser {
	
	/**
	 * 解析注解
	 * @param clazz
	 */
	public void parseAnnotation(Class<?> clazz);

}
