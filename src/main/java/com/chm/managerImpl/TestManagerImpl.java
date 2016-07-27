package com.chm.managerImpl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.chm.manager.TestManeger;

@Service("testManagerImpl")
public class TestManagerImpl implements TestManeger{

	public String getMessage() {
		
		return this.getClass().getName();
	}
}
