package com.mcp.lottery.util.cons;


import java.util.HashMap;
import java.util.Map;

public class BingoCons {

    public static String PLAY_FFC = "261977";
    public static String GROUP_FFC = "26408";
    public static String TYPE_FFC = "113";

    public static Map<String, String> TOU_ZHU_FFC = new HashMap() {{
        put("111", "56789,-,-,-,-");
        put("112", "01234,-,-,-,-");
        put("121", "13579,-,-,-,-");
        put("122", "02468,-,-,-,-");

        put("211", "-,56789,-,-,-");
        put("212", "-,01234,-,-,-");
        put("221", "-,13579,-,-,-");
        put("222", "-,02468,-,-,-");

        put("311", "-,-,56789,-,-");
        put("312", "-,-,01234,-,-");
        put("321", "-,-,13579,-,-");
        put("322", "-,-,02468,-,-");

        put("411", "-,-,-,56789,-");
        put("412", "-,-,-,01234,-");
        put("421", "-,-,-,13579,-");
        put("422", "-,-,-,02468,-");

        put("511", "-,-,-,-,56789");
        put("512", "-,-,-,-,01234");
        put("521", "-,-,-,-,13579");
        put("522", "-,-,-,-,02468");
    }};


}
