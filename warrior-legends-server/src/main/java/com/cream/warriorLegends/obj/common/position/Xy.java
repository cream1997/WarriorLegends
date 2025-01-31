package com.cream.warriorLegends.obj.common.position;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 坐标
 */
@EqualsAndHashCode
@ToString
public class Xy {
    public int x;
    public int y;

    public Xy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
