package com.chm.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;


public class BeanUtil {
	
	
	
	/**
	 * 基本类型赋值 
	 * @param obj
	 * @param value
	 */
	public static void setProperty(Object obj,Object value){
		setProperty(obj, null, value);
	}
	/**
	 * 给obj对象赋值
	 * @param obj obj
	 * @param propertyName 字段名
	 * @param value 字段值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setProperty(Object obj,String propertyName,Object value){
		
		if(obj instanceof Map){
			((Map)obj).put(propertyName,value);
		}else if(obj instanceof Integer){
			obj = Integer.valueOf(StringUtil.toString(value));
		}else if(obj instanceof Float){
			obj = Float.valueOf(StringUtil.toString(value));
		}else if(obj instanceof Double){
			obj = Double.valueOf(StringUtil.toString(value));
		}else if(obj instanceof Short){
			obj = Short.valueOf(StringUtil.toString(value));
		}else if(obj instanceof String){
			obj = StringUtil.toString(value);
		}else if(obj instanceof Date){
			obj = DateUtil.getDateFromStr(StringUtil.toString(value));
		}else{
			 try {
				PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
				Method method = pd.getWriteMethod();
				method.invoke(obj, value);
			 } catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	/**
	 * 判断一个对象是否是基本类型
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(Class<?> cla){
		if(cla.equals(String.class)){
			return true;
		}
		return cla.isPrimitive();
	}
	/**
	 * 判断一个对象是否是基本类型
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(Object obj){
		
		if(obj instanceof String){
			return true;
		}
		if(obj instanceof Number){
			return true;
		}
		if(obj instanceof Boolean){
			return true;
		}
		if(obj instanceof Character){
			return true;
		}
		return false;
	}
	/**
	 * 判断一个对象是不是 无属性的简单对象 例如Date Time
	 * @param cla
	 * @return
	 */
	public static boolean isNofieldType(Class<?> cla){
		try {
			if(isPrimitive(cla)){
				return true;
			}
			Object obj = cla.newInstance();
			return isNofieldType(obj);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断一个对象是不是 无属性的简单对象 例如Date Time
	 * @param obj 待补充
	 * @return
	 */
	public static boolean isNofieldType(Object obj){
		
		if(obj instanceof Date){
			return true;
		}
		if(obj instanceof String){
			return true;
		}
		return false;
	}
	

	
	public static void main(String[] args) {
		
	}

}
