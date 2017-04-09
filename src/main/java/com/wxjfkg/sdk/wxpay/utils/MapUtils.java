package com.wxjfkg.sdk.wxpay.utils;

import java.util.Map;

/**
 * Map工具类
 * 
 * @author GuoRui
 *
 */
public class MapUtils {

	public static String getStringFromMap(Map<String, String> map, String key, String defaultValue) {
        if (key == "" || key == null) {
            return defaultValue;
        }
        String result = (String) map.get(key);
        if (result == null) {
            return defaultValue;
        } else {
            return result;
        }
    }

    public static Integer getIntFromMap(Map<String, String> map, String key) {
        if (key == "" || key == null) {
            return null;
        }
        if (map.get(key) == null) {
            return null;
        }
        return Integer.parseInt((String) map.get(key));
    }
    
}
