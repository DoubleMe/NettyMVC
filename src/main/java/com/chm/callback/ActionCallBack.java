package com.chm.callback;

import org.springframework.stereotype.Component;


@Component
public class ActionCallBack implements CallBack {

	public Object before() {
		System.out.println("callback : before");
		return null;
	}

	public Object after() {
		System.out.println("callback : after");
		return null;
	}

}
