package com.chm.parameter.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
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
	
	 LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	/**
	 * 构建action方法参数
	 * @param baseAction
	 * @param method
	 * @return
	 */
	public MethodParameter[] buildParameter(Method method) {
		String[] ParameterNames = parameterNameDiscoverer.getParameterNames(method);
		Parameter[] params = method.getParameters();
		MethodParameter[] methodParameters = new MethodParameter[params.length];
		for(int i = 0; i < params.length; i++){
			methodParameters[i] = getMethodParameter(params[i],ParameterNames[i],method);
		}
		
		return methodParameters;
	}

	/**
	 * 获取一个MethodParameter
	 * @param parameter
	 * @param method
	 * @return
	 */
	public MethodParameter getMethodParameter(Parameter parameter,String parameterName, Method method) {
		
		MethodParameter methodParameter = new MethodParameter();
		Class<?> clazz = parameter.getType();
		methodParameter.setParameterAnnotations(parameter.getAnnotations());
		methodParameter.setMethod(method);
		methodParameter.setParameterType(clazz);
		methodParameter.setParameterName(parameterName);
//		methodParameter.setParameterIndex(parameter.);
		if(!BeanUtil.isBasicTypes(clazz)){
			methodParameter.setMethodParameters(buildMethodParameter(parameter.getType(),1));
		}
		return methodParameter;
	}

	/**
	 * 构建复杂类的MethodParameter
	 * @param clazz
	 * @return
	 */
	public MethodParameter[] buildMethodParameter(Class<?> clazz,int level) {
		Field[] fields = clazz.getDeclaredFields();
		if(fields.length == 0 || level >= 3){
			return null;
		}
		MethodParameter[] methodParameters = new MethodParameter[fields.length];
		
		for(int index = 0; index < fields.length; index++){
			Field field = fields[index];
			methodParameters[index] = new MethodParameter();
			methodParameters[index].setParameterName(field.getName());
			methodParameters[index].setParameterAnnotations(field.getAnnotations());
			methodParameters[index].setParameterType(field.getType());
			if(!BeanUtil.isBasicTypes(field.getType())){
				methodParameters[index].setMethodParameters(buildMethodParameter(field.getType(),++level));
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
	public MethodParameter[] doDispatchParameterName(Map<String, Object> request,ActionCompent action) {
		MethodParameter[] methodParameters = action.getParameterTypes();
		
		for(Entry<String, Object> entry : request.entrySet()){
			
			if(StringUtils.isEmpty(entry.getKey()))continue;
			doDispatchParameterName(entry.getValue(), processParameterName(entry.getKey()), methodParameters, 0);
		}
		return methodParameters;
	}
	
	/**
	 * 处理ParameterName
	 * @param parameterName
	 * @return
	 */
	private String[] processParameterName(String parameterName){
		return parameterName.split("\\.");
	}
	
	/**
	 * 递归寻找匹配的参数
	 * @param requestValue
	 * @param requestName
	 * @param methodParameter
	 * @param level
	 */
	private void doDispatchParameterName(Object requestValue,String[] requestName,MethodParameter[] methodParameters,int level){
		
		if(methodParameters == null){
			return;
		}
		for(MethodParameter methodParameter : methodParameters){
			//如果当前层级找到匹配字段 并且是最后一个层级就给最后匹配上的参数赋值
			if(methodParameter.getParameterName().equals(requestName[level])){
				if((requestName.length -1) == level){
					methodParameter.setParameterValue(requestValue);
					return;
				}else{//当前层级有匹配，寻找下一层级匹配
					doDispatchParameterName(requestValue, requestName, methodParameter.getMethodParameters(), ++level);
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
		Object[] args = new Object[methodParameters.length];
		
		for(int i= 0;i < methodParameters.length; i++){
			args[i] = getObject(methodParameters[i]);
		}
		
		return args;
	}
	
	/**
	 * 
	 * @param obj 要填充数据的对象
	 * @param methodParameters 对象参数 类型、字段名、字段值
	 * @return
	 */
	private Object getObject(Object obj,MethodParameter[] methodParameters){
		if(methodParameters == null || methodParameters.length == 0){
			return obj;
		}
		for(MethodParameter parameter : methodParameters){
			//获取对应的set方法
			String methodName = getSetMethodName(parameter.getParameterName());
			try {
				Class<?> clazz = parameter.getParameterType();
				
				Method method = obj.getClass().getMethod(methodName, clazz);
				Object parameterValue = null;
				if(!BeanUtil.isBasicTypes(clazz)){
					Object param = clazz.newInstance();
					parameterValue = getObject(param, parameter.getMethodParameters());
				}else{
					parameterValue = parameter.getParameterValue();
				}
				method.invoke(obj, parameterValue);
			  
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
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
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return obj;
	}
	
	/**
	 * MethodParameter 转换为一个对象
	 * @param methodParameter
	 * @return
	 */
	public Object getObject(MethodParameter methodParameter){
		try {
			Object obj = methodParameter.getParameterType().newInstance();
			
			if(BeanUtil.isBasicTypes(methodParameter.getParameterType())){
				obj = methodParameter.getParameterValue();
			}else{//不是基本类型。并且有字段,调用反射将值注入
				getObject(obj, methodParameter.getMethodParameters());
			}
			return obj;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
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
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String[] test = "test.name".split(".");
		System.out.println(test.length);
	}
}
