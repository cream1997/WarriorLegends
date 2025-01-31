package com.cream.warriorLegends.game.msg.dto.res;

import com.cream.warriorLegends.game.msg.base.BaseRes;
import com.cream.warriorLegends.game.msg.base.ResMsgType;


public class WalkRes implements BaseRes {

    private final long id;
    private final int x;
    private final int y;

    public WalkRes(long id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public ResMsgType msgType() {
        return ResMsgType.WalkRes;
    }
}
