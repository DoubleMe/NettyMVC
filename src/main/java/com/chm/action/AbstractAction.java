package com.chm.action;

import java.lang.reflect.Method;

import com.chm.annotation.RequestMapping;
import com.chm.cache.ActionCache;
import com.chm.exception.UrlException;
import com.chm.model.ActionCompent;
import com.chm.parameter.impl.ParameterManagerImpl;
import com.chm.resolver.UrlResolver;

/**
 * ACTION基类 所有的ACTION都继承这个类
 * 
 * 用于构建Action
 * @author chen-hongmin
 *
 */
public abstract class AbstractAction {
	
	private ParameterManagerImpl parameterManager = new ParameterManagerImpl();
	/**
	 * 向注册中心注册Action 初始化Action方法
	 * @throws UrlException 
	 */
	public AbstractAction(){
		register();
	}
	
	public void register(){
		if(this.getClass().isAnnotationPresent(RequestMapping.class)){
			try {
				buildAction(this);
			} catch (UrlException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 构建一个Action,并初始化到缓存
	 * @param baseAction
	 * @throws UrlException 
	 */
	private void buildAction(Object baseAction) throws UrlException{
		
		RequestMapping claRequestMapping = this.getClass().getAnnotation(RequestMapping.class);
		
		String claPath = claRequestMapping.value();
		Method[] methods = baseAction.getClass().getMethods();
		 
		 for(Method method : methods){
			 if(method.isAnnotationPresent(RequestMapping.class)){
				 
				 RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				 String methodPath = requestMapping.value();
				 ActionCompent compent = new ActionCompent();
				 
				 String path = UrlResolver.resolverActionUrl(claPath, methodPath);
				 
				 if(UrlResolver.checkUrl(path)){
					 throw new UrlException("this action path is null");
				 }
				 compent.setPath(path);
				 compent.setHander(method);
				 compent.setTarget(baseAction);
				 compent.setName(baseAction.getClass().getName()+"."+method.getName());
				 compent.setParameterTypes(parameterManager.buildParameter(method));
				 ActionCache.setAction(compent);
			 }
		 }
	}
}
