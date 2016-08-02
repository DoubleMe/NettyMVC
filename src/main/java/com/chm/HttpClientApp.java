package com.chm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import io.netty.channel.ChannelFuture;

import com.chm.server.HttpNettyClient;
import com.chm.server.NettyClient;




/**
 * Hello world!
 *
 */
public class HttpClientApp {
	
    public static void main( String[] args ) throws UnsupportedEncodingException{
    	HttpClientApp app = new HttpClientApp();
    	app.get();
    }
    
    public void get(){
    	
    	String url = "http://127.0.0.1:8080/demo/test";
    	HttpNettyClient.httpGet(url);
    }
    
    public void post(){
    	
    	String url = "http://127.0.0.1:8080/demo/test";
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	NameValuePair valuePair1 = new BasicNameValuePair("type1", "test1");
    	NameValuePair valuePair2 = new BasicNameValuePair("type2", "test2");
    	NameValuePair valuePair3 = new BasicNameValuePair("type3", "test3");
    	params.add(valuePair1);
    	params.add(valuePair2);
    	params.add(valuePair3);
    	HttpResponse reponse = HttpNettyClient.httpPost(url, params);
    	
    }
}
