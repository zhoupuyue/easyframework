/**
 * 
 */
package org.easyframework.persistence.dao;

import static org.easyframework.persistence.jdbc.SQLPlaceholderResolver.resolve;
import static org.easyframework.persistence.jdbc.SQLPlaceholderResolver.resolveSp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.persistence.ConnectionFactory;
import org.easyframework.persistence.config.EasySQLHolder;
import org.easyframework.persistence.config.EasySqlMapConfig;
import org.easyframework.persistence.exception.EasyPersistenceConfigException;
import org.easyframework.persistence.support.SPParam;
import org.easyframework.persistence.support.SPParamItem;
import org.easyframework.persistence.support.SQLParam;

/**
 * 通用DAO类
 * @author zhoupuyue
 * @date 2013-9-16
 */
public class EasyDao {
	
	public static final Log log = LogFactory.getLog(EasyDao.class);

	/**
	 * Dao模板对象
	 */
	private TemplateDao template;
	
	/**
	 * 数据库连接工厂
	 */
	private ConnectionFactory connectionFactory;
	
	public EasyDao(){
		
	}
	
	/**
	 * @param connectionFactory
	 */
	public EasyDao(ConnectionFactory connectionFactory){
		this.connectionFactory = connectionFactory;
		this.template = new TemplateDao(connectionFactory);
	}
	
	/**
	 * 设置ConnectionFactory
	 * @param connectionFactory
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory){
		this.connectionFactory = connectionFactory;
		this.template = new TemplateDao(connectionFactory);
	}
	
	/**
	 * 获取数据库连接工厂实例
	 * @return
	 */
	public ConnectionFactory getConnectionFactory(){
		return this.connectionFactory;
	}
	
	/**
	 * 获取模板Dao实例
	 * @return
	 */
	public TemplateDao getTemplateDao(){
		return this.template;
	}
	
	/**
	 * 批量查询，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> List<T> queryForList(String id, Class<T> returnType, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.queryForList(sql, returnType, parameterList);
	}
	
	/**
	 * 批量查询，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> List<T> queryForList(String id, Class<T> returnType, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.queryForList(sql, returnType, parameterList);
	}
	
	/**
	 * 批量查询，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param map
	 * @return
	 */
	public <T> List<T> queryForList(String id, Class<T> returnType, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.queryForList(sql, returnType, parameterList);
	}
	
	/**
	 * 批量查询，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param params
	 * @return
	 */
	public <T> List<T> queryForList(String id, Class<T> returnType, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.queryForList(sql, returnType, parameterList);
	}
	
