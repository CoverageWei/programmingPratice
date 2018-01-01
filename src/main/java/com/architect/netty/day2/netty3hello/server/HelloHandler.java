//package com.architect.netty.day2.netty3hello.server;
//
//import org.jboss.netty.buffer.ChannelBuffer;
//import org.jboss.netty.buffer.ChannelBuffers;
//import org.jboss.netty.channel.ChannelHandlerContext;
//import org.jboss.netty.channel.ChannelStateEvent;
//import org.jboss.netty.channel.ExceptionEvent;
//import org.jboss.netty.channel.MessageEvent;
//import org.jboss.netty.channel.SimpleChannelHandler;
///**
// * 消息接受处理类
// *
// */
//public class HelloHandler extends SimpleChannelHandler {
//
//	/**
//	 * 接收消息
//	 */
//	@Override
//	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//
////		ChannelBuffer message = (ChannelBuffer) e.getMessage();
////		String s = new String(message.array());
////		System.out.println(s);
//
//		// 增加 pipeline.addLast("decoder", new StringDecoder());
//		String s = (String) e.getMessage();
//		System.out.println(s);
//
//		//回写数据
////		ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi".getBytes());
////		ctx.getChannel().write(copiedBuffer);
//
//		// 增加 pipeline.addLast("encoder", new StringEncoder());
//		ctx.getChannel().write("hi");
//		super.messageReceived(ctx, e);
//	}
//
//	/**
//	 * 捕获异常
//	 */
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
//		System.out.println("exceptionCaught");
//		super.exceptionCaught(ctx, e);
//	}
//
//	/**
//	 * 新连接
//	 */
//	@Override
//	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//		System.out.println("channelConnected");
//		super.channelConnected(ctx, e);
//	}
//
//	/**
//	 * 必须是链接已经建立，关闭通道的时候才会触发
//	 */
//	@Override
//	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//		System.out.println("channelDisconnected");
//		super.channelDisconnected(ctx, e);
//	}
//
//	/**
//	 * channel关闭的时候触发
//	 */
//	@Override
//	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//		System.out.println("channelClosed");
//		super.channelClosed(ctx, e);
//	}
//}
