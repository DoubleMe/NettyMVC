package com.chm.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MethodParameter {
	
	private Method method;
	private Constructor constructor;
	private int parameterIndex;
	private volatile Class<?> parameterType;
	private volatile String parameterName;
	private Object parameterValue;
	private Annotation[] parameterAnnotations;
	private MethodParameter[] methodParameters;
	/**
	 * 层级 防止关联应用进入死循环 最大不能超过三层深度
	 */
	private int level;
	
	public Class<?> getParameterType() {
		return parameterType;
	}
	public void setParameterType(Class<?> parameterType) {
		this.parameterType = parameterType;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public int getParameterIndex() {
		return parameterIndex;
	}
	public void methodParameter(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public MethodParameter[] getMethodParameters() {
		return methodParameters;
	}
	public void setMethodParameters(MethodParameter[] methodParameters) {
		this.methodParameters = methodParameters;
	}
	public Method getMethod() {
		return method;
	}
	public Constructor getConstructor() {
		return constructor;
	}
	public Annotation[] getParameterAnnotations() {
		return parameterAnnotations;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}
	public void setParameterAnnotations(Annotation[] parameterAnnotations) {
		this.parameterAnnotations = parameterAnnotations;
	}
	public Object getParameterValue() {
		return parameterValue;
	}
	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