	/**
	 * 批量查询，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param returnType
	 * @return
	 */
	public <T> List<T> queryForList(String id, Class<T> returnType){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList);
		return template.queryForList(sql, returnType, parameterList);
	}
	
	/**
	 * 批量查询，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.queryForList(sql, parameterList);
	}
	
	/**
	 * 批量查询，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.queryForList(sql, parameterList);
	}
	
	/**
	 * 批量查询，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.queryForList(sql, parameterList);
	}
	
	/**
	 * 批量查询，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryForList(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.queryForList(sql, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> T queryForObject(String id, Class<T> returnType, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.queryForObject(sql, returnType, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> T queryForObject(String id, Class<T> returnType, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.queryForObject(sql, returnType, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param map
	 * @return
	 */
	public <T> T queryForObject(String id, Class<T> returnType, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.queryForObject(sql, returnType, parameterList);
	}
	
	/**
	 * 查询单条记录，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param returnType
	 * @param params
	 * @return
	 */
	public <T> T queryForObject(String id, Class<T> returnType, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.queryForObject(sql, returnType, parameterList);
	}
	
	/**
	 * 查询单条记录，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param returnType
	 * @return
	 */
	public <T> T queryForObject(String id, Class<T> returnType){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList);
		return template.queryForObject(sql, returnType, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryForObject(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.queryForObject(sql, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryForObject(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.queryForObject(sql, parameterList);
	}
	
	/**
	 * 查询单条记录，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 * @return
	 */
	public Map<String,Object> queryForObject(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.queryForObject(sql, parameterList);
	}
	
	/**
	 * 查询单条记录，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 * @return
	 */
	public Map<String,Object> queryForObject(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.queryForObject(sql, parameterList);
	}
	
	/**
	 * 更新方法，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public int update(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.update(sql, parameterList);
	}
	
	/**
	 * 更新方法，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public int update(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.update(sql, parameterList);
	}
	
	/**
	 * 更新方法，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 */
	public void update(String id, Map<String, Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		template.update(sql, parameterList);
	}
	
	/**
	 * 更新方法，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 */
	public int update(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.update(sql, parameterList);
	}
	
	/**
	 * 删除方法，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public int delete(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.delete(sql, parameterList);
	}
	
	/**
	 * 删除方法，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public int delete(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.delete(sql, parameterList);
	}
	
	/**
	 * 删除方法，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 */
	public int delete(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.delete(sql, parameterList);
	}
	
	/**
	 * 删除方法，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 */
	public int delete(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.delete(sql, parameterList);
	}
	
	/**
	 * 插入数据方法，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public void insert(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		template.insert(sql, parameterList);
	}
	
	/**
	 * 插入数据方法，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public void insert(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		template.insert(sql, parameterList);
	}
	
	/**
	 * 插入数据方法，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 * @return
	 */
	public void insert(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		template.insert(sql, parameterList);
	}
	
	/**
	 * 插入数据方法，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 * @return
	 */
	public void insert(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		template.insert(sql, parameterList);
	}
	
	/**
	 * 插入数据并返回自动生成的主键值，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public int insertAndGetKey(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		return template.insertAndGetKey(sql, parameterList);
	}
	
	/**
	 * 插入数据并返回自动生成的主键值，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 * @return
	 */
	public int insertAndGetKey(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		return template.insertAndGetKey(sql, parameterList);
	}
	
	/**
	 * 插入数据并返回自动生成的主键值，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 * @return
	 */
	public int insertAndGetKey(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		return template.insertAndGetKey(sql, parameterList);
	}
	
	/**
	 * 插入数据并返回自动生成的主键值，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 * @return
	 */
	public int insertAndGetKey(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		return template.insertAndGetKey(sql, parameterList);
	}
	
	/**
	 * 执行管理类SQL，参数是POJO对象类型和Number,Date,String类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public void execute(String id, Object param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param, parameterList);
		template.execute(sql, parameterList);
	}
	
	/**
	 * 执行管理类SQL，参数是SQLParam类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param param
	 */
	public void execute(String id, SQLParam param){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), param.getParamMap(), parameterList);
		template.execute(sql, parameterList);
	}
	
	/**
	 * 执行管理类SQL，参数是Map类型；支持#{name}参数占位符和${name}变量占位符
	 * @param id
	 * @param map
	 */
	public void execute(String id, Map<String,Object> map){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), map, parameterList);
		template.execute(sql, parameterList);
	}
	
	/**
	 * 执行管理类SQL，动态参数，SQL可以是"?"占位符和#{name}参数占位符，不支持${name}变量占位符
	 * @param id
	 * @param params
	 */
	public void execute(String id, Object ... params){
		List<Object> parameterList = new ArrayList<Object>();
		String sql = resolve(getSQLHolder(id), parameterList, params);
		template.execute(sql, parameterList);
	}
	
	/**
	 * 调用没有结果集返回的存储过程
	 * @param id
	 * @param param
	 */
	public void callWithoutResultSet(String id, SPParam param){
		List<SPParamItem> parameterList = new ArrayList<SPParamItem>();
		String sql = resolveSp(getSQLHolder(id), param, parameterList);
		template.callWithoutResultSet(sql, param, parameterList);
	}
	
	/**
	 * 调用有结果集返回集合的存储过程
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> List<T> callWithListResultSet(String id, Class<T> returnType, SPParam param){
		List<SPParamItem> parameterList = new ArrayList<SPParamItem>();
		String sql = resolveSp(getSQLHolder(id), param, parameterList);
		return template.callWithListResultSet(sql, returnType, param, parameterList);
	}
	
	/**
	 * 调用有结果集返回集合的存储过程
	 * @param id
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> callWithListResultSet(String id, SPParam param){
		List<SPParamItem> parameterList = new ArrayList<SPParamItem>();
		String sql = resolveSp(getSQLHolder(id), param, parameterList);
		return template.callWithListResultSet(sql, param, parameterList);
	}
	
	/**
	 * 调用有结果集返回对象的存储过程
	 * @param id
	 * @param returnType
	 * @param param
	 * @return
	 */
	public <T> T callWithObjectResultSet(String id, Class<T> returnType, SPParam param){
		List<SPParamItem> parameterList = new ArrayList<SPParamItem>();
		String sql = resolveSp(getSQLHolder(id), param, parameterList);
		return template.callWithObjectResultSet(sql, returnType, param, parameterList);
	}
	
	/**
	 * 调用有结果集返回对象的存储过程
	 * @param id
	 * @param param
	 * @return
	 */
	public Map<String,Object> callWithObjectResultSet(String id, SPParam param){
		List<SPParamItem> parameterList = new ArrayList<SPParamItem>();
		String sql = resolveSp(getSQLHolder(id), param, parameterList);
		return template.callWithObjectResultSet(sql, param, parameterList);
	}
	
	/**
	 * 获取ID对应的SQLHolder对象
	 * @param id
	 * @return
	 */
	private EasySQLHolder getSQLHolder(String id){
		EasySQLHolder sqlHolder = EasySqlMapConfig.getSqlConfig(id);
		if(sqlHolder == null){
			log.error("无法找到id=" + id + "对应的SQL语句！");
			throw new EasyPersistenceConfigException("无法找到id=" + id + "对应的SQL语句！");
		}
		return sqlHolder;
	}
	
}
