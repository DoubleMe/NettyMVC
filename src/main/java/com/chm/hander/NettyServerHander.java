package com.chm.hander;


import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import com.chm.server.HttpServer;
import com.chm.server.RequestParser;

/**
 * netty处理类
 * 
 * @author chen-hongmin
 *
 */
public class NettyServerHander extends ChannelInboundHandlerAdapter {

	private static Integer count = 0;

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		count++;
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			ByteBuf buf = content.content();
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);
			String str = new String(bytes, "UTF-8");
			System.out.println(str);
		}

		System.out.println(count+":"+msg);
		HttpServer.writeResponse(ctx, "I'm ok");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("请求断开了");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("异常---------------------------------------------");
	}

}
