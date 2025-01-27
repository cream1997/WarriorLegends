package com.cream.warriorLegends.obj.common.tuple;

import java.util.Objects;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/27 20:35
 */
public class Tuple2<A, B> {
    public final A first;
    public final B second;

    public Tuple2(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(first, tuple2.first) && Objects.equals(second, tuple2.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
