package org.easyframework.web.mvc.container;

import static org.easyframework.util.ParamUtil.empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easyframework.web.mvc.MappingContainerAdapter;
import org.easyframework.web.mvc.mapping.InterceptorMapping;

/**
 * 拦截器容器
 * @author zhoupuyue
 * @date 2014-3-2 上午10:20:19
 */
public class InterceptorMappingContainer extends MappingContainerAdapter<InterceptorMapping> {
	
	/**
	 * 拦截器集合，按order从小到大排序
	 */
	private List<InterceptorMapping> interceptors = new ArrayList<InterceptorMapping>();
	
	/**
	 * 添加拦截器映射配置
	 */
	public void addMapping(InterceptorMapping mapping){
		this.interceptors.add(mapping);
	}
	
	/**
	 * 获取匹配的拦截器
	 * @param path
	 * @return
	 */
	public List<InterceptorMapping> getMappings(String path){
		
		List<InterceptorMapping> list = new ArrayList<InterceptorMapping>();
		
		if(empty(interceptors)) return list;
		
		if(!path.endsWith("/")) path += "/";
		
		for(int i = 0 ; i < interceptors.size() ; i++ ){
			
			InterceptorMapping interceptor = interceptors.get(i);
			String p = interceptor.getPath();
			
			if(path.startsWith(p)) 
				list.add(interceptor);
			
		}
		
		return list;
		
	}
	
	/**
	 * 检查interceptor是否已经扫描
	 * @param mapping
	 * @return
	 */
	public boolean exists(InterceptorMapping mapping){
		
		if(mapping == null) return true;
		
		for(InterceptorMapping im : interceptors){
			if(im.getPath().equals(mapping.getPath()) && im.getClazz().getCanonicalName().equals(mapping.getClazz().getCanonicalName())){
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * 对拦截器按order从小到大排序
	 */
	public void sort(){
		
		if(interceptors.size() < 2) return;
		
		InterceptorMapping[] mappings = interceptors.toArray(new InterceptorMapping[]{});
		
		for(int i = 0 ; i < mappings.length - 1 ; i++ ){
			
			InterceptorMapping mapping = mappings[i];
			
			for(int k = i + 1 ; k < mappings.length ; k++ ){
				
				InterceptorMapping m = mappings[k];
				
				if(mapping.getOrder() > m.getOrder()){
					mappings[i] = m;
					mappings[k] = mapping;
				}
				
			}
		}
		
		interceptors = Arrays.asList(mappings);
		
	}
	
	/**
	 *  获取所有映射配置
	 */
	@Override
	public List<InterceptorMapping> getMappings() {
		return this.interceptors;
	}
	
}
