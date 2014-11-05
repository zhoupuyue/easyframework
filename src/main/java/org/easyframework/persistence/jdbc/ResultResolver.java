package org.easyframework.persistence.jdbc;

import static org.easyframework.persistence.jdbc.PersistenceParameterResolver.getParameter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.asm.AsmCache;
import org.easyframework.exception.EasyException;
import org.easyframework.persistence.annotation.Column;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * 返回结果处理类
 * @author zhoupuyue
 * @date 2013-4-22
 */
public class ResultResolver {

	public static final Log log = LogFactory.getLog(ResultResolver.class);
	
	/**
	 * 获取数据库返回结果
	 * @param rs
	 * @return
	 */
	public static Map<String,Object> getMapResult(ResultSet rs){
		
		if(rs == null) return null;
		
		try{
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			if(rs.next()){
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				for(int i = 1 ; i <= columnCount ; i++){
					
					String columnLabel = metaData.getColumnLabel(i);
					int type = metaData.getColumnType(i);
					Object value = getParameter(rs, i, type);
					
					map.put(columnLabel, value);
					
				}
				
				return map;
				
			}
			
		}catch(SQLException e){
			log.error("从结果集获取数据异常！", e);
			throw new EasyException(e, "从结果集获取数据异常！");
		}
		
		return null;
		
	}
	
	/**
	 * 获取数据库Object返回结果
	 * @param rs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectResult(ResultSet rs, Class<T> cls){
		
		if(rs == null) return null;
		
		Object result = null;
		
		try{
			
			if(rs.next()){
				
				if(String.class.isAssignableFrom(cls)){
					result = rs.getString(1);
				}
				else if(Integer.class.isAssignableFrom(cls)){
					result = rs.getInt(1);
				}
				else if(Long.class.isAssignableFrom(cls)){
					result = rs.getLong(1);
				}
				else if(Double.class.isAssignableFrom(cls)){
					result = rs.getDouble(1);
				}
				else if(java.util.Date.class.isAssignableFrom(cls)){
					result = rs.getTimestamp(1);
				}
				else if(BigDecimal.class.isAssignableFrom(cls)){
					result = rs.getBigDecimal(1);
				}
				else if(java.sql.Date.class.isAssignableFrom(cls)){
					result = rs.getDate(1);
				}
				else if(Float.class.isAssignableFrom(cls)){
					result = rs.getFloat(1);
				}
				else if(Blob.class.isAssignableFrom(cls)){
					result = rs.getBlob(1);
				}
				else if(Byte.class.isAssignableFrom(cls)){
					result = rs.getByte(1);
				}
				else if(Short.class.isAssignableFrom(cls)){
					result = rs.getShort(1);
				}
				else{
					
					MethodAccess methodAccess = AsmCache.getMethodAccess(cls);
					T o = (T)AsmCache.getConstructorAccess(cls).newInstance();
					
					Field[] fields = cls.getDeclaredFields();
					
					Set<String> columnNameSet = new HashSet<String>();
					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
					
					for(int i = 1 ; i <= columnCount ; i++ ){
						String columnName = metaData.getColumnLabel(i);
						columnNameSet.add(columnName);
					}

					for(int i = 0 ; i < fields.length ; i++ ){
						
						Field field = fields[i];
						String fieldName = field.getName();
						String key = fieldName;
						
						Column column = field.getAnnotation(Column.class);
						if(column != null) key = column.value();
						
						StringBuilder methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
						
						Object value = getParameter(rs, columnNameSet, key, field.getType());
						
						methodAccess.invoke(o, methodName.toString(), value);
						
					}
					
					return o;
					
				}
				
			}
			
		}catch(SQLException e){
			log.error("从结果集获取数据异常！", e);
			throw new EasyException(e, "从结果集获取数据异常！");
		}
		
		if(result == null){
			return null;
		}else{
			return (T)result;
		}
		
	}
	
	/**
	 * 获取数据库返回结果列表
	 * @param rs
	 * @return
	 */
	public static List<Map<String,Object>> getListResult(ResultSet rs){
		
		if(rs == null) return null;
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		try{
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			while(rs.next()){
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				for(int i = 1 ; i <= columnCount ; i++){
					
					String columnLabel = metaData.getColumnLabel(i);
					int type = metaData.getColumnType(i);
					Object value = getParameter(rs, i, type);
					
					map.put(columnLabel, value);
					
				}
				
				list.add(map);
				
			}
			
		}catch(SQLException e){
			log.error("从结果集获取数据异常！", e);
			throw new EasyException(e, "从结果集获取数据异常！");
		}
		
		return list;
		
	}
	
