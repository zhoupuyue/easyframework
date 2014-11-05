/**
 * 
 */
package org.easyframework.persistence.config;

import static org.easyframework.util.ParamUtil.empty;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyframework.persistence.exception.EasyPersistenceConfigException;

/**
 * 配置文件解析器
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyPersistenceConfigParser {
	
	public static final Log log = LogFactory.getLog(EasyPersistenceConfigParser.class);
	
	/**
	 * 解析配置文件
	 * @param is
	 */
	public static void parse(InputStream is){
		
		Document document = null;
		
		try {
			document = new SAXReader().read(is);
		} catch (DocumentException e) {
			log.error("读取配置文件异常！", e);
			throw new EasyPersistenceConfigException(e, "读取配置文件异常！");
		}
		
		parse(document);
		
	}

	/**
	 * 解析配置文件
	 */
	public static void parse(File file){
		
		Document document = null;
		
		try {
			document = new SAXReader().read(file);
		} catch (DocumentException e) {
			log.error("读取配置文件异常，filepath=" + file.getAbsolutePath(), e);
			throw new EasyPersistenceConfigException(e, "读取配置文件异常！");
		}
		
		parse(document);
		
	}
	
	/**
	 * 解析配置文件元素
	 * @param document
	 */
	private static void parse(Document document){
		
		if(document == null) return;
		
		Element root = (Element)document.selectSingleNode("/config");
		String namespace = root.attributeValue("namespace");
		
		if(empty(namespace)){
			namespace = "";
		}else{
			namespace += ".";
		}
		
		List<?> list = document.selectNodes("/config/sql");
		
		if(empty(list)) return;
		
		for(int i = 0 ; i < list.size() ; i++ ){
			
			Element element = (Element)list.get(i);
			
			String id = element.attributeValue("id");
			String sql = element.getTextTrim();
			
			if(EasySqlMapConfig.getSqlConfig(namespace + id) != null){
				throw new EasyPersistenceConfigException("SQL配置错误，\"" + namespace + id + "\"已存在！");
			}
			
			EasySQLHolder sqlHolder = new EasySQLHolder(namespace + id, sql);
			sqlHolder.compile();
			
			EasySqlMapConfig.addSqlConfig(namespace + id, sqlHolder);
			
			log.info("加载SQL配置，id：" + namespace + id + "，sql：" + sql);
			
		}
		
	}
	
}
