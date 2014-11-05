/**
 * 
 */
package org.easyframework.web.mvc.validator;

import static org.easyframework.util.ParamUtil.empty;

import java.lang.reflect.Field;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.annotation.field.Require;
import org.easyframework.web.mvc.exception.EasyParameterException;

/**
 * 非空参数验证器
 * @author zhoupuyue
 * @date 2013-9-20
 */
@Validator
public class RequireValidator implements ParameterValidator {

	@Override
	public boolean validate(Field field, String value) {
		
		String name = field.getName();
		Require require = field.getAnnotation(Require.class);
		
		if(require == null) return true;
		
		if(value == null){
			if(empty(require.msg())){
				throw new EasyParameterException("参数{1}不允许为空！", name);
			}else{
				throw new EasyParameterException(require.msg());
			}
		}
		
		if(value.equals("") && !require.empty()){
			if(empty(require.msg())){
				throw new EasyParameterException("参数{1}不允许为空！", name);
			}else{
				throw new EasyParameterException(require.msg());
			}
		}
		
		return true;
		
	}
	
	@Override
	public int order() {
		return -400;
	}

}
