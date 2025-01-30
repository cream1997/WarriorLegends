package com.cream.warriorLegends.game.net;

import com.cream.warriorLegends.utils.NullUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
@Component
public class TokenValidator extends ChannelInboundHandlerAdapter {

    private static final AttributeKey<Long> ID_KEY = AttributeKey.newInstance("idKey");
    // 缓存是线程安全的
    private final Cache<Long, String> id2TokenCache;
    private final MsgDispatcher msgDispatcher;

    @Autowired
    public TokenValidator(MsgDispatcher msgDispatcher) {
        this.msgDispatcher = msgDispatcher;
        this.id2TokenCache = CacheBuilder.newBuilder()
                // fixme 100会不会有点小?
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
    }

    public static long getIdAfterLogin(Channel channel) {
        Attribute<Long> attr = channel.attr(ID_KEY);
        if (attr == null || attr.get() == null) {
            log.error("获取id为空，channel:{}", channel);
            return 0;
        }
        return attr.get();
    }

    public void setTokenCache(long id, String token) {
        id2TokenCache.put(id, token);
    }

    public String getTokenCache(long id) {
        return id2TokenCache.getIfPresent(id);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (isVerified(ctx.channel())) {
            ctx.fireChannelRead(msg);
            return;
        }
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
        Map<String, List<String>> parameters = decoder.parameters();
        List<String> ids = parameters.get("id");
        List<String> tokens = parameters.get("token");
        if (NullUtil.anyEmpty(ids, tokens)) {
            log.error("建立ws连接时未携带token等信息");
            ctx.close();
            return;
        }
        long id = 0;
        if (NumberUtils.isCreatable(ids.getFirst())) { //不是数字说明传来的值不合法
            id = Long.parseLong(ids.getFirst());
        }
        String token = tokens.getFirst();
        String tokenCache = getTokenCache(id);
        if (tokenCache == null || !Objects.equals(token, tokenCache)) {
            log.error("token验证不合法，拒绝连接");
            ctx.close();
            return;
        }
        //验证成功
        Attribute<Long> attr = ctx.channel().attr(ID_KEY);
        attr.set(id);
        this.msgDispatcher.putChannel(ctx.channel());
        ctx.fireChannelRead(msg);
    }

    private boolean isVerified(Channel channel) {
        return channel.attr(ID_KEY).get() != null;
    }
}
