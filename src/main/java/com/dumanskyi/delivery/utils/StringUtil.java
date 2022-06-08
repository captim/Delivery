package com.dumanskyi.delivery.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {
    public String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
