package com.chm.util;

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
		
		return false;
	}
	
	public static void main(String[] args) {
		
		String a = "ssds";
		System.out.println(isBasicTypes(a.getClass()));
	}

}
