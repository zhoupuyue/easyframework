/**
 * 
 */
package org.easyframework.web.mvc.validator;

import static org.easyframework.util.DateUtil.parseDate;
import static org.easyframework.util.DateUtil.parseDateTime;
import static org.easyframework.util.ParamUtil.empty;

import java.lang.reflect.Field;

import org.easyframework.web.mvc.ParameterValidator;
import org.easyframework.web.mvc.annotation.Validator;
import org.easyframework.web.mvc.annotation.field.DateFormat;
import org.easyframework.web.mvc.exception.EasyParameterException;

/**
 * 日期类型参数验证器
 * @author zhoupuyue
 * @date 2013-9-20
 */
@Validator
public class DateFormatValidator implements ParameterValidator {

	@Override
	public boolean validate(Field field, String value) {
		
		String name = field.getName();
		DateFormat dateFormat = field.getAnnotation(DateFormat.class);
		
		if(dateFormat == null) return true;
		
		try{
			
			if(java.util.Date.class.isAssignableFrom(field.getType())){
				parseDateTime(value, dateFormat.format());
			}
			else if(java.sql.Date.class.isAssignableFrom(field.getType())){
				parseDate(value, dateFormat.format());
			}
			
		}catch(Exception e){
			if(empty(dateFormat.msg())){
				throw new EasyParameterException("参数{1}必须是{2}格式！", name, dateFormat.format());
			}else{
				throw new EasyParameterException(dateFormat.msg());
			}
		}
		
		return true;
		
	}
	
	@Override
	public int order() {
		return -100;
	}
	
}
