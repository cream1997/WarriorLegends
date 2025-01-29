package com.cream.warriorLegends.game.msg.dto;


import com.cream.warriorLegends.game.msg.base.ReqMsgType;

public abstract class BaseReq {
    private final int reqMsgType;

    public BaseReq(ReqMsgType reqMsgType) {
        this.reqMsgType = reqMsgType.val;
    }
}
