package com.chm.excutor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseExcutor {

	//public String excutor(HttpServletRequest request,HttpServletResponse response);
	
	public String excutor(Map<String, Object> request);
}
