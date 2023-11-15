package com.sharky;

import com.google.common.base.CaseFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        var inputData = List.of("config_key_with_some_digits_42:", "config_value_with_some_digits_42");
        var keyPattern = Pattern.compile("\\w*\\d*_\\w*\\d*:");
        var result = new HashMap<String, String>();
        for (String element : inputData) {
            var matcher = keyPattern.matcher(element);
            if (matcher.find()) {
                result.put(element, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, element));
            }
        }
        System.out.println("Transformed the following config keys to camelCase: " + result);
    }
}
