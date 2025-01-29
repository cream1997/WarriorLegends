package com.cream.warriorLegends.game.net;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cream.warriorLegends.game.msg.base.MsgProcessor;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class MsgDispatcher {

    private static final ConcurrentHashMap<Long, Channel> ID2CHANNEL = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Integer, MsgProcessor<?>> msgType2Processor = new ConcurrentHashMap<>();

    public void registry(MsgProcessor<?> msgProcessor) {
        msgType2Processor.put(msgProcessor.matchType().val, msgProcessor);
        log.info("注册消息处理器:{}", msgProcessor);
    }

    public void putChannel(Channel channel) {
        Long id = channel.attr(TokenValidator.ID_KEY).get();
        Objects.requireNonNull(id);
        ID2CHANNEL.put(id, channel);
    }

    public void removeChannel(Channel channel) {
        Long id = channel.attr(TokenValidator.ID_KEY).get();
        if (id != null) {
            ID2CHANNEL.remove(id);
        }
    }

    public void dispatch(long id, JSONObject receiveMsg) {
        Object msgType = receiveMsg.get("msgType");
        if (!(msgType instanceof Integer)) {
            log.error("接收消息类型格式错误,msgType:{}", msgType);
            return;
        }
        MsgProcessor<?> msgProcessor = this.msgType2Processor.get(msgType);
        if (msgProcessor == null) {
            log.error("未找到对应类型的消息处理器,msgType:{}", msgType);
            return;
        }
        Object data = receiveMsg.get("data");
        if (data == null) {
            msgProcessor.process(id);
        } else {
            if (data instanceof JSONObject jsonObject) {
                Type dataType = msgProcessor.getDataType();
                try {
                    msgProcessor.process(id, jsonObject.to(dataType));
                } catch (Exception e) {
                    log.error("消息处理异常", e);
                }

            } else {
                log.error("数据类型格式错误,data应该以对象方式传递");
            }
        }
    }

    public static void sendMsg(long id, Object msg) {
        Channel channel = ID2CHANNEL.get(id);
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }
}
