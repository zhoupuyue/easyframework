/**
 * 
 */
package org.easyframework.web.mvc;

import java.util.List;


/**
 * 配置文件解析接口
 * @author zhoupuyue
 * @date 2013-8-30
 */
public interface ComponentParser<E> {

	/**
	 * 解析控制器
	 */
	public List<E> parseList(Object resource);
	
	/**
	 * 解析拦截器和异常处理器
	 * @param resource
	 * @return
	 */
	public E parseObject(Object resource);
	
}
