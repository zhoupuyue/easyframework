/**
 * 
 */
package org.easyframework.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.exception.EasyException;
import org.easyframework.persistence.config.EasyPersistenceConfig;


/**
 * 默认数据库连接工厂
 * @author zhoupuyue
 * @date 2013-4-28
 */
public class DefaultConnectionFactory implements ConnectionFactory {
	
	public static final Log log = LogFactory.getLog(DefaultConnectionFactory.class);
	
	/**
	 * 数据库连接池
	 */
	private DataSource dataSource;
	
	/**
	 * SQL配置文件路径
	 */
	private String configPath;
	
	/**
	 * 设置SQL配置文件路径，解析SQL配置文件
	 * @param configPath
	 */
	public DefaultConnectionFactory(String configPath){
		this.configPath = configPath;
		new EasyPersistenceConfig(configPath);
	}
	
	/**
	 * 从连接池获取数据库连接
	 * @return
	 */
	public Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("从连接池获取数据库连接失败！", e);
			throw new EasyException(e, "服务器繁忙！");
		}
	}
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 */
	public void closeConnection(Connection connection){
		if(connection == null) return;
		try {
			connection.close();
		} catch (SQLException e) {
			log.error("关闭数据库连接异常！", e);
		}
	}
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * @return the configPath
	 */
	public String getConfigPath() {
		return configPath;
	}
	
	/**
	 * 设置SQL配置文件路径
	 * @param configPath
	 */
	public void setConfigPath(String configPath){
		this.configPath = configPath;
	}
	
}
