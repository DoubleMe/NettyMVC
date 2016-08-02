package com.chm.hander;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{

	@Override
    public void channelActive(ChannelHandlerContext ctx) {
		 ctx.writeAndFlush("来自客户端的问候！！！");
        System.out.println("channelActive");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println(msg);
        System.out.println("channelRead");
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("channelReadComplete");
    }
}
