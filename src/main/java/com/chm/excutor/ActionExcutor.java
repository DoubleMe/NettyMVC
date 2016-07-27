package com.chm.excutor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chm.cache.ActionCache;
import com.chm.callback.CallBack;
import com.chm.model.ActionCompent;
import com.chm.model.MethodParameter;
import com.chm.parameter.ParameterManager;
import com.chm.parameter.impl.ParameterManagerImpl;
import com.chm.util.BeanUtil;


public class ActionExcutor implements BaseExcutor{

	
	
	public String excutor(Map<String, Object> request){
		
		String uri = (String)request.get("uri");
		ActionCompent action = ActionCache.getAction(uri);
		ParameterManagerImpl manager = new ParameterManagerImpl();
		manager.buildMethodParameter(request, action);
		String target = invoke(action);
		
		return target;
	}
	
	public String invoke(ActionCompent action){
		try {
			
			
			return (String) action.getHander().invoke(action.getTarget(), null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch blockzzzzz
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
