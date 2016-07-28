package com.chm.model;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	private String name;
	
	private int id;
	
	private Date date;
	
	private OtherTest otherTest;
	
	private Test test;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public OtherTest getOtherTest() {
		return otherTest;
	}

	public void setOtherTest(OtherTest otherTest) {
		this.otherTest = otherTest;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	@Override
	public String toString() {
		
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
