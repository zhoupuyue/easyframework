package org.easyframework.persistence;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * 数据库连接工厂
 * @author zhoupuyue
 * @date 2013-4-22
 */
public interface ConnectionFactory {
	
	/**
	 * 设置数据源
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource);
	
	/**
	 * 获取数据源
	 * @return
	 */
	public DataSource getDataSource();
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 */
	public void closeConnection(Connection connection);
	
	/**
	 * 获取配置文件路径
	 * @return
	 */
	public String getConfigPath();
	
	/**
	 * 设置SQL配置文件路径
	 * @param configPath
	 */
	public void setConfigPath(String configPath);

}
