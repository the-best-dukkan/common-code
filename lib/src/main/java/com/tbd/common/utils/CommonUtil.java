package com.tbd.common.utils;

public class CommonUtil {

    private CommonUtil() {}

    public static boolean validateId(Long id) {
        return id != null && id >= 1;
    }
}
