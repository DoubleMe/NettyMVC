package com.chm.parameter.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chm.action.AbstractAction;
import com.chm.model.ActionCompent;
import com.chm.model.MethodParameter;
import com.chm.model.Test;
import com.chm.parameter.ParameterManager;
import com.chm.util.BeanUtil;

@Service("parameterManager")
public class ParameterManagerImpl implements ParameterManager{

	/**
	 * 构建action方法参数
	 * @param baseAction
	 * @param method
	 * @return
	 */
	public MethodParameter[] buildParameter(Method method) {
		Parameter[] params = method.getParameters();
		List<MethodParameter> parameters = new ArrayList<MethodParameter>();
		
		for(Parameter param : params){
			parameters.add(getMethodParameter(param,method));
		}
		return (MethodParameter[])parameters.toArray();
	}

	/**
	 * 获取一个MethodParameter
	 * @param parameter
	 * @param method
	 * @return
	 */
	public MethodParameter getMethodParameter(Parameter parameter, Method method) {
		MethodParameter methodParameter = new MethodParameter();
		Class<?> clazz = parameter.getType();
		methodParameter.setParameterAnnotations(parameter.getAnnotations());
		methodParameter.setMethod(method);
		methodParameter.setParameterType(clazz);
		methodParameter.setParameterName(parameter.getName());
//		methodParameter.setParameterIndex(parameter.);
		if(!BeanUtil.isBasicTypes(clazz)){
			methodParameter.setMethodParameters(buildMethodParameter(parameter.getType()));
		}
		return methodParameter;
	}

	/**
	 * 构建复杂类的MethodParameter
	 * @param clazz
	 * @return
	 */
	public MethodParameter[] buildMethodParameter(Class<?> clazz) {
		Field[] fields = clazz.getFields();
		if(fields.length == 0){
			return null;
		}
		MethodParameter[] methodParameters = new MethodParameter[fields.length];
		
		for(int index = 0; index < fields.length; index++){
			Field field = fields[index];
			MethodParameter methodParameter = methodParameters[index];
			methodParameter.setParameterName(field.getName());
			methodParameter.setParameterAnnotations(field.getAnnotations());
			methodParameter.setParameterType(field.getClass());
			if(!BeanUtil.isBasicTypes(field.getClass())){
				methodParameter.setMethodParameters(buildMethodParameter(field.getClass()));
			}
		}
		
		return methodParameters;
	}

	/**
	 * 装配action方法的参数
	 * @param request 请求
	 * @param action 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public MethodParameter[] buildMethodParameter(Map<String, Object> request,ActionCompent action) {
		MethodParameter[] methodParameters = action.getParameterTypes();
		
		for(Entry<String, Object> entry : request.entrySet()){
			if(StringUtils.isEmpty(entry.getKey()))continue;
			for(MethodParameter methodParameter : methodParameters){
				doDispatchParameterName(entry.getValue(), processParameterName(entry.getKey()), methodParameter, 0);
			}
		}
		return null;
	}
	
	/**
	 * 处理ParameterName
	 * @param parameterName
	 * @return
	 */
	private String[] processParameterName(String parameterName){
		return parameterName.split(".");
	}
	
	/**
	 * 递归寻找匹配的参数
	 * @param requestValue
	 * @param requestName
	 * @param methodParameter
	 * @param level
	 */
	private void doDispatchParameterName(Object requestValue,String[] requestName,MethodParameter methodParameter,int level){
		if(level == requestName.length){
			methodParameter.setParameterValue(requestValue);
			return;
		}
		if(methodParameter.getMethodParameters() != null){
			for(MethodParameter parameter : methodParameter.getMethodParameters()){
				if(parameter.getParameterName().equals(requestName)){
					doDispatchParameterName(requestValue, requestName, methodParameter, level++);
				}
			}
		}
	}

	/**
	 * 构造action方法参数
	 * @param action
	 */
	public Object[] buildArgs(ActionCompent action) {
		
		MethodParameter[] methodParameters = action.getParameterTypes();
		
		return null;
	}
	
	public Object getObject(MethodParameter methodParameter){
		try {
			Object obj = methodParameter.getParameterType().newInstance();
			if(methodParameter.getParameterValue() != null){
				if(BeanUtil.isBasicTypes(methodParameter.getParameterType())){
					obj = methodParameter.getParameterValue();
				}
			}else{//不是基本类型。并且有字段,调用反射将值注入
				if(methodParameter.getMethodParameters() != null){
					for(MethodParameter parameter : methodParameter.getMethodParameters()){
						Method moethd = obj.getClass().getMethod(getSetMethodName(parameter.getParameterName()), parameter.getParameterType());
						moethd.invoke(obj, parameter.getParameterValue());
					}
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	private String getSetMethodName(String parameterName){
		String first = parameterName.substring(0,1);
		String temp = parameterName.replaceFirst(first,first.toUpperCase());
		
		return "set"+temp;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		ParameterManagerImpl impl = new ParameterManagerImpl();
		Object test = new Test();
		Method method = test.getClass().getMethod(impl.getSetMethodName("name"), String.class);
		method.invoke(test, "nihao");
		
		System.out.println(((Test)test).getName());
	}
}
