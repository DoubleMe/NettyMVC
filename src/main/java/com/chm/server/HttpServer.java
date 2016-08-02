package com.chm.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chm.hander.NettyServerHander;

public class HttpServer {
	
	private static final Log logger = LogFactory.getLog(HttpServer.class);
	
	public static HttpServer serverManager = null;
	private HttpServer(){
		
	}
	
	public synchronized static HttpServer getInstance(){
		if(serverManager == null){
			serverManager = new HttpServer();
		}
		return serverManager;
	}

	
	public void startServer(int port) {
		logger.info("启动netty服务器。。。,port={"+port+"}");
		 EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap bootstrap = new ServerBootstrap();
	            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
	                    .childHandler(new ChannelInitializer<SocketChannel>() {
	                                @Override
	                                public void initChannel(SocketChannel ch) throws Exception {
	                                    // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
	                                    ch.pipeline().addLast(new HttpResponseEncoder());
	                                    // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
	                                    ch.pipeline().addLast(new HttpRequestDecoder());
	                                    ch.pipeline().addLast(new NettyServerHander());
	                                }
	                            }).option(ChannelOption.SO_BACKLOG, 128) 
	                    .childOption(ChannelOption.SO_KEEPALIVE, true);
	            ChannelFuture f = bootstrap.bind(port).sync();
	            f.channel().closeFuture().sync();
	        } catch (Exception e){
	        	e.printStackTrace();
	        }finally{
	        	bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	}
	
	public static void writeResponse(ChannelHandlerContext ctx, String parameterStr){
		
		ByteBuf buf;
		try {
			buf = Unpooled.wrappedBuffer(parameterStr.getBytes("UTF-8"));
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
	                 HttpResponseStatus.OK, buf);
	         response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
	         response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
	                 response.content().readableBytes());
	             response.headers().set(HttpHeaders.Names.CONNECTION, Values.KEEP_ALIVE);
	         ctx.write(response);
	         ctx.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
