/**
 * 
 */
package org.easyframework.web.mvc.validator;

import static org.easyframework.util.StringUtil.arrayToString;
import static org.easyframework.util.StringUtil.include;
import static org.easyframework.util.ParamUtil.empty;

import java.lang.reflect.Field;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.annotation.field.Enums;
import org.easyframework.web.mvc.exception.EasyParameterException;

/**
 * 枚举类型参数验证器
 * @author zhoupuyue
 * @date 2013-9-20
 */
@Validator
public class EnumsValidator implements ParameterValidator {

	@Override
	public boolean validate(Field field, String value) {
		
		String name = field.getName();
		Enums enums = field.getAnnotation(Enums.class);
		
		if(enums == null) return true;
		
		if(!include(value, enums.value())){
			if(empty(enums.msg())){
				throw new EasyParameterException("参数{1}必须是{2}之一！", name, arrayToString(enums.value()));
			}else{
				throw new EasyParameterException(enums.msg());
			}
		}
		
		return true;
		
	}

	@Override
	public int order() {
		return -200;
	}
	
}
