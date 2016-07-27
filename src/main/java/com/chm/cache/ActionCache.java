package com.chm.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.chm.model.ActionCompent;

public class ActionCache {
	
	private static ConcurrentHashMap<String, ActionCompent> actions = 
			new ConcurrentHashMap<String, ActionCompent>(); 
	
	public static void setAction(ActionCompent action){
		if(action == null){
			new RuntimeException("action is null");
		}
		String path = action.getPath();
		
		if(actions.contains(path)){
			new RuntimeException("action is exist");
		}
		actions.put(path, action);
	}
	
	public static ActionCompent getAction(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		
		return actions.get(name);
	}
	

}
