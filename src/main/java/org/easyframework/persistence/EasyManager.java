/**
 * 
 */
package org.easyframework.persistence;

import java.lang.reflect.Method;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.persistence.dao.EasyDao;
import org.easyframework.persistence.dao.TemplateDao;
import org.easyframework.persistence.exception.EasyGetClassMethodException;
import org.easyframework.persistence.exception.EasyInvokeMethodException;
import org.easyframework.persistence.exception.EasyPersistenceConfigException;

/**
 * Dao工具类
 * @author zhoupuyue
 * @date 2013-10-15
 */
public class EasyManager {
	
	public static final Log log = LogFactory.getLog(EasyManager.class);

	/**
	 * ConnectionFactory配置
	 */
	private static ConnectionFactory connectionFactory;
	
	/**
	 * EasyDao实例
	 */
	private static EasyDao easyDao;
	
	/**
	 * TemplateDao实例
	 */
	private static TemplateDao templateDao;
	
	/**
	 * 关闭数据库连接池方法名称
	 */
	private static String closeMethod = "close";
	
	/**
	 * 设置ConnectionFactory
	 * @param connectionFactory
	 */
	public static void setConnectionFactory(ConnectionFactory connectionFactory){
		EasyManager.connectionFactory = connectionFactory;
		templateDao = new TemplateDao(connectionFactory);
		easyDao = new EasyDao(connectionFactory);
	}
	
	/**
	 * 关闭数据库连接池
	 */
	public static void closeDataSource(){
		
		if(connectionFactory == null){
			log.error("connectionFactory未配置！");
			throw new EasyPersistenceConfigException("connectionFactory未配置！");
		}
		
		Method method = null;
		
		try {
			method = connectionFactory.getDataSource().getClass().getMethod(closeMethod);
		} catch (NoSuchMethodException e) {
			log.error("数据库连接池没有" + closeMethod + "方法！", e);
			throw new EasyGetClassMethodException(e, "数据库连接池没有{1}方法！", closeMethod);
		} catch (SecurityException e) {
			log.error("没有权限获取类" + connectionFactory.getDataSource().getClass().getCanonicalName() + "的" + closeMethod + "方法！", e);
			throw new EasyGetClassMethodException(e, "没有权限获取类{1}的{2}方法！", connectionFactory.getDataSource().getClass().getCanonicalName(), closeMethod);
		}
		
		try {
			method.invoke(connectionFactory.getDataSource());
		} catch (Exception e) {
			log.error("反射方式调用类" + connectionFactory.getDataSource().getClass().getCanonicalName() + "的" + closeMethod + "方法异常！", e);
			throw new EasyInvokeMethodException(e, "反射调用类{1}的方法{2}异常！", connectionFactory.getDataSource().getClass().getCanonicalName(), closeMethod);
		}
		
	}
	
	/**
	 * 设置关闭数据源方法
	 */
	public static void setCloseMethod(String closeMethod) {
		EasyManager.closeMethod = closeMethod;
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		return connectionFactory.getConnection();
	}
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 */
	public static void closeConnection(Connection connection){
		connectionFactory.closeConnection(connection);
	}
	
	/**
	 * 获取EasyDao实例
	 * @return
	 */
	public static EasyDao getEasyDao(){
		
		if(connectionFactory == null){
			log.error("connectionFactory未配置！");
			throw new EasyPersistenceConfigException("connectionFactory未配置！");
		}
		
		return easyDao;
		
	}
	
	/**
	 * 获取TemplateDao实例
	 * @return
	 */
	public static TemplateDao getTemplateDao(){
		
		if(connectionFactory == null){
			log.error("connectionFactory未配置！");
			throw new EasyPersistenceConfigException("connectionFactory未配置！");
		}
		
		return templateDao;
		
	}
	
}
