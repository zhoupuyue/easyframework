/**
 * 
 */
package org.easyframework.web.mvc.validator;

import java.lang.reflect.Field;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.annotation.field.Exclude;

/**
 * 非参数类型验证器
 * @author zhoupuyue
 * @date 2013-9-20
 */
@Validator
public class ExcludeValidator implements ParameterValidator {

	@Override
	public boolean validate(Field field, String value) {
		
		Exclude exclude = field.getAnnotation(Exclude.class);
		
		if(exclude == null) return true;
		
		return false;
		
	}
	
	@Override
	public int order() {
		return -500;
	}

}
