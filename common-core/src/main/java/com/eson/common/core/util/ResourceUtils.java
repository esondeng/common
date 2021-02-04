package com.eson.common.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

import com.eson.common.core.constants.Constants;
import com.google.common.collect.ImmutableMap;

import static java.util.stream.Collectors.joining;

/**
 * @author dengxiaolin
 * @since 2021/02/04
 */
public class ResourceUtils {

    public static String getResource(String directory, String fileName) {
        try {
            if (directory.endsWith(Constants.SLASH)) {
                fileName = directory + fileName;
            }
            else {
                fileName = directory + Constants.SLASH + fileName;
            }

            InputStreamReader in = new InputStreamReader(ResourceUtils.class.getResourceAsStream(fileName));
            return new BufferedReader(in).lines().collect(joining(" "));
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String replace(String resourceTemplate, String k1, String v1) {
        Map<String, String> valueMap = ImmutableMap.of(k1, v1);
        return StringSubstitutor.replace(resourceTemplate, valueMap);
    }

    public static String replace(String resourceTemplate, String k1, String v1, String k2, String v2) {
        Map<String, String> valueMap = ImmutableMap.of(k1, v1, k2, v2);
        return StringSubstitutor.replace(resourceTemplate, valueMap);
    }

    public static String replace(String resourceTemplate, String k1, String v1, String k2, String v2, String k3, String v3) {
        Map<String, String> valueMap = ImmutableMap.of(k1, v1, k2, v2, k3, v3);
        return StringSubstitutor.replace(resourceTemplate, valueMap);
    }

    public static String replace(String resourceTemplate, Map<String, String> map) {
        return StringSubstitutor.replace(resourceTemplate, map);
    }
}