	/**
	 * 获取结果集数据
	 * @param rs : 结果集
	 * @param cls : 类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getListResult(ResultSet rs, Class<T> cls){
		
		if(rs == null) return null;
		
		if(String.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Integer.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Long.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Double.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(java.util.Date.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(BigDecimal.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(java.sql.Date.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Float.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Blob.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Byte.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		else if(Short.class.isAssignableFrom(cls)){
			return getSingleListResult(rs, cls);
		}
		
		List<T> list = new ArrayList<T>();
		
		try {
			
			Set<String> columnNameSet = new HashSet<String>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			for(int i = 1 ; i <= columnCount ; i++ ){
				String columnName = metaData.getColumnLabel(i);
				columnNameSet.add(columnName);
			}
			
			Field[] fields = cls.getDeclaredFields();
			
			List<String> columnList = new ArrayList<String>(fields.length);
			List<Class<?>> columnTypeList = new ArrayList<Class<?>>(fields.length);
			List<String> methodList = new ArrayList<String>(fields.length);
			
			for(int i = 0 ; i < fields.length ; i++ ){
				
				Field field = fields[i];
				String fieldName = field.getName();
				String key = fieldName;
				
				Column column = field.getAnnotation(Column.class);
				if(column != null) key = column.value();
				
				StringBuilder methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
				
				columnList.add(key);
				columnTypeList.add(field.getType());
				methodList.add(methodName.toString());
				
			}
			
			MethodAccess methodAccess = AsmCache.getMethodAccess(cls);
			
			while(rs.next()){
				
				T o = (T)AsmCache.getConstructorAccess(cls).newInstance();
				
				for(int i = 0 ; i < columnList.size() ; i++){
					String key = columnList.get(i);
					Object value = getParameter(rs, columnNameSet, key, columnTypeList.get(i));
					methodAccess.invoke(o, methodList.get(i), value);
				}
				
				list.add(o);
				
			}
			
		} catch (SQLException e) {
			log.error("从结果集获取数据异常！", e);
			throw new EasyException(e, "从结果集获取数据异常！");
		}
		
		return list;
		
	}
	
	/**
	 * 获取单个字段结果
	 * @param rs
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSingleListResult(ResultSet rs, Class<T> cls){
		
		List<T> list = new ArrayList<T>();
		
		try {
			
			while(rs.next()){
			
				if(String.class.isAssignableFrom(cls)){
					String value = rs.getString(1);
					list.add((T)value);
				}
				else if(Integer.class.isAssignableFrom(cls)){
					Integer value = rs.getInt(1);
					list.add((T)value);
				}
				else if(Long.class.isAssignableFrom(cls)){
					Long value = rs.getLong(1);
					list.add((T)value);
				}
				else if(Double.class.isAssignableFrom(cls)){
					Double value = rs.getDouble(1);
					list.add((T)value);
				}
				else if(java.util.Date.class.isAssignableFrom(cls)){
					java.util.Date value = rs.getTimestamp(1);
					list.add((T)value);
				}
				else if(BigDecimal.class.isAssignableFrom(cls)){
					BigDecimal value = rs.getBigDecimal(1);
					list.add((T)value);
				}
				else if(java.sql.Date.class.isAssignableFrom(cls)){
					java.sql.Date value = rs.getDate(1);
					list.add((T)value);
				}
				else if(Float.class.isAssignableFrom(cls)){
					Float value = rs.getFloat(1);
					list.add((T)value);
				}
				else if(Blob.class.isAssignableFrom(cls)){
					Blob value = rs.getBlob(1);
					list.add((T)value);
				}
				else if(Byte.class.isAssignableFrom(cls)){
					Byte value = rs.getByte(1);
					list.add((T)value);
				}
				else if(Short.class.isAssignableFrom(cls)){
					Short value = rs.getShort(1);
					list.add((T)value);
				}
				
			}
			
		} catch (SQLException e) {
			log.error("从结果集获取数据异常！", e);
			throw new EasyException(e, "从结果集获取数据异常！");
		}
		
		return list;
		
	}
	
}
