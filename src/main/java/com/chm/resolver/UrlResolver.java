package com.chm.resolver;

import org.springframework.util.StringUtils;


public class UrlResolver {
	
	public static String resolverActionUrl(String claUrl,String methodUrl) {
		
		return resolverUrl(claUrl)+resolverUrl(methodUrl);
	}
	
	public static boolean checkUrl(String url){
		
		return StringUtils.isEmpty(url);
	}
	
	public static String resolverUrl(String url){

		if(StringUtils.isEmpty(url)){
			return "";
		}
		if(!url.startsWith("/")){
			url = "/" + url;
		}
		return url;
	}

}
