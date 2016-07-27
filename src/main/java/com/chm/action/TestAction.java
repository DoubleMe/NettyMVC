package com.chm.action;

import org.springframework.stereotype.Service;

import com.chm.annotation.RequestMapping;

@Service
@RequestMapping("/test")
public class TestAction extends AbstractAction {
	
	@RequestMapping("/getTest")
	public String getTest(){
		
		return "this is a test annotation!";
	}

}
