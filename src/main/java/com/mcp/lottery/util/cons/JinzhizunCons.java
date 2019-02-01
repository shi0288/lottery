package com.mcp.lottery.util.cons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JinzhizunCons {

    public static Map<String,JSONObject> TOU_ZHU = new HashMap(){{
        put("111", JSON.parseObject("{\"ID\":\"110\",\"LeiXing\":\"第一球大小\",\"FangXiang\":\"大\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("112", JSON.parseObject("{\"ID\":\"111\",\"LeiXing\":\"第一球大小\",\"FangXiang\":\"小\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("121", JSON.parseObject("{\"ID\":\"112\",\"LeiXing\":\"第一球單雙\",\"FangXiang\":\"單\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("122", JSON.parseObject("{\"ID\":\"113\",\"LeiXing\":\"第一球單雙\",\"FangXiang\":\"雙\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));

        put("211", JSON.parseObject("{\"ID\":\"124\",\"LeiXing\":\"第二球大小\",\"FangXiang\":\"大\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("212", JSON.parseObject("{\"ID\":\"125\",\"LeiXing\":\"第二球大小\",\"FangXiang\":\"小\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("221", JSON.parseObject("{\"ID\":\"126\",\"LeiXing\":\"第二球單雙\",\"FangXiang\":\"單\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("222", JSON.parseObject("{\"ID\":\"127\",\"LeiXing\":\"第二球單雙\",\"FangXiang\":\"雙\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));

        put("311", JSON.parseObject("{\"ID\":\"138\",\"LeiXing\":\"第三球大小\",\"FangXiang\":\"大\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("312", JSON.parseObject("{\"ID\":\"139\",\"LeiXing\":\"第三球大小\",\"FangXiang\":\"小\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("321", JSON.parseObject("{\"ID\":\"140\",\"LeiXing\":\"第三球單雙\",\"FangXiang\":\"單\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("322", JSON.parseObject("{\"ID\":\"141\",\"LeiXing\":\"第三球單雙\",\"FangXiang\":\"雙\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));

        put("411", JSON.parseObject("{\"ID\":\"152\",\"LeiXing\":\"第四球大小\",\"FangXiang\":\"大\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("412", JSON.parseObject("{\"ID\":\"153\",\"LeiXing\":\"第四球大小\",\"FangXiang\":\"小\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("421", JSON.parseObject("{\"ID\":\"154\",\"LeiXing\":\"第四球單雙\",\"FangXiang\":\"單\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("422", JSON.parseObject("{\"ID\":\"155\",\"LeiXing\":\"第四球單雙\",\"FangXiang\":\"雙\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));

        put("511", JSON.parseObject("{\"ID\":\"166\",\"LeiXing\":\"第五球大小\",\"FangXiang\":\"大\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("512", JSON.parseObject("{\"ID\":\"167\",\"LeiXing\":\"第五球大小\",\"FangXiang\":\"小\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("521", JSON.parseObject("{\"ID\":\"168\",\"LeiXing\":\"第五球單雙\",\"FangXiang\":\"單\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));
        put("522", JSON.parseObject("{\"ID\":\"169\",\"LeiXing\":\"第五球單雙\",\"FangXiang\":\"雙\",\"XiangMu\":\"5\",\"Type\":\"2\"}"));


    }};

}
