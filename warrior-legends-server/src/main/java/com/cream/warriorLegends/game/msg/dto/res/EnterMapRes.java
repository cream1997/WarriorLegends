package com.cream.warriorLegends.game.msg.dto.res;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.msg.base.ResMsgType;
import com.cream.warriorLegends.game.msg.base.BaseRes;
import lombok.Setter;

@Setter
public class EnterMapRes implements BaseRes {

    private Role role;

    @Override
    public ResMsgType msgType() {
        return ResMsgType.EnterMap;
    }
}
