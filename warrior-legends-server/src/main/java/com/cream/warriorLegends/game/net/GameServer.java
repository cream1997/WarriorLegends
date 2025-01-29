package com.cream.warriorLegends.game.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 13:46
 */
@Slf4j
@Component
public class GameServer {

    @Value("${game.port:8889}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private static final String WEBSOCKET_PATH = "/ws";

    private final WebSocketHandler webSocketHandler;
    private final TokenValidator tokenValidator;

    @Autowired
    public GameServer(WebSocketHandler webSocketHandler, TokenValidator tokenValidator) {
        this.webSocketHandler = webSocketHandler;
        this.tokenValidator = tokenValidator;
    }

    @PostConstruct
    public void start() throws Exception {
        // fixme 线程数先随便给个值
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(2);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //似乎只需要在logback中配置就好，这里好像不需要加
                //.handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(65535)) // 聚合http请求体（处理post请求体）
                                .addLast(tokenValidator) //在握手之前验证token
                                //.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true, 64 * 1024, true, true, 10000))
                                .addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, true))
                                // fixme 数值将来改小
                                .addLast(new IdleStateHandler(600, 600, 600))
                                .addLast(webSocketHandler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.bind(port).sync();
        log.info("Game Server started on port:{}, path:{}", port, WEBSOCKET_PATH);
    }


    @PreDestroy
    public void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        log.info("Game Server stopped");
    }
}
