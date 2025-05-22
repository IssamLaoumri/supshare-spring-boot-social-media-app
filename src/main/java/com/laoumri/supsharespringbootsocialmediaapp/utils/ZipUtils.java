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

    public static String generateCode(int length){
        StringBuilder code = new StringBuilder();
        String schema = "0123456789";
        for(int i = 0; i < length; i++){
            code.append(schema.charAt((int) (Math.random() * schema.length())));
        }
        return code.toString();
    }
}
