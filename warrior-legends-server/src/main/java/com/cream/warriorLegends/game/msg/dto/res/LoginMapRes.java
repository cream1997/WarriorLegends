package com.cream.warriorLegends.game.msg.dto.res;

import com.cream.warriorLegends.game.msg.base.ResMsgType;
import com.cream.warriorLegends.game.msg.dto.BaseRes;
import lombok.Setter;

@Setter
public class LoginMapRes implements BaseRes {
    private int mapId;
    private String mapName;
    private int width;
    private int height;

    @Override
    public ResMsgType msgType() {
        return ResMsgType.LoginMap;
    }
}
