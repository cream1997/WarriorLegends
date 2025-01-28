package com.cream.warriorLegends.game.net;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 14:32
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final MsgDispatcher msgDispatcher;

    @Autowired
    public WebSocketHandler(MsgDispatcher msgDispatcher) {
        this.msgDispatcher = msgDispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("Received message: {}", msg.text());
        // 处理接收到的消息，这里简单返回一个响应
        ctx.writeAndFlush(new TextWebSocketFrame("Message received: " + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        log.info("客户端链接:{}",ctx.channel().remoteAddress());
        log.info("客户端链接:{}",ctx.channel().id().asShortText());
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.info("客户端断开: {}", ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught: ", cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 关闭空闲连接
            ctx.close();
        }
    }
}
