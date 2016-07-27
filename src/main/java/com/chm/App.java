package com.chm;

import javax.servlet.http.HttpServletRequest;

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
    	 
    }
}
