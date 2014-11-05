/**
 * 
 */
package org.easyframework.web.mvc;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.easyframework.web.mvc.exception.EasyRequestException;

/**
 * 上传文件
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class MultipartFile {

	private FileItem fileItem;
	
	private long size;
	
	private String fileName;

	/**
	 * @return the fileItem
	 */
	public FileItem getFileItem() {
		return fileItem;
	}

	/**
	 * @param fileItem the fileItem to set
	 */
	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public InputStream getInputStream(){
		try {
			return fileItem.getInputStream();
		} catch (IOException e) {
			throw new EasyRequestException(e, "获取上传文件输入流异常！");
		}
	}
	
}
