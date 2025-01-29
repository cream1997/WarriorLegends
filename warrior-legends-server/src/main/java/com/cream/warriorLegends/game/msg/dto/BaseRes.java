package com.cream.warriorLegends.game.msg.dto;

import com.cream.warriorLegends.game.msg.base.ResMsgType;

public abstract class BaseRes {

    private final int resMsgType;

    public BaseRes(ResMsgType resMsgType) {
        this.resMsgType = resMsgType.val;
    }
}
