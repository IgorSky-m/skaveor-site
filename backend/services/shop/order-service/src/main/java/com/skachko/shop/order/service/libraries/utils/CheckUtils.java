package com.skachko.shop.order.service.libraries.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CheckUtils {
    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNullOrEmpty(Collection<T> collection) {
        return !isNullOrEmpty(collection);
    }

    public static <K,V> boolean isNullOrEmpty(Map<K,V> map) {
        return map == null || map.isEmpty();
    }
    public static <K,V> boolean isNotNullOrEmpty(Map<K,V> map) {
        return isNullOrEmpty(map);
    }
}
