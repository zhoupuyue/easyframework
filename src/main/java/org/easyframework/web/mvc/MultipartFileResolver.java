/**
 * 
 */
package org.easyframework.web.mvc;

import static org.easyframework.util.ParamUtil.empty;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.web.mvc.context.ApplicationContext;
import org.easyframework.web.mvc.exception.EasyRequestException;

/**
 * 文件上传处理器
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class MultipartFileResolver {
	
	public static final Log log = LogFactory.getLog(MultipartFileResolver.class);
	
	/**
	 * 最大存放临时文件内存大小,10M
	 */
	public static final int MAX_MEMORY_SIZE = 1024*1024*10;
	
	/**
	 * 最大上传文件大小，100M
	 */
	public static final int MAX_FILE_SIZE = 1024*1024*100;
	
	public MultipartFile resolve(HttpServletRequest request, Map<String,String> params) {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if(!isMultipart) return null;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//设置上传文件时用于临时存放文件的内存大小，这里是10M
		factory.setSizeThreshold(MAX_MEMORY_SIZE);
		
		//设置存放临时文件目录，这里是web根目录下的upload目录
		factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/") + "tmp"));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		
		List<FileItem> fileItemList = null;
		try {
			fileItemList = (List<FileItem>)upload.parseRequest(request);
		} catch (FileUploadException e) {
			log.error("上传文件失败，文件尺寸超过最大尺寸" + MAX_FILE_SIZE + "字节！", e);
			throw new EasyRequestException("上传文件失败，文件尺寸超过最大尺寸" + MAX_FILE_SIZE + "字节！");
		}
		
		if(fileItemList == null || fileItemList.size() == 0){
			return null;
		}
		
		MultipartFile multipartFile = null;
		
		for(FileItem item : fileItemList){
			
			if(item.isFormField()){
				try {
					params.put(item.getFieldName(), item.getString(ApplicationContext.REQUEST_ENCODING));
				} catch (UnsupportedEncodingException e) {
					log.error("不支持的编码！encoding-" + ApplicationContext.REQUEST_ENCODING, e);
					throw new EasyRequestException("不支持的编码！encoding=" + ApplicationContext.REQUEST_ENCODING);
				}
				continue;
			}
			
			String path = item.getName();
			
			if(empty(path)) continue;
			
			path = path.replace("\\", "/");
			
			multipartFile = new MultipartFile();
			multipartFile.setFileItem(item);
			multipartFile.setSize(item.getSize());
			
			String name = "";
			if(path.lastIndexOf("/") != -1){
				name = path.substring(path.lastIndexOf("/") + 1);
			}else{
				if(!empty(path))name = path;
			}
			
			multipartFile.setFileName(name);
			
		}
		
		return multipartFile;
		
	}
	
}
