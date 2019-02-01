package com.mcp.lottery.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PluginCache {

    private final static Map<String, String> map = new HashMap();

    public static void add(String key, String name) {
        map.put(key, name);
    }

    public static String get(String key) {
        return map.get(key);
    }

    public static boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public static String string() {
        return JSON.toJSONString(map);
    }

    public static Map<String, String> map() {
        return map;
    }


}
