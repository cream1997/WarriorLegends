package com.cream.warriorLegends.game.msg.base;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public enum ResMsgType {
    LoginMap(1),
    EnterMap(2),
    WalkRes(3),
    ;

    public final int val;

    ResMsgType(int val) {
        this.val = val;
    }

    private static final Map<Integer, ResMsgType> val2enum = new HashMap<>();

    static {
        for (ResMsgType typeEnum : ResMsgType.values()) {
            val2enum.put(typeEnum.val, typeEnum);
        }
    }

    public static ResMsgType getByVal(int val) {
        ResMsgType msgType = val2enum.get(val);
        if (msgType == null) {
            log.error("获取枚举实例为空，val:{}", val);
        }
        return msgType;
    }
}
