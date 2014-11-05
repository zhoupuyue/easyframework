/**
 * 
 */
package org.easyframework.web.mvc;

/**
 * 解析配置文件链
 * @author zhoupuyue
 * @date 2013-9-6
 */
public interface AnnotationParserChain extends AnnotationParser {

	/**
	 * 设置下一个解析器
	 * @param parser
	 */
	public void setNext(AnnotationParser parser);
	
	/**
	 * 获取下一个解析器
	 * @return
	 */
	public AnnotationParser getNext();
	
}
