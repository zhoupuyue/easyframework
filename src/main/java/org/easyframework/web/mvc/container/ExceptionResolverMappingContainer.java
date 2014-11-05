package org.easyframework.web.mvc.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easyframework.web.mvc.MappingContainerAdapter;
import org.easyframework.web.mvc.mapping.ExceptionMapping;

/**
 * 异常处理器容器
 * @author zhoupuyue
 * @date 2014-3-2 上午10:19:17
 */
public class ExceptionResolverMappingContainer extends MappingContainerAdapter<ExceptionMapping> {

	/**
	 * 异常处理器集合，范围从小到大排序
	 */
	private List<ExceptionMapping> resolvers = new ArrayList<ExceptionMapping>();
	
	/**
	 * 添加异常处理器映射
	 * @param e
	 */
	public void addMapping(ExceptionMapping mapping){
		resolvers.add(mapping);
	}
	
	/**
	 * 寻找合适的异常处理器
	 * @param clazz
	 * @return
	 */
	public Class<?> getMapping(Class<?> clazz){
		
		for(int i = 0 ; i < resolvers.size() ; i++ ){
			
			ExceptionMapping mapping = resolvers.get(i);
			Class<?> cls = mapping.getException();
			
			if(cls.isAssignableFrom(clazz)) 
				return mapping.getResolver();
			
		}
		
		return null;
		
	}
	
	/**
	 * 检查exception resolver是否已经扫描
	 * @param mapping
	 * @return
	 */
	public boolean exists(ExceptionMapping mapping){
		
		if(mapping == null) return true;
		
		for(ExceptionMapping em : resolvers){
			if(em.getException().getCanonicalName().equals(mapping.getException().getCanonicalName()) && em.getResolver().getCanonicalName().equals(mapping.getResolver().getCanonicalName())){
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * 对异常处理器按范围从小到大排序
	 */
	public void sort(){
		
		if(resolvers.size() < 2) return;
		
		ExceptionMapping[] mappings = resolvers.toArray(new ExceptionMapping[]{});
		
		for(int i = 0 ; i < mappings.length - 1 ; i++ ){
			
			ExceptionMapping mapping = mappings[i];
			
			for(int k = i + 1 ; k < mappings.length ; k++){
				
				ExceptionMapping m = mappings[k];
				
				if(mapping.getException().isAssignableFrom(m.getException())){
					mappings[i] = m;
					mappings[k] = mapping;
				}
				
			}
			
		}
		
		resolvers = Arrays.asList(mappings);
		
	}
	
	/**
	 *  获取所有映射配置
	 */
	@Override
	public List<ExceptionMapping> getMappings() {
		return this.resolvers;
	}

}
