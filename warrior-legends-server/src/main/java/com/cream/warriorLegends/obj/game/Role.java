package com.cream.warriorLegends.obj.game;

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
    private OfflineInfo offlineInfo;
}
