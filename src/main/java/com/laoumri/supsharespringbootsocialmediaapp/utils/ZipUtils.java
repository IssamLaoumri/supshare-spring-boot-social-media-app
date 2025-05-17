package com.laoumri.supsharespringbootsocialmediaapp.utils;

import java.util.HashMap;
import java.util.Map;

public class ZipUtils {
    public static Map<String, Object> argsToMap(String[] paramNames, Object[] args) {
        if (paramNames.length != args.length) {
            throw new IllegalArgumentException("Parameter names and arguments must match in length.");
        }

        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            result.put(paramNames[i], args[i]);
        }
        return result;
    }
}
