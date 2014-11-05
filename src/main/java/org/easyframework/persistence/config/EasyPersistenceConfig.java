/**
 * 
 */
package org.easyframework.persistence.config;

import static org.easyframework.util.ParamUtil.empty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.persistence.exception.EasyPersistenceConfigException;

/**
 * 配置处理类
 * @author zhoupuyue
 * @date 2013-9-13
 */
public class EasyPersistenceConfig {
	
	public static final Log log = LogFactory.getLog(EasyPersistenceConfig.class);
	
	private String configPath;

	public EasyPersistenceConfig(String configPath){
		this.configPath = configPath;
		this.build();
	}
	
	/**
	 * 解析配置文件路径
	 */
	public void build(){
		
		if(empty(configPath)) return;
		
		configPath = configPath.replace("\\", "/");
		if(configPath.startsWith("/")) configPath = configPath.substring(1);
		
		String path = "";
		try {
			path = this.getClass().getClassLoader().getResource("").toURI().getPath() + configPath;
		} catch (URISyntaxException e1) {
			log.error("路径格式错误！", e1);
		}
		
		File file = new File(path);
		
		//从jar中读取配置文件
		if(!file.exists()){
			
			URL url = this.getClass().getClassLoader().getResource(configPath);
			if(url == null) throw new EasyPersistenceConfigException("配置文件路径错误，文件不存在！");
			
			path = url.toString();
			String jarPath = path.substring(0, path.indexOf("!/") + 2);
			URL jarURL = null;
			try {
				jarURL = new URL(jarPath);
			} catch (MalformedURLException e) {
				log.error("配置文件路径协议错误！", e);
				throw new EasyPersistenceConfigException("配置文件路径协议错误！");
			}
			
			try{
				
				JarURLConnection jarCon = (JarURLConnection)jarURL.openConnection();
				JarFile jarFile = jarCon.getJarFile();
				Enumeration<JarEntry> jarEntrys = jarFile.entries(); 
				
				while(jarEntrys.hasMoreElements()){
					JarEntry entry = jarEntrys.nextElement();
					if(!entry.isDirectory() && entry.getName().startsWith(configPath)){
						InputStream is = this.getClass().getClassLoader().getResourceAsStream(entry.getName());
						EasyPersistenceConfigParser.parse(is);
					}
				}
				
			}catch(IOException e){
				log.error("解析配置文件异常！", e);
				throw new EasyPersistenceConfigException(e, "解析配置文件异常！");
			}
			
		}else{
			
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i = 0 ; i < files.length ; i++ ){
					if(files[i].getName().endsWith(".xml")) EasyPersistenceConfigParser.parse(files[i]);
				}
			}else{
				EasyPersistenceConfigParser.parse(file);
			}
			
		}
		
	}
	
}
