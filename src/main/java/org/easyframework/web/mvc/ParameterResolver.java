/**
 * 
 */
package org.easyframework.web.mvc;

import static org.easyframework.util.DateUtil.parseDate;
import static org.easyframework.util.DateUtil.parseDateTime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyframework.asm.AsmCache;
import org.easyframework.web.mvc.annotation.field.DateFormat;
import org.easyframework.web.mvc.annotation.field.Param;
import org.easyframework.web.mvc.context.ApplicationContext;
import org.easyframework.web.mvc.context.RequestContext;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * 处理参数
 * @author zhoupuyue
 * @date 2013-8-27
 */
public class ParameterResolver {
	
	public static Log log = LogFactory.getLog(ParameterResolver.class);

	/**
	 * 返回参数值列表
	 * @param parameterTypes : 参数类型数组
	 * @param params : 参数值Map
	 * @param annotations : 参数注解
	 * @return
	 */
	public static List<Object> getParameters(Class<?>[] parameterTypes, Annotation[][] annotations, Map<String,String> params){
		
		List<Object> parameters = new ArrayList<Object>();
		
		if(parameterTypes != null && parameterTypes.length > 0){
			
			for(int i = 0 ; i < parameterTypes.length ; i++){
				
				Class<?> clazz = parameterTypes[i];
				
				if(HttpServletRequest.class.isAssignableFrom(clazz)){
					parameters.add(RequestContext.getRequest());
				}
				else if(HttpServletResponse.class.isAssignableFrom(clazz)){
					parameters.add(RequestContext.getResponse());
				}
				else if(HttpSession.class.isAssignableFrom(clazz)){
					parameters.add(RequestContext.getRequest().getSession());
				}
				else if(Map.class.isAssignableFrom(clazz)){
					parameters.add(params);
				}
				else if(MultipartFile.class.isAssignableFrom(clazz)){
					parameters.add(RequestContext.getMultipartFile());
				}
				else if(annotations[i].length > 0){
					parameters.add(getParameterValue(annotations[i], clazz, params));
				}
				else{
					parameters.add(getParameterObject(clazz, params));
				}
			}
		}
		
		return parameters;
		
	}
	
	/**
	 * 获取配置注解基本类型参数值，暂时只支持Param注解
	 * @param annotations : 参数注解数组
	 * @param type : 参数类型
	 * @param map : 参数值Map
	 * @return
	 */
	public static Object getParameterValue(Annotation[] annotations, Class<?> type, Map<String,String> map){
		
		if(annotations == null || annotations.length == 0) return null;
		
		for(int i = 0 ; i < annotations.length ; i++ ){
			
			Annotation annotation = annotations[i];
			
			if(annotation instanceof Param){
				
				Param param = (Param)annotation;
				String paramName = param.value();
				String value = map.get(paramName);
				
				if(value == null){
					return null;
				}
				
				if(String.class.isAssignableFrom(type)){
					return value;
				}
				else if(value.trim().equals("")){
					return null;
				}
				else if(Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)){
					return Integer.parseInt(value);
				}
				else if(Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)){
					return Long.parseLong(value);
				}
				else if(Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)){
					return Double.parseDouble(value);
				}
				else if(Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)){
					return Float.parseFloat(value);
				}
				else if(Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type)){
					return Short.parseShort(value);
				}
				
			}
		}
		
		return null;
		
	}
	
	/**
	 * 创建参数类对象，并设置参数
	 * 在VO类属性加上注释，自动进行空值检查或者格式检查
	 * @param clazz : 参数类型
	 * @param map : 参数值Map
	 * @return
	 */
	public static Object getParameterObject(Class<?> clazz, Map<String,String> map){
		
		Object o = AsmCache.getConstructorAccess(clazz).newInstance();
		
		Field[] fields = clazz.getDeclaredFields();
		
		List<ParameterValidator> validatorList = ApplicationContext.getValidators();
		
		MethodAccess methodAccess = AsmCache.getMethodAccess(o.getClass());
		
		for(int i = 0 ; i < fields.length ; i++ ){
			
			Field field = fields[i];
			
			String fieldName = field.getName();
			String value = map.get(fieldName);
			
			//验证参数
			for(int j = 0 ; j < validatorList.size() ; j++ ){
				ParameterValidator validator = validatorList.get(j);
				if(!validator.validate(field, value)){
					break;
				}
			}
			
			if(value == null) continue;
			
			StringBuilder methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
			
			if(String.class.isAssignableFrom(field.getType())){
				methodAccess.invoke(o, methodName.toString(), value);
			}
			else if(value.trim().equals("")){
				continue;
			}
			else if(Integer.class.isAssignableFrom(field.getType()) || int.class.isAssignableFrom(field.getType())){
				methodAccess.invoke(o, methodName.toString(), Integer.parseInt(value));
			}
			else if(Long.class.isAssignableFrom(field.getType()) || long.class.isAssignableFrom(field.getType())){
				methodAccess.invoke(o, methodName.toString(), Long.parseLong(value));
			}
			else if(Double.class.isAssignableFrom(field.getType()) || double.class.isAssignableFrom(field.getType())){
				methodAccess.invoke(o, methodName.toString(), Double.parseDouble(value));
			}
			else if(Float.class.isAssignableFrom(field.getType()) || float.class.isAssignableFrom(field.getType())){
				methodAccess.invoke(o, methodName.toString(), Float.parseFloat(value));
			}
			else if(java.sql.Date.class.isAssignableFrom(field.getType())){
				DateFormat dateFormat = field.getAnnotation(DateFormat.class);
				if(dateFormat != null){
					methodAccess.invoke(o, methodName.toString(), parseDate(value, dateFormat.format()));
				}else{
					methodAccess.invoke(o, methodName.toString(), parseDate(value));
				}
			}
			else if(java.util.Date.class.isAssignableFrom(field.getType())){
				DateFormat dateFormat = field.getAnnotation(DateFormat.class);
				if(dateFormat != null){
					methodAccess.invoke(o, methodName.toString(), parseDateTime(value, dateFormat.format()));
				}else{
					methodAccess.invoke(o, methodName.toString(), parseDateTime(value));
				}
			}
			
		}
		
		return o;
		
	}
}
