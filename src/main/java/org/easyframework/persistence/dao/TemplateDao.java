/**
 * 
 */
package org.easyframework.persistence.dao;

import static org.easyframework.persistence.jdbc.PersistenceParameterResolver.getSPOutParameter;
import static org.easyframework.persistence.jdbc.PrepareResolver.prepare;
import static org.easyframework.persistence.jdbc.PrepareResolver.prepareCall;
import static org.easyframework.persistence.jdbc.PrepareResolver.prepareInsert;
import static org.easyframework.persistence.jdbc.ResourceResolver.closeResource;
import static org.easyframework.persistence.jdbc.ResultResolver.getListResult;
import static org.easyframework.persistence.jdbc.ResultResolver.getMapResult;
import static org.easyframework.persistence.jdbc.ResultResolver.getObjectResult;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;
import org.easyframework.persistence.ConnectionFactory;
import org.easyframework.persistence.exception.EasyQueryException;
import org.easyframework.persistence.support.SPParam;
import org.easyframework.persistence.support.SPParamItem;

/**
 * DAO模板类
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class TemplateDao {
	
	public static final Log log = LogFactory.getLog(TemplateDao.class);
	
	private ConnectionFactory connectionFactory;
	
	public TemplateDao(ConnectionFactory connectionFactory){
		this.connectionFactory = connectionFactory;
	}

	/**
	 * 批量查询，返回POJO对象链表
	 * @param sql
	 * @param returnType
	 * @param parameterList : 参数链表
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Class<T> returnType, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = pstmt.executeQuery();
			return getListResult(rs, returnType);
			
		} catch (SQLException e) {
			log.error("执行查询SQL失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行查询SQL失败！");
		}finally{
			closeResource(pstmt, rs);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 批量查询，返回Map链表
	 * @param sql
	 * @param parameterList : 参数链表
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = pstmt.executeQuery();
			return getListResult(rs);
			
		} catch (SQLException e) {
			log.error("执行查询SQL失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行查询SQL失败！");
		}finally{
			closeResource(pstmt, rs);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 查询单条记录，返回POJO对象
	 * @param sql
	 * @param returnType
	 * @param parameterList : 参数链表
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> returnType, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = pstmt.executeQuery();
			return getObjectResult(rs, returnType);
			
		} catch (SQLException e) {
			log.error("执行查询SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行查询SQL失败！");
		}finally{
			closeResource(pstmt, rs);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 查询单条记录，返回Map对象
	 * @param sql
	 * @param returnType
	 * @param parameterList : 参数链表
	 * @return
	 */
	public Map<String,Object> queryForObject(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = pstmt.executeQuery();
			return getMapResult(rs);
			
		} catch (SQLException e) {
			log.error("执行查询SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行查询SQL失败！");
		}finally{
			closeResource(pstmt, rs);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 更新方法
	 * @param sql
	 * @param parameterList : 参数链表
	 */
	public int update(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		
		try {
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("执行更新SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行更新SQL失败！");
		}finally{
			closeResource(pstmt);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 删除方法
	 * @param sql
	 * @param parameterList : 参数链表
	 */
	public int delete(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		
		try {
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("执行删除SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行删除SQL失败！");
		}finally{
			closeResource(pstmt);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 插入数据方法
	 * @param sql
	 * @param parameterList : 参数链表
	 */
	public void insert(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		
		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("执行插入SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行插入SQL失败！");
		}finally{
			closeResource(pstmt);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 *  插入数据方法，返回自动生成的主键值
	 * @param sql
	 * @param parameterList : 参数链表
	 * @return
	 */
	public int insertAndGetKey(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepareInsert(connection, sql, parameterList);
		ResultSet rs = null;
		int id = -1;
		
		try {
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			return id;
			
		} catch (SQLException e) {
			log.error("执行插入SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行插入SQL失败！");
		}finally{
			closeResource(pstmt, rs);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 执行管理类SQL语句
	 * @param sql
	 * @param parameterList : 参数链表
	 */
	public void execute(String sql, List<Object> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		PreparedStatement pstmt = prepare(connection, sql, parameterList);
		
		try {
			pstmt.execute();
		} catch (SQLException e) {
			log.error("执行SQL失败,\n" + sql, e);
			throw new EasyException(e, "执行SQL失败！");
		}finally{
			closeResource(pstmt);
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 调用没有结果集返回的存储过程
	 * @param sql
	 * @param param
	 * @param parameterList
	 */
	public void callWithoutResultSet(String sql, SPParam param, List<SPParamItem> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		CallableStatement cs = prepareCall(connection, sql, parameterList);
		
		try {
			
			cs.execute();
			getSPOutParameter(cs, parameterList, param);
			
		} catch (SQLException e) {
			log.error("执行存储过程失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行存储过程失败！");
		}finally{
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 调用返回结果集的存储过程
	 * @param sql
	 * @param returnType
	 * @param param
	 * @param parameterList
	 * @return
	 */
	public <T> List<T> callWithListResultSet(String sql, Class<T> returnType, SPParam param, List<SPParamItem> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		CallableStatement cs = prepareCall(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = cs.executeQuery();
			getSPOutParameter(cs, parameterList, param);
			
			return getListResult(rs, returnType);
			
		} catch (SQLException e) {
			log.error("执行存储过程失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行存储过程失败！");
		}finally{
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 调用返回结果集的存储过程
	 * @param sql
	 * @param param
	 * @param parameterList
	 * @return
	 */
	public List<Map<String,Object>> callWithListResultSet(String sql, SPParam param, List<SPParamItem> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		CallableStatement cs = prepareCall(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = cs.executeQuery();
			getSPOutParameter(cs, parameterList, param);
			
			return getListResult(rs);
			
		} catch (SQLException e) {
			log.error("执行存储过程失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行存储过程失败！");
		}finally{
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 调用返回对象的存储过程
	 * @param sql
	 * @param returnType
	 * @param param
	 * @param parameterList
	 * @return
	 */
	public <T> T callWithObjectResultSet(String sql, Class<T> returnType, SPParam param, List<SPParamItem> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		CallableStatement cs = prepareCall(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = cs.executeQuery();
			getSPOutParameter(cs, parameterList, param);
			
			return getObjectResult(rs, returnType);
			
		} catch (SQLException e) {
			log.error("执行存储过程失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行存储过程失败！");
		}finally{
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
	/**
	 * 调用返回对象的存储过程
	 * @param sql
	 * @param param
	 * @param parameterList
	 * @return
	 */
	public Map<String,Object> callWithObjectResultSet(String sql, SPParam param, List<SPParamItem> parameterList){
		
		Connection connection = this.connectionFactory.getConnection();
		CallableStatement cs = prepareCall(connection, sql, parameterList);
		ResultSet rs = null;
		
		try {
			
			rs = cs.executeQuery();
			getSPOutParameter(cs, parameterList, param);
			
			return getMapResult(rs);
			
		} catch (SQLException e) {
			log.error("执行存储过程失败,\n" + sql, e);
			throw new EasyQueryException(e, "执行存储过程失败！");
		}finally{
			this.connectionFactory.closeConnection(connection);
		}
		
	}
	
}
