/**
 * 
 */
package org.easyframework.web.mvc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;

/**
 * 默认组件对象工厂
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class DefaultObjectFactory implements ObjectFactory {
	
	/**
	 * 对象缓存
	 */
	private Map<Class<?>,Object> instances = new HashMap<Class<?>,Object>();
	
	public static final Log log = LogFactory.getLog(DefaultObjectFactory.class);
	
	/**
	 * 获取组件对象
	 * @param clazz
	 * @return
	 */
	public Object getInstance(Class<?> clazz){
		
		if(clazz == null) throw new IllegalArgumentException("参数clazz为空！");
		
		Object o = instances.get(clazz);
		
		if(o == null){
			
			synchronized(clazz){
				
				if(o == null){
					
					try {
						o = clazz.newInstance();
						instances.put(clazz, o);
					} catch (InstantiationException e) {
						log.error("无法创建对象！", e);
						throw new EasyException("服务器异常！");
					} catch (IllegalAccessException e) {
						log.error("没有创建对象的权限！", e);
						throw new EasyException("服务器异常！");
					}
					
				}
			}
		}
		
		return o;
	}
	
}
