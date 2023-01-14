package com.skachko.shop.order.service.support.utils;

import java.util.Collection;
import java.util.Map;

public class IsEmptyUtil {
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Map map) {
        return !isNullOrEmpty(map);
    }


}
