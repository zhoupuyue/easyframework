package org.easyframework.persistence.jdbc;

import static org.easyframework.persistence.jdbc.PersistenceParameterResolver.setParameter;
import static org.easyframework.persistence.jdbc.PersistenceParameterResolver.setSPParameter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;
import org.easyframework.persistence.support.SPParamItem;

/**
 * 准备数据库资源处理类
 * @author zhoupuyue
 * @date 2013-4-22
 */
public class PrepareResolver {
	
	public static final Log log = LogFactory.getLog(PrepareResolver.class);
	
	/**
	 * 执行数据库操作之前的准备
	 * @param sql : SQL语句
	 * @param args : 执行SQL需要的参数
	 * @return
	 */
	public static PreparedStatement prepare(Connection conn, String sql, Object ...args){
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			log.error("解析SQL异常,\n{1}" + sql, e);
			throw new EasyException(e, "解析SQL异常！");
		}
		
		if(args != null && args.length > 0){
			for(int i = 0 ; i < args.length ; i++){
				setParameter(pstmt, i+1 , args[i]);
			}
		}
		
		return pstmt;
		
	}
	
	/**
	 * 执行数据库操作之前的准备
	 * @param sql : SQL语句
	 * @param parameterList : 执行SQL需要的参数
	 * @return
	 */
	public static PreparedStatement prepare(Connection conn, String sql, List<Object> parameterList){
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			log.error("解析SQL异常,\n{1}" + sql, e);
			throw new EasyException(e, "解析SQL异常！");
		}
		
		if(parameterList != null && parameterList.size() > 0){
			for(int i = 0 ; i < parameterList.size() ; i++){
				setParameter(pstmt, i+1 , parameterList.get(i));
			}
		}
		
		return pstmt;
		
	}
	
	/**
	 * 执行数据库存储过程之前的准备
	 * @param sql : SQL语句
	 * @param args : 执行SQL需要的参数
	 * @return
	 */
	public static CallableStatement prepareCall(Connection conn, String sql, List<SPParamItem> parameterList){
		
		CallableStatement cs = null;
		
		try {
			cs = conn.prepareCall(sql);
		} catch (SQLException e) {
			log.error("解析SQL异常,\n" + sql, e);
			throw new EasyException(e, "解析SQL异常！");
		}
		
		if(parameterList.size() > 0){
			for(int i = 0 ; i < parameterList.size() ; i++){
				setSPParameter(cs, i + 1 , parameterList.get(i));
			}
		}
		
		return cs;
		
	}
	
	/**
	 * 执行插入操作之前的准备，返回生成的主键值
	 * @param sql : SQL语句
	 * @param args : 执行SQL需要的参数
	 * @return
	 */
	public static PreparedStatement prepareInsert(Connection conn, String sql, List<Object> parameterList){
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			log.error("解析SQL异常,\n{1}" + sql, e);
			throw new EasyException(e, "解析SQL异常！");
		}
		
		if(parameterList != null && parameterList.size() > 0){
			for(int i = 0 ; i < parameterList.size() ; i++){
				setParameter(pstmt, i+1 , parameterList.get(i));
			}
		}
		
		return pstmt;
		
	}
	
}
