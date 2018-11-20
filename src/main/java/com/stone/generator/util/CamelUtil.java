package com.stone.generator.util;

import com.google.common.base.CaseFormat;

/**
 * author : WH
 * time : 2018/11/20 3:29 PM
 */
public final class CamelUtil {

    public static String castUpper(String str) {
        return caseFormat(str).to(CaseFormat.UPPER_CAMEL, str);
    }

    public static String castLower(String str) {
        return caseFormat(str).to(CaseFormat.LOWER_CAMEL, str);
    }

    private static CaseFormat caseFormat(String str) {
        CaseFormat caseFormat;
        if (str.contains("-")) {
            caseFormat = CaseFormat.LOWER_HYPHEN;
        } else if (str.contains("_")) {
            caseFormat = CaseFormat.LOWER_UNDERSCORE;
        } else {
            caseFormat = CaseFormat.LOWER_CAMEL;
        }
        return caseFormat;
    }
}
