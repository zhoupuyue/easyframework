/**
 * 
 */
package org.easyframework.web.mvc.validator;

import static org.easyframework.util.ParamUtil.empty;

import java.lang.reflect.Field;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.annotation.field.Digit;
import org.easyframework.web.mvc.exception.EasyParameterException;

/**
 * 数字类型参数验证器
 * @author zhoupuyue
 * @date 2013-9-20
 */
@Validator
public class DigitValidator implements ParameterValidator {

	@Override
	public boolean validate(Field field, String value) {
		
		String name = field.getName();
		Digit digit = field.getAnnotation(Digit.class);
		
		if(digit == null) return true;
		
		if(empty(value)) return true;
		
		char[] chars = value.toCharArray();
		
		for(int j = 0 ; j < chars.length ; j++){
			
			char c = chars[j];
			
			//负号
			if(j == 0 && c == 45) continue;
			
			//数字和小数点以外的字符
			if(c > 57 || (c < 48 && c != 46)){
				if(empty(digit.msg())){
					throw new EasyParameterException("参数{1}必须为数字类型！", name);
				}else{
					throw new EasyParameterException(digit.msg());
				}
			}
		}
		
		return true;
		
	}
	
	@Override
	public int order() {
		return -300;
	}

}
