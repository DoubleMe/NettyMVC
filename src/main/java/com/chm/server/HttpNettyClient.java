package com.chm.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpNettyClient {
	
	private static HttpClient httpCilent;
	
	public static HttpClient getHttpClient(){
		if(httpCilent == null){
			httpCilent = new DefaultHttpClient();
		}
		return httpCilent;
	}
	
	public static HttpResponse httpPost(String url,List<NameValuePair> params){
		
		HttpClient httpClient = HttpNettyClient.getHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse reponse = httpClient.execute(post);
			
			return reponse;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
	}
	
	public static HttpResponse httpGet(String url){
		
		HttpClient httpClient = HttpNettyClient.getHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse reponse = httpClient.execute(httpGet);
			
			return reponse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
