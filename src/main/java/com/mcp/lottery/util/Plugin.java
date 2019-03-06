package com.mcp.lottery.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Plugin {


    public abstract void getAuthor(Plat plat);

    public abstract Double getBalance(Plat plat);

    public abstract LotteryResult send(Plat plat, String game, String termCode, JSONArray list);

    public Map<String, JSONObject> tranSum(JSONArray list) {
        Map<String, JSONObject> map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.getJSONObject(i);
            String key = jsonObject.getString("value");
            if (map.containsKey(key)) {
                JSONObject temp = map.get(key);
                temp.put("money",temp.getIntValue("money")+jsonObject.getIntValue("money"));
            } else {
                map.put(key, jsonObject);
            }
        }
        return map;
    }


}
