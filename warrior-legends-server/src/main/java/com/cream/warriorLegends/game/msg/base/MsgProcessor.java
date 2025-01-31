package com.cream.warriorLegends.game.msg.base;

import lombok.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class MsgProcessor<T> {

    public void process(long rid, @NonNull T data) {
    }

    public void process(long rid) {
    }

    public abstract ReqMsgType matchType();

    public final Type getDataType() {
        Type superclass = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
        return actualTypeArguments[0];
    }
}
