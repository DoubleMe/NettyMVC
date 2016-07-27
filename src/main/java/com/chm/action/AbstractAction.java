package com.chm.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.chm.annotation.RequestMapping;
import com.chm.cache.ActionCache;
import com.chm.exception.UrlException;
import com.chm.model.ActionCompent;
import com.chm.model.MethodParameter;
import com.chm.parameter.ParameterManager;
import com.chm.resolver.UrlResolver;
import com.chm.util.BeanUtil;

/**
 * ACTION基类 所有的ACTION都继承这个类
 * 
 * 用于构建Action
 * @author chen-hongmin
 *
 */
public abstract class AbstractAction {
	
	@Autowired
	private ParameterManager parameterManager;
	/**
	 * 向注册中心注册Action 初始化Action方法
	 * @throws UrlException 
	 */
	
	public void register() throws UrlException{
		if(this.getClass().isAnnotationPresent(RequestMapping.class)){
			buildAction(this);
		}
	}
	
	/**
	 * 构建一个Action,并初始化到缓存
	 * @param baseAction
	 * @throws UrlException 
	 */
	private void buildAction(AbstractAction baseAction) throws UrlException{
		
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
