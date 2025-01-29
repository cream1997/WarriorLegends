package com.cream.warriorLegends.game.msg.base;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public enum ReqMsgType {
    EnterRole(1);

    public final int val;

    ReqMsgType(int val) {
        this.val = val;
    }

    private static final Map<Integer, ReqMsgType> val2enum = new HashMap<>();

    static {
        for (ReqMsgType typeEnum : ReqMsgType.values()) {
            val2enum.put(typeEnum.val, typeEnum);
        }
    }

    public static ReqMsgType getByVal(int val) {
        ReqMsgType msgType = val2enum.get(val);
        if (msgType == null) {
            log.error("获取枚举实例为空，val:{}", val);
        }
        return msgType;
    }
}
