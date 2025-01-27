package com.cream.warriorLegends.game.scene;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 23:11
 */
public class Point {
    public final int x;
    public final int y;
    public boolean block;

    private final Set<Long> allRoleId = new HashSet<>();

    public void addRole(long id){
        allRoleId.add(id);
    }

    public void removeRole(long id){
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
