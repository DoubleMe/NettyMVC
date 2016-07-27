package com.chm.SpringContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * spring容器初始化类，并提供从容器中获取对象的方法
 * @author chen-hongmin
 *
 */
public class SpringContext {

	
	private static ApplicationContext applicationContext;
	
	public static void initSpringContext() {

		String[] configs = { "classpath*:spring/applicationContext*.xml" };
		applicationContext = new FileSystemXmlApplicationContext(configs);
	}
	/**
	 * 获取spring 容器里面的bean
	 * @param beanName
	 * @return Object
	 */
	public static Object getBean(String beanName) {
		
		try{
			return applicationContext.getBean(beanName);
		}catch(BeansException e){
			return null;
		}
		
	}
	/**
	 * 获取spring 容器里面的bean
	 * @param beanName
	 * @return Object
	 */
	public static Object getBean(Class<?> cla) {
		
		try{
			return applicationContext.getBean(cla);
		}catch(BeansException e){
			return null;
		}
	}
}
