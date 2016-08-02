package com.chm.server;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chm.hander.NettyClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClient {

	private static final Log logger = LogFactory.getLog(NettyClient.class);

	private static NettyClient clientManager = null;
	private static Bootstrap bootstrap = null;
	private static String HOST= null;
	private static int PORT;
	
	private NettyClient(){
		
	}
	
	public synchronized static NettyClient getInstance(){
		if(clientManager == null){
			clientManager = new NettyClient();
		}
		
		return clientManager;
	}
	
	/**
	 * 发送消息
	 * @param f
	 * @param message
	 */
	public DefaultFullHttpRequest sendMessage(ChannelFuture f,String message){
		
		try {
			URI uri = new URI("http://127.0.0.1:8080/demo");
			DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
	                 uri.toASCIIString(), Unpooled.wrappedBuffer(message.getBytes("UTF-8")));

	         // 构建http请求
	         request.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
	         request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
	         request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
	        
	         f.channel().write(request);
	         f.channel().flush();
	         f.channel().closeFuture().sync();
	         return request;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 连接netty 服务端
	 * @param HOST 服务端地址
	 * @param PORT 服务端端口号
	 * @param isKeepActive 是否长连接
	 */
	public ChannelFuture connect(String HOST,int PORT,boolean isKeepActive){
		logger.info("初始化netty客服端。。。");
		// Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        	bootstrap = new Bootstrap();
        	bootstrap.group(group)
        	.channel(NioSocketChannel.class)
        	.option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });
        	ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
        	String message = "ok?";
        	URI uri = new URI("http://127.0.0.1:88/demo/test");
			DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
	                 uri.toASCIIString(), Unpooled.wrappedBuffer(message.getBytes("UTF-8")));

	         // 构建http请求
	         request.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
	         request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
	         request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
	        
	         f.channel().write(request);
	         f.channel().flush();
			 f.channel().closeFuture().sync();
			
        	return f;

        }catch(Exception e){
        	e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
        
        return null;
	}
}
