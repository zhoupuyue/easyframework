/**
 * 
 */
package org.easyframework.web.mvc.mapping;

/**
 * 拦截器映射配置类
 * @author zhoupuyue
 * @date 2013-8-28
 */
public class InterceptorMapping {

	/**
	 * 拦截路径
	 */
	private String path;
	
	/**
	 * 拦截器实现类
	 */
	private Class<?> clazz;
	
	/**
	 * 拦截器执行顺序(order越小优先级越高)
	 */
	private int order;					
	
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
