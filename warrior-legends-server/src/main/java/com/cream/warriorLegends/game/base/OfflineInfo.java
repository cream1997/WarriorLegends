package com.cream.warriorLegends.game.base;

import com.cream.warriorLegends.obj.common.position.Location;
import lombok.Getter;
import lombok.Setter;

/**
 * 离线信息
 */
@Getter
@Setter
public class OfflineInfo {
    private long time;
    private Location location;
    private long hp;
    private long mp;
}
