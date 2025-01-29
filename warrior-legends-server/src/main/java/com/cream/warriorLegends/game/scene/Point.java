package com.cream.warriorLegends.game.scene;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Point {
    public final int x;
    public final int y;
    public boolean block;

    private final Set<Long> allRoleId = new HashSet<>();

    public void addRole(long id) {
        if (allRoleId.contains(id)) {
            log.error("该点位已存在该玩家，id:{}", id);
            return;
        }
        allRoleId.add(id);
    }

    public void removeRole(long id) {
        allRoleId.remove(id);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, boolean block) {
        this.x = x;
        this.y = y;
        this.block = block;
    }
}
