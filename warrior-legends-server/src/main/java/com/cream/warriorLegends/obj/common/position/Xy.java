package com.cream.warriorLegends.obj.common.position;

import java.util.Objects;

/**
 * 坐标
 */
public class Xy {
    public int x;
    public int y;

    public Xy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Xy xy = (Xy) o;
        return x == xy.x && y == xy.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
