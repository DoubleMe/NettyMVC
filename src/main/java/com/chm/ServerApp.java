package com.chm;


import com.chm.SpringContext.SpringContext;
import com.chm.server.NettyClient;
import com.chm.server.HttpServer;

/**
 * Hello world!
 *
 */
public class ServerApp {
	
    public static void main( String[] args ){
    	
    	SpringContext.initSpringContext();
    	HttpServer serverManager = HttpServer.getInstance();
    	serverManager.startServer(8847);
    }
}
