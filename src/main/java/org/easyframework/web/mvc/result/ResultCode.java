package org.easyframework.web.mvc.result;

/**
 * 结果状态枚举类型,1:成功;0:失败;2:没权限
 * @author zhoupuyue
 * @date 2014-3-1 下午8:17:20
 */
public enum ResultCode {
	
	SUCCESS(1), FAILED(0), NO_PERMISSION(2);
	
	/**
	 * 结果状态代码
	 */
	private int value;
	
	private ResultCode(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}
