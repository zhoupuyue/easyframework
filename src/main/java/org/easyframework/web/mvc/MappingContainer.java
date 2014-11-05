package org.easyframework.web.mvc;

import java.util.List;

/**
 * 映射容器接口
 * @author zhoupuyue
 * @date 2014-3-2 上午9:46:39
 */
public interface MappingContainer<E> {

	/**
	 * 添加映射配置信息
	 * @param e
	 */
	public void addMapping(E e);
	
	/**
	 * 获取映射配置
	 * @param path
	 * @return
	 */
	public E getMapping(String path);
	
	/**
	 * 获取映射配置
	 * @param clazz
	 * @return
	 */
	public Class<?> getMapping(Class<?> clazz);
	
	/**
	 * 获取映射配置
	 * @param path
	 * @return
	 */
	public List<E> getMappings(String path);
	
	/**
	 * 对映射信息排序
	 */
	public void sort();
	
	/**
	 * 检查映射配置信息是否已经存在
	 * @param e
	 * @return
	 */
	public boolean exists(E e);
	
	/**
	 * 获取所有映射配置
	 * @return
	 */
	public List<E> getMappings();
	
}
