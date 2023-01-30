package com.skachko.shop.auth.service.support.utils;

import java.util.Collection;
import java.util.Map;

public class IsEmptyUtil {
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNullOrEmpty(Collection<T> collection) {
        return !isNullOrEmpty(collection);
    }

    public  static <K,V> boolean isNullOrEmpty(Map<K,V> map) {
        return map == null || map.isEmpty();
    }

    public static <K,V> boolean isNotNullOrEmpty(Map<K,V> map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }


}
