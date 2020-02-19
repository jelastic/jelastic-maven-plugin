package com.jelastic.util;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum PackageTypeEnum {
    WAR("war"), EAR("ear"), JAR("jar");
    private final String type;

    public static boolean isSupported(String type) {
        return Arrays.stream(values()).anyMatch(packageTypeEnum -> packageTypeEnum.type.equals(type));
    }
}
