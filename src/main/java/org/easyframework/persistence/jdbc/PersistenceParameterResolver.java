package org.easyframework.persistence.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.asm.AsmCache;
import org.easyframework.persistence.exception.EasyPersistenceParameterException;
import org.easyframework.persistence.support.SPParam;
import org.easyframework.persistence.support.SPParamItem;
import org.easyframework.persistence.support.SPParamType;

import com.esotericsoftware.reflectasm.MethodAccess;


/**
 * SQL参数处理类
 * @author zhoupuyue
 * @date 2013-4-22
 */
public class PersistenceParameterResolver {
	
	public static final Log log = LogFactory.getLog(PersistenceParameterResolver.class);
	
	/**
	 * 设置参数
	 * @param pstmt
	 * @param index : 参数索引
	 * @param value : 参数值
	 */
	public static void setParameter(PreparedStatement pstmt, int index, Object value){
		
		try{
			
			if(value == null){
				pstmt.setObject(index, null);
				return;
			}
			
			if(value instanceof java.lang.String){
				pstmt.setString(index, (String)value);
			}
			else if(value instanceof java.lang.Integer){
				pstmt.setInt(index, (Integer)value);
			}
			else if(value instanceof java.lang.Long){
				pstmt.setLong(index, (Long)value);
			}
			else if(value instanceof java.sql.Date){
				pstmt.setDate(index, (java.sql.Date)value);
			}
			else if(value instanceof java.util.Date){
				java.util.Date d = (java.util.Date)value;
				pstmt.setTimestamp(index, new java.sql.Timestamp(d.getTime()));
			}
			else if(value instanceof java.math.BigDecimal){
				pstmt.setBigDecimal(index, (BigDecimal)value);
			}
			else if(value instanceof java.lang.Double){
				pstmt.setDouble(index, (Double)value);
			}
			else if(value instanceof java.lang.Float){
				pstmt.setFloat(index, (Float)value);
			}
			else if(value instanceof java.lang.Short){
				pstmt.setShort(index, (Short)value);
			}
			else if(value instanceof Byte){
				pstmt.setByte(index, (Byte)value);
			}
			else if(value instanceof Blob){
				pstmt.setBlob(index, (Blob)value);
			}
			else{
				pstmt.setObject(index, value);
			}
			
		}catch(SQLException e){
			log.error("设置SQL参数异常！Index = " + index, e);
			throw new EasyPersistenceParameterException(e, "设置SQL参数异常！");
		}
		
	}
	
	/**
	 * 设置存储过程参数
	 * @param cs
	 * @param index : 参数索引
	 * @param param : 参数值
	 */
	public static void setSPParameter(CallableStatement cs, int index, SPParamItem param){
		
		try{
			
			if(param.getParamType() == SPParamType.OUT){
				cs.registerOutParameter(index, param.getType());
				return;
			}
			
			Object value = param.getValue();
			
			if(value == null){
				cs.setObject(index, null);
				return;
			}
			
			if(value instanceof java.lang.String){
				cs.setString(index, (String)value);
			}
			else if(value instanceof java.lang.Integer){
				cs.setInt(index, (Integer)value);
			}
			else if(value instanceof java.util.Date){
				java.util.Date d = (java.util.Date)value;
				cs.setTimestamp(index, new java.sql.Timestamp(d.getTime()));
			}
			else if(value instanceof java.lang.Long){
				cs.setLong(index, (Long)value);
			}
			else if(value instanceof java.lang.Double){
				cs.setDouble(index, (Double)value);
			}
			else if(value instanceof java.math.BigDecimal){
				cs.setBigDecimal(index, (BigDecimal)value);
			}
			else if(value instanceof java.lang.Float){
				cs.setFloat(index, (Float)value);
			}
			else if(value instanceof java.lang.Short){
				cs.setShort(index, (Short)value);
			}
			else if(value instanceof java.sql.Date){
				cs.setDate(index, (java.sql.Date)value);
			}
			else if(value instanceof Blob){
				cs.setBlob(index, (Blob)value);
			}
			else if(value instanceof Byte){
				cs.setByte(index, (Byte)value);
			}
			else{
				cs.setObject(index, value);
			}
			
		}catch(SQLException e){
			log.error("设置SQL参数异常！Index = " + index, e);
			throw new EasyPersistenceParameterException(e, "设置SQL参数异常！");
		}
		
	}
	
