package org.easyframework.web.mvc;

import java.util.List;

/**
 * 映射配置容器适配器
 * @author zhoupuyue
 * @date 2014-3-2 上午10:08:09
 */
public abstract class MappingContainerAdapter<E> implements MappingContainer<E> {
	
	@Override
	public E getMapping(String path) {
		return null;
	}

	@Override
	public Class<?> getMapping(Class<?> clazz) {
		return null;
	}

	@Override
	public List<E> getMappings(String path) {
		return null;
	}

	@Override
	public void addMapping(E e) {
		
	}

	@Override
	public void sort() {
		
	}

	@Override
	public boolean exists(E e) {
		return false;
	}

	@Override
	public List<E> getMappings() {
		return null;
	}
	
}
