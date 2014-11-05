/**
 * 
 */
package org.easyframework.web.mvc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.exception.EasyMappingScannerException;

/**
 * 扫描classpath中class、jar中的annotation
 * @author zhoupuyue
 * @date 2013-8-30
 */
public class ClassPathAnnotationScanner implements AnnotationScanner {
	
	public static final Log log = LogFactory.getLog(ClassPathAnnotationScanner.class);
	
	/**
	 * 扫描注解的基础包
	 */
	private List<String> basePackageList;
	
	/**
	 * 解析器链
	 */
	private AnnotationParserChain parserChain;
	
	public ClassPathAnnotationScanner(List<String> basePackageList, AnnotationParserChain parserChain){
		this.basePackageList = basePackageList;
		this.parserChain = parserChain;
	}

	/**
	 * 扫描包及子包下类中的注解
	 */
	@Override
	public void scan() {
		
		for(String basePackage : this.basePackageList){
			
			String packagePath = basePackage.replace(".", "/");
			
			try {
				
				Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
				
				while(urls.hasMoreElements()){
					
					URL url = urls.nextElement();
					String protocol = url.getProtocol();
					
					if("file".equals(protocol)){
						String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
						doScanPackageClassesByFile(basePackage, filePath, true);
					}else if("jar".equals(protocol)){
						doScanPackageClassesByJar(basePackage, url, true);
					}
					
				}
				
			} catch (IOException e) {
				log.error("读取文件异常！", e);
				throw new EasyMappingScannerException(e, "读取文件异常！");
			}
			
		}
		
	}
	
	/**
	 * 扫描classpath中的class文件
	 * @param packageName
	 * @param packagePath
	 */
	private void doScanPackageClassesByFile(String packageName, String packagePath, final boolean recursive){
		
		File dir = new File(packagePath);
		
		if(!dir.exists() || !dir.isDirectory()){
			return;
		}
		
		File[] files = dir.listFiles(new FileFilter(){
			public boolean accept(File file){
				if(file.isDirectory()){
					return recursive;
				}
				String fileName = file.getName();
				if(!fileName.endsWith(".class")){
					return false;
				}
				return true;
			}
		});
		
		for(int i = 0 ; i < files.length ; i++ ){
			
			File file = files[i];
			
			if(file.isDirectory()){
				doScanPackageClassesByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive);
			}else{
				
				String fileName = file.getName();
				String className = fileName.substring(0, fileName.length() - ".class".length());
				String fullClassName = packageName + "." + className;
				
				try {
					Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
					parse(clazz);
				} catch (ClassNotFoundException e) {
					log.error("类" + fullClassName +"不存在", e);
					throw new EasyMappingScannerException(e, "类" + fullClassName + "不存在");
				}
			}
		}
		
	}
	
	/**
	 * 扫描classpath中jar中的class文件
	 * @param packageName
	 * @param url
	 * @param recursive
	 */
	private void doScanPackageClassesByJar(String packageName, URL url, final boolean recursive){
		
		String packagePath = packageName.replace(".", "/");
		JarFile jar = null;
		
		try {
			jar = ((JarURLConnection)url.openConnection()).getJarFile();
		} catch (IOException e) {
			log.error("加载jar包异常！url=" + url.getFile(), e);
			throw new EasyMappingScannerException(e, "加载jar包异常！");
		}
		
		if(jar == null) return;
		
		Enumeration<JarEntry> entries = jar.entries();
		
		while(entries.hasMoreElements()){
			
			JarEntry entry = entries.nextElement();
			String name = entry.getName();

			if(!name.startsWith(packagePath) || entry.isDirectory() || !name.endsWith(".class")){
				continue;
			}
			
			String className = name.replace("/", ".");
			className = className.substring(0, className.length() - ".class".length());
			
			try {
				Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
				parse(clazz);
			} catch (ClassNotFoundException e) {
				log.error("类" + className +"不存在", e);
				throw new EasyMappingScannerException(e, "类" + className + "不存在");
			}
			
		}
		
	}
	
	/**
	 * 解析该类中的注释
	 * @param clazz
	 */
	private void parse(Class<?> clazz){
		this.parserChain.parseAnnotation(clazz);
	}

}