	/**
	 * 获取结果集数据
	 * @param rs：结果集
	 * @param columnNameSet : 字段名集合
	 * @param columnName : 列明
	 * @param cls ： 字段类型
	 * @return
	 */
	public static Object getParameter(ResultSet rs, Set<String> columnNameSet, String columnName, Class<?> cls){
		
		if(rs == null) return null;
		
		if(columnNameSet == null) throw new EasyPersistenceParameterException("参数columnNameSet不允许为空！");
		
		if(!columnNameSet.contains(columnName)) return null;
		
		try{
			
			if(String.class.isAssignableFrom(cls)){
				return rs.getString(columnName);
			}
			else if(Integer.class.isAssignableFrom(cls)){
				return rs.getInt(columnName);
			}
			else if(Long.class.isAssignableFrom(cls)){
				return rs.getLong(columnName);
			}
			else if(Double.class.isAssignableFrom(cls)){
				return rs.getDouble(columnName);
			}
			else if(java.util.Date.class.isAssignableFrom(cls)){
				return rs.getTimestamp(columnName);
			}
			else if(BigDecimal.class.isAssignableFrom(cls)){
				return rs.getBigDecimal(columnName);
			}
			else if(java.sql.Date.class.isAssignableFrom(cls)){
				return rs.getDate(columnName);
			}
			else if(Float.class.isAssignableFrom(cls)){
				return rs.getFloat(columnName);
			}
			else if(Blob.class.isAssignableFrom(cls)){
				return rs.getBlob(columnName);
			}
			else if(Byte.class.isAssignableFrom(cls)){
				return rs.getByte(columnName);
			}
			else if(Short.class.isAssignableFrom(cls)){
				return rs.getShort(columnName);
			}
			else{
				return rs.getObject(columnName);
			}
			
		}catch(SQLException e){
			log.error("从结果集获取数据异常！ColumnName = " + columnName, e);
			throw new EasyPersistenceParameterException(e, "从结果集获取数据异常！");
		}
		
	}
	
	/**
	 * 获取结果集数据
	 * @param rs：结果集
	 * @param index : 索引下标
	 * @param type：参数类型,java.sql.Types中的类型
	 * @return
	 */
	public static Object getParameter(ResultSet rs, int index, int type){
		
		if(rs == null) return null;
		
		try{
			
			if(type == Types.VARCHAR){
				return rs.getString(index);
			}
			else if(type == Types.INTEGER){
				return rs.getInt(index);
			}
			else if(type == Types.BIGINT){
				return rs.getLong(index);
			}
			else if(type == Types.DOUBLE){
				return rs.getDouble(index);
			}
			else if(type == Types.TIMESTAMP){
				return rs.getTimestamp(index);
			}
			else if(type == Types.DECIMAL){
				return rs.getBigDecimal(index);
			}
			else if(type == Types.DATE){
				return rs.getDate(index);
			}
			else if(type == Types.CHAR){
				return rs.getString(index);
			}
			else if(type == Types.FLOAT){
				return rs.getFloat(index);
			}
			else if(type == Types.BLOB){
				return rs.getBlob(index);
			}
			else if(type == Types.CLOB){
				return rs.getString(index);
			}
			else if(type == Types.TINYINT){
				return rs.getInt(index);
			}
			else if(type == Types.SMALLINT){
				return rs.getInt(index);
			}
			else{
				return rs.getObject(index);
			}
			
		}catch(SQLException e){
			log.error("从结果集获取数据异常！ColumnIndex = " + index, e);
			throw new EasyPersistenceParameterException(e, "从结果集获取数据异常！");
		}
		
	}
	
	/**
	 * 获取存储过程返回参数
	 * @param cs
	 * @param parameterList
	 * @param param
	 */
	public static void getSPOutParameter(CallableStatement cs, List<SPParamItem> parameterList, SPParam param){
		
		for(int i = 0 ; i < parameterList.size() ; i++ ){
			
			SPParamItem item = parameterList.get(i);
			
			if(item.getParamType() == SPParamType.OUT){
				
				try {
					
					int type = param.getParam(item.getName()).getType();
					Object value = null;
					
					if(type == Types.VARCHAR){
						value = cs.getString(i + 1);
					}
					else if(type == Types.INTEGER){
						value = cs.getInt(i + 1);
					}
					else if(type == Types.BIGINT){
						value = cs.getLong(i + 1);
					}
					else if(type == Types.DOUBLE){
						value = cs.getDouble(i + 1);
					}
					else if(type == Types.TIMESTAMP){
						value = cs.getTimestamp(i + 1);
					}
					else if(type == Types.DECIMAL){
						value = cs.getBigDecimal(i + 1);
					}
					else if(type == Types.DATE){
						value = cs.getDate(i + 1);
					}
					else if(type == Types.CHAR){
						value = cs.getString(i + 1);
					}
					else if(type == Types.FLOAT){
						value = cs.getFloat(i + 1);
					}
					else if(type == Types.BLOB){
						value = cs.getBlob(i + 1);
					}
					else if(type == Types.CLOB){
						value = cs.getString(i + 1);
					}
					else if(type == Types.TINYINT){
						value = cs.getInt(i + 1);
					}
					else if(type == Types.SMALLINT){
						value = cs.getInt(i + 1);
					}
					else{
						value = cs.getObject(i + 1);
					}
					
					param.getParam(item.getName()).setValue(value);
					
				} catch (SQLException e) {
					log.error("获取存储过程参数异常！", e);
					throw new EasyPersistenceParameterException(e, "获取存储过程参数异常！");
				}
			}
			
		}
		
	}
	
	/**
	 * 获取对象属性值
	 * @param o
	 * @param fieldName
	 * @return
	 */
	public static Object getObjectFieldValue(Object o, String fieldName){
		MethodAccess methodAccess = AsmCache.getMethodAccess(o.getClass());
		StringBuilder methodName = new StringBuilder("get").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
		return methodAccess.invoke(o, methodName.toString());
	}
	
}
