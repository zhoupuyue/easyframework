package org.easyframework.web.mvc.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyframework.web.mvc.MappingContainerAdapter;
import org.easyframework.web.mvc.mapping.ControllerMapping;

/**
 * 控制器容器
 * @author zhoupuyue
 * @date 2014-3-2 上午10:19:00
 */
public class ControllerMappingContainer extends MappingContainerAdapter<ControllerMapping> {

	/**
	 * 控制器集合
	 */
	private Map<String,ControllerMapping> controllers = new HashMap<String,ControllerMapping>();
	
	/**
	 * 添加控制器映射
	 * @param path
	 * @param mapper
	 */
	public void addMapping(ControllerMapping mapping){
		this.controllers.put(mapping.getPath(), mapping);
	}
	
	/**
	 * 获取控制器映射
	 * @param path
	 * @return
	 */
	public ControllerMapping getMapping(String path){
		return controllers.get(path);
	}
	
	/**
	 *  获取所有映射配置
	 */
	@Override
	public List<ControllerMapping> getMappings() {
		Collection<ControllerMapping> mappings = controllers.values();
		List<ControllerMapping> list = new ArrayList<ControllerMapping>();
		list.addAll(mappings);
		return list;
	}
	
}
