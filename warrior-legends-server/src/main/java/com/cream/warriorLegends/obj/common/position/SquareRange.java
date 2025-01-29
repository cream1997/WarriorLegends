package com.cream.warriorLegends.obj.common.position;

import java.util.Objects;

/**
 * 正方形区域
 */
public class SquareRange {

    private final Xy xy;
    private final int range;

    public SquareRange(int x, int y, int range) {
        this.xy = new Xy(x, y);
        this.range = range;
    }

    public Xy getCenterXy() {
        return xy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SquareRange that = (SquareRange) o;
        return range == that.range && Objects.equals(xy, that.xy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xy, range);
    }
}
