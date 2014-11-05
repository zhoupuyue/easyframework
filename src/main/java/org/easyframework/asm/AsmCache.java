/**
 */
package org.easyframework.asm;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * Asm字节码缓存
 * @author zhoupuyue
 * @date 2014-3-12 下午1:14:08
 */
public class AsmCache {

	/**
	 * Asm构造方法缓存
	 */
	private static Map<Class<?>, ConstructorAccess<?>> constructorAccessCache = new HashMap<Class<?>,ConstructorAccess<?>>();
	
	/**
	 * Asm方法缓存
	 */
	private static Map<Class<?>, MethodAccess> methodAccessCache = new HashMap<Class<?>, MethodAccess>();
	
	/**
	 * 获取类的构造方法Asm对象
	 * @param clazz
	 * @return
	 */
	public static ConstructorAccess<?> getConstructorAccess(Class<?> clazz){
		
		ConstructorAccess<?> constructorAccess = constructorAccessCache.get(clazz);
		
		if(constructorAccess == null){
			
			synchronized(clazz){
				
				if(constructorAccess == null){
					
					constructorAccess = ConstructorAccess.get(clazz);
					constructorAccessCache.put(clazz, constructorAccess);
					return constructorAccess;
				}
				
			}
		}
		
		return constructorAccess;
		
	}
	
	/**
	 * 获取类的方法Asm对象
	 * @param clazz
	 * @return
	 */
	public static MethodAccess getMethodAccess(Class<?> clazz){
		
		MethodAccess methodAccess = methodAccessCache.get(clazz);
		
		if(methodAccess == null){
			
			synchronized(clazz){
				
				if(methodAccess == null){
					methodAccess = MethodAccess.get(clazz);
					methodAccessCache.put(clazz, methodAccess);
					return methodAccess;
				}
				
			}
		}
		
		return methodAccess;
		
	}
	
}
