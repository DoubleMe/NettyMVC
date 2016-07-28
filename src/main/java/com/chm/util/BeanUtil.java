package com.chm.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;

public class BeanUtil {
	
	
	
	/**
	 * 判断一个类是不是基本类型
	 * @param clazz
	 * @return
	 */
	public static boolean isBasicTypes(Class clazz){
		
		if(clazz == String.class){
			return true;
		}
		if(clazz.isPrimitive()){
			return true;
		}
		if(clazz == java.util.Date.class){
			return true;
		}
		return false;
	}
	
	public static Object newInstanceAndSetValue(Class clazz,String targetValue){
		
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setProperty(Class<?> cla,String propertyName,Object value){
		Object obj = null;
		try {
			obj = cla.newInstance();
			if(cla.equals(HashMap.class)){
				((Map)obj).put(propertyName,value);
			}else{
				String propTmp=propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
				try {
					Method method=BeanUtils.findDeclaredMethodWithMinimalParameters(obj.getClass(), "set"+propTmp);
					method.invoke(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * 判断一个对象是否是基本类型
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(Class<?> cla){
		try {
			Object obj = cla.newInstance();
			return isPrimitive(obj);
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
		return false;
	}
	

	
	public static void main(String[] args) {
		
		String a = "ssds";
		System.out.println(isBasicTypes(int.class));
	}

}
