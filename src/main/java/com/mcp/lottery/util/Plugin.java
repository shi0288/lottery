package com.mcp.lottery.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.util.annotation.Log;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import java.util.*;

public abstract class Plugin {

    @Log
    private Logger logger;


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

    public ValidResult getValid(String loginUrl,Map header,Map params) {
        ValidResult validResult = new ValidResult();
        for (int i = 0; i < 1; i++) {
            try {
                HttpResult httpResult = HttpClientWrapper.sendGetForBase64(loginUrl, header, params);
                List<String> cookies = httpResult.getCookies();
                String setCookies = "";
                for (int m = 0; m < cookies.size(); m++) {
                    String cookie = cookies.get(m);
                    String[] attr = cookie.split(";");
                    setCookies += (attr[0] + ";");
                }
                validResult.setCookies(setCookies);
                String json = HttpClientWrapper.orcValid(httpResult.getResult());
                logger.info("ORC返回:" + json);
                JSONObject jsonObject = JSONObject.parseObject(json);
                if (jsonObject.getString("err_str").equals("OK")) {
                    validResult.setCode(jsonObject.getString("pic_str"));
                    validResult.setPicId(jsonObject.getString("pic_id"));
                    return validResult;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }


}
