/**
 * 
 */
package org.easyframework.web.mvc.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.mapping.ViewPathMapping;

/**
 * 应用上下文
 * @author zhoupuyue
 * @date 2013-9-2
 */
public class ApplicationContext {
	
	/**
	 * 编码
	 */
	public static String REQUEST_ENCODING = "UTF-8";
	
	/**
	 * 视图路径配置
	 */
	private static ViewPathMapping viewPathMapping;
	
	/**
	 * 参数验证器列表
	 */
	private static List<ParameterValidator> validatorList = new ArrayList<ParameterValidator>();

	
	/**
	 * 返回视图路径配置
	 * @return
	 */
	public static ViewPathMapping getViewPathMapping() {
		return viewPathMapping;
	}

	/**
	 * 设置视图路径配置
	 * @param viewPathMapping
	 */
	public static void setViewPathMapping(ViewPathMapping viewPathMapping) {
		ApplicationContext.viewPathMapping = viewPathMapping;
	}
	
	/**
	 * 添加参数验证器
	 * @param validator
	 */
	public static void addValidator(ParameterValidator validator){
		validatorList.add(validator);
	}
	
	/**
	 * 返回所有参数验证器
	 * @return
	 */
	public static List<ParameterValidator> getValidators(){
		return validatorList;
	}
	
	/**
	 * 对参数验证器按order值从小到大排序
	 */
	public static void sortValidators(){
		
		if(validatorList.size() < 2) return;
		
		ParameterValidator[] validators = validatorList.toArray(new ParameterValidator[]{});
		
		for(int i = 0 ; i < validators.length - 1 ; i++ ){
			
			for(int j = i + 1 ; j < validators.length ; j++ ){
				
				if(validators[i].order() > validators[j].order()){
					ParameterValidator tmp = validators[i];
					validators[i] = validators[j];
					validators[j] = tmp;
				}
				
			}
			
		}
		
		validatorList = Arrays.asList(validators);
		
	}
	
}
