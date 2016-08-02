package com.chm;

import io.netty.channel.ChannelFuture;

import com.chm.server.NettyClient;




/**
 * Hello world!
 *
 */
public class ClientApp {
	
    public static void main( String[] args ){
    	
    	NettyClient client = NettyClient.getInstance();
    	ChannelFuture f = client.connect("127.0.0.1", 8847, false);
    	//client.sendMessage(f, "I'm ok?");
    }
}
