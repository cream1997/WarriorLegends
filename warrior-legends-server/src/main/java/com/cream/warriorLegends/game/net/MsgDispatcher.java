package com.cream.warriorLegends.game.net;


import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MsgDispatcher {

    private final ConcurrentHashMap<Long, Channel> id2Channel = new ConcurrentHashMap<>();

    public void putChannel(Channel channel) {
        Long id = channel.attr(TokenValidator.ID_KEY).get();
        Objects.requireNonNull(id);
        this.id2Channel.put(id, channel);
    }

    public void removeChannel(Channel channel) {
        Long id = channel.attr(TokenValidator.ID_KEY).get();
        if (id != null) {
            this.id2Channel.remove(id);
        }
    }

    public void dispatch(long id, Object receiveMsg) {

    }

    public void sendMsg(long id, Object msg) {
        Channel channel = id2Channel.get(id);
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }
}
