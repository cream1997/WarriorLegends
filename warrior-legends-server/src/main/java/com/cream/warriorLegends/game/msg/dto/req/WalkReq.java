package com.cream.warriorLegends.game.msg.dto.req;

import lombok.Getter;

@Getter
public class WalkReq {
    private final int x;
    private final int y;

    public WalkReq(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
