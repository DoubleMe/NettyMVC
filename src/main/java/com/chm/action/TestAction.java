package com.chm.action;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.chm.annotation.RequestMapping;
import com.chm.model.Test;

@Service
@RequestMapping("/test")
public class TestAction extends AbstractAction {
	
	@RequestMapping("/getTest")
	public Test getTest(Test test,String map){
		System.out.println(map);
		return test;
	}

}
