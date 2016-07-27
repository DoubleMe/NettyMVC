package com.chm.model;

import java.lang.reflect.Method;

/**
 * Action
 * @author chen-hongmin
 *
 */
public class ActionCompent {
	
	/**
	 * Action路径
	 */
	private String path;
	/**
	 * Action名称
	 */
	private String name;
	/**
	 * 请求处理方法
	 */
	private Method hander;
	private Object target;
	private MethodParameter[] ParameterTypes;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public MethodParameter[] getParameterTypes() {
		return ParameterTypes;
	}
	public void setParameterTypes(MethodParameter[] parameterTypes) {
		ParameterTypes = parameterTypes;
	}
	public Method getHander() {
		return hander;
	}
	public void setHander(Method hander) {
		this.hander = hander;
	}
	
}
