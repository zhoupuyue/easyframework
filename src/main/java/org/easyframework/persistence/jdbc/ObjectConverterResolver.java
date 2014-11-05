/**
 * 
 */
package org.easyframework.persistence.jdbc;

import static org.easyframework.util.ParamUtil.empty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.asm.AsmCache;
import org.easyframework.persistence.annotation.Column;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * 对象转换处理器
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class ObjectConverterResolver {
	
	public static final Log log = LogFactory.getLog(ObjectConverterResolver.class);

	/**
	 * 将Map集合转换成对象
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mapToObject(Map<String,Object> map, Class<T> clazz){
		
		if(map == null) return null;
		
		T o = (T)AsmCache.getConstructorAccess(clazz).newInstance();
		
		if(o == null) return null;
		
		Field[] fields = clazz.getDeclaredFields();
		MethodAccess methodAccess = AsmCache.getMethodAccess(clazz);
		
		for(int i = 0 ; i < fields.length ; i++ ){
			
			Field field = fields[i];
			String fieldName = field.getName();
			String key = fieldName;
			
			Column column = field.getAnnotation(Column.class);
			if(column != null) key = column.value();
			
			if(!map.containsKey(key)) continue;
			
			Object value = map.get(key);
			
			if(value == null) continue;
			
			StringBuilder methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
			methodAccess.invoke(o, methodName.toString(), value);
			
		}
		
		return o;
		
	}
	
	/**
	 * 将Map列表转换成对象列表
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertList(List<Map<String,Object>> list, Class<T> clazz){
		
		if(empty(list)) return null;
		
		List<T> objectList = new ArrayList<T>();
		
		for(int i = 0 ; i < list.size() ; i++ ){
			Map<String,Object> map = list.get(i);
			T o = mapToObject(map, clazz);
			objectList.add(o);
		}
		
		return objectList;
		
	}
	
}
