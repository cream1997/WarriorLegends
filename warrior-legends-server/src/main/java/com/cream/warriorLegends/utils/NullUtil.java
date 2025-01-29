package com.cream.warriorLegends.utils;

import java.util.Collection;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/29 15:15
 */
public class NullUtil {

    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    public static boolean anyEmpty(Collection<?>... collections) {
        if (collections == null) {
            return true;
        }
        for (Collection<?> collection : collections) {
            if (isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }
}
