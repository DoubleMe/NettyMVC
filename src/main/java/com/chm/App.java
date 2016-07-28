package com.chm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chm.SpringContext.SpringContext;
import com.chm.excutor.ActionExcutor;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ){
    	
    	SpringContext.initSpringContext();
    	System.out.println(SpringContext.getBean("callBack"));
    	ActionExcutor excutor = new ActionExcutor();
    	
    	Map<String, Object> request = new HashMap<String, Object>();
    	request.put("uri","/test/getTest");
    	request.put("test.name", "test.name");
    	request.put("test.otherTest.testName", "test.otherTest.testName");
    	request.put("test.test.name", "test.test.name");

    	System.out.println(excutor.excutor(request).toString());
    	 
    }
}
