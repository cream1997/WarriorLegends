package com.cream.warriorLegends.game.msg.dto.res;

import com.cream.warriorLegends.game.msg.base.ResMsgType;
import com.cream.warriorLegends.game.msg.dto.BaseRes;

public class EnterMapRes implements BaseRes {

    @Override
    public ResMsgType msgType() {
        return ResMsgType.EnterMap;
    }
}
