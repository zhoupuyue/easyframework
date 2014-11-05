package org.easyframework.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库资源处理类
 * @author zhoupuyue
 * @date 2013-4-22
 */
public class ResourceResolver {
	
	public static final Log log = LogFactory.getLog(ResourceResolver.class);
	
	/**
	 * 关闭数据库资源
	 * @param stmt
	 * @param rs
	 */
	public static void closeResource(Statement stmt, ResultSet rs){
		closeResource(rs);
		closeResource(stmt);
	}
	
	/**
	 * 关闭数据库资源
	 * @param rs
	 */
	public static void closeResource(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("关闭结果集异常！", e);
			}
		}
	}
	
	/**
	 * 关闭数据库资源
	 * @param stmt
	 */
	public static void closeResource(Statement stmt){
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("关闭Statement异常！", e);
			}
		}
	}
	
}
