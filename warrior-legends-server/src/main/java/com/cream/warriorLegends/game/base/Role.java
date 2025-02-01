package com.cream.warriorLegends.game.base;

import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Role {
    private long id;
    private String nickName;
    private int level;
    private int hp;
    private int mp;
    private int mapId;
    private Xy xy;
    private long loginTime;
    private long logoutTime;
}
