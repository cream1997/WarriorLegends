package com.cream.warriorLegends.game.msg.dto;

import com.cream.warriorLegends.game.msg.base.ResMsgType;

public abstract class BaseRes {

    private final int msgType;

    public BaseRes(ResMsgType msgType) {
        this.msgType = msgType.val;
    }
}
