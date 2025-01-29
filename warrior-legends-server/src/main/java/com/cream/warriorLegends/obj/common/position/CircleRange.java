package com.cream.warriorLegends.obj.common.position;

import java.util.Objects;

/**
 * 圆形区域
 */
public class CircleRange {
    public final int centerX;
    public final int centerY;
    public final int radius;

    public CircleRange(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CircleRange that = (CircleRange) o;
        return centerX == that.centerX && centerY == that.centerY && radius == that.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerX, centerY, radius);
    }
}
