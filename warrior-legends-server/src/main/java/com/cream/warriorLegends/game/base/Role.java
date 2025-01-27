package com.cream.warriorLegends.game.base;

import com.cream.warriorLegends.obj.common.position.Xy;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 20:31
 */
@Getter
@Setter
public class Role {
    private long id;
    private String nickNane;
    private int level;
    private long hp;
    private long mp;
    private int mapId;
    private Xy xy;
    private long loginTime;
    private long logoutTime;
}
