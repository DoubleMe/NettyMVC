package com.chm.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.chm.action.AbstractAction;
import com.chm.model.ActionCompent;
import com.chm.model.MethodParameter;

public interface ParameterManager {

	public MethodParameter[] buildParameter(Method method);
	
	public MethodParameter getMethodParameter(Parameter parameter,String parameterName,Method method);
	
	public MethodParameter[] buildMethodParameter(Class<?> clazz,int level);
	
	public MethodParameter[] doDispatchParameterName(Map<String, Object> request,ActionCompent action);
	
	public Object[] buildArgs(ActionCompent action);
	
}
