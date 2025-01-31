package com.cream.warriorLegends.game.msg.dto.res;

import com.cream.warriorLegends.game.base.Role;
import com.cream.warriorLegends.game.msg.base.ResMsgType;
import com.cream.warriorLegends.game.msg.base.BaseRes;
import lombok.Setter;

@Setter
public class LoginMapRes implements BaseRes {
    private Role role;
    private int mapId;
    private String mapName;
    private int width;
    private int height;

    @Override
    public ResMsgType msgType() {
        return ResMsgType.LoginMap;
    }
}
