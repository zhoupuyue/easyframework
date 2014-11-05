package org.easyframework.web.mvc;

import java.util.List;

/**
 * 解析器适配器
 * @author Administrator
 *
 */
public abstract class ComponentParserAdapter<E> implements ComponentParser<E> {

	@Override
	public List<E> parseList(Object resource) {
		return null;
	}
	
	@Override
	public E parseObject(Object resource) {
		return null;
	}
	
}
