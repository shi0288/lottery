package com.mcp.lottery.plugin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.util.LotteryResult;
import com.mcp.lottery.util.Plugin;
import com.mcp.lottery.util.HttpClientWrapper;
import com.mcp.lottery.util.HttpResult;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.annotation.Type;
import com.mcp.lottery.util.cons.Cons;
import com.mcp.lottery.util.cons.JinzhizunCons;
import com.mcp.lottery.util.exception.ApiException;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Type(name = "金至尊", value = "jinzhizun")
public class JinzhizunPlugin extends Plugin {

    @Log
    private Logger logger;

    @Override
    public void getAuthor(Plat plat) {
        String[] urlArr = plat.getLoginUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + urlArr[1]);
        header.put(":scheme:", plat.getLoginUrl().split(":")[0]);
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        Map<String, String> params = new HashMap<>();
        params.put("r", String.valueOf(Math.random()));
        params.put("action", "login");
        params.put("username", plat.getUsername());
        params.put("password", plat.getPassword());
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl(), header, params);
        List<String> cookies = httpResult.getCookies();
        String setCookies = "";
        for (int i = 0; i < cookies.size(); i++) {
            String cookie = cookies.get(i);
            String[] attr = cookie.split(";");
            setCookies += (attr[0] + ";");
        }
        try {
            String uid = JSONObject.parseObject(httpResult.getResult()).getString("uid");
            plat.setCookies(setCookies);
            plat.setAssist(uid);
        }catch (Exception e){
        }
    }


    @Override
    public Double getBalance(Plat plat) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT 0800 (中国标准时间)'");
        String getUrl = "uid=" + plat.getAssist() + "&Time=";
        try {
            String dateStr = URLEncoder.encode(dateFormat.format(date), "utf-8");
            getUrl += dateStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (plat.getBalanceUrl().indexOf("?") > -1) {
            getUrl = "&" + getUrl;
        } else {
            getUrl = "?" + getUrl;
        }
        String[] urlArr = plat.getBalanceUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + urlArr[1] + getUrl);
        header.put(":scheme:", plat.getBalanceUrl().split(":")[0]);
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getBalanceUrl() + getUrl, header, new HashMap<>());
        if (httpResult.getResult().indexOf("登录已超时") > -1) {
            return null;
        }
        return JSONObject.parseObject(httpResult.getResult()).getDouble("YuWo");
    }


    @Override
    public LotteryResult send(Plat plat,String game,String termCode, JSONArray list) {
        if(game.equals(Cons.Game.CQSSC)){
            return sendSSC(plat,list);
        }
        return null;
    }

    public LotteryResult sendSSC(Plat plat, JSONArray list){
        resetOdds(plat);
        LotteryResult lotteryResult = new LotteryResult();
        String touzhu = this.transform(plat, list, lotteryResult);
        logger.info("金至尊时时彩投注:"+touzhu);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-M-dd HH:mm:ss.SSS");
        String getUrl = "uid=" + plat.getAssist() + "&InsetOrderTime=";
        try {
            String dateStr = URLEncoder.encode(dateFormat.format(date), "utf-8");
            getUrl += dateStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (plat.getTouzhuUrl().indexOf("?") > -1) {
            getUrl = "&" + getUrl;
        } else {
            getUrl = "?" + getUrl;
        }
        String[] urlArr = plat.getTouzhuUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + urlArr[1] + getUrl);
        header.put(":scheme:", plat.getTouzhuUrl().split(":")[0]);
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        Map<String, String> params = new HashMap<>();
        params.put("List", touzhu);
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl() + getUrl, header, params);
        if (httpResult.getResult().equals("投注成功")) {
            lotteryResult.setSuccess(true);
        } else {
            lotteryResult.setSuccess(false);
        }
        lotteryResult.setResponse(httpResult.getResult());
        return lotteryResult;
    }


    public void resetOdds(Plat plat) {
        String getUrl = "SSC.aspx?&uid=" + plat.getAssist();
        String[] urlArr = plat.getTouzhuUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + getUrl);
        header.put(":scheme:", plat.getTouzhuUrl().split(":")[0]);
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        HttpResult httpResult = HttpClientWrapper.sendGet("https://hy1.868akk.com/PostHandler.ashx".split(":")[0] + "://" + urlArr[0] + "/" + getUrl, header, null);
        if (httpResult.getResult().indexOf("登录已超时") > -1) {
            throw new ApiException("登录已超时");
        }
    }

    public JSONArray getNewOdds(Plat plat) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT 0800 (中国标准时间)'");
        String getUrl = "Ajax/Ajax_Kc.aspx?XiangMu=5&uid=" + plat.getAssist() + "&Time=";
        try {
            String dateStr = URLEncoder.encode(dateFormat.format(date), "utf-8");
            getUrl += dateStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] urlArr = plat.getTouzhuUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + getUrl);
        header.put(":scheme:", plat.getTouzhuUrl().split(":")[0]);
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        HttpResult httpResult = HttpClientWrapper.sendGet(plat.getTouzhuUrl().split(":")[0] + "://" + urlArr[0] + "/" + getUrl, header, null);
        if (httpResult.getResult().indexOf("登录") > -1) {
            throw new ApiException("登录已超时");
        }
        return JSONObject.parseObject(httpResult.getResult()).getJSONArray("NewOdds");
    }

    public String transform(Plat plat, JSONArray list, LotteryResult lotteryResult) {
        JSONArray data = new JSONArray();
        JSONArray odds = this.getNewOdds(plat);
        JSONArray arr = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject temp = list.getJSONObject(i);
            JSONObject curTou = (JSONObject) JinzhizunCons.TOU_ZHU.get(temp.getString("value")).clone();
            curTou.put("Money", temp.getIntValue("money"));
            this.addOdds(odds, curTou);
            data.add(new JSONObject() {{
                put("log_id", temp.getString("log_id"));
                put("odds", curTou.getString("Odds"));
            }});
            arr.add(curTou);
        }
        lotteryResult.setData(data);
        //合单
        Map<String, JSONObject> result = new HashMap();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject parent = arr.getJSONObject(i);
            if (result.containsKey(parent.getString("ID"))) {
                JSONObject temp = result.get(parent.getString("ID"));
                temp.put("Money", temp.getIntValue("Money") + parent.getIntValue("Money"));
            } else {
                result.put(parent.getString("ID"), parent);
            }
        }
        JSONArray trans = new JSONArray();
        trans.addAll(result.values());
        return trans.toJSONString();
    }

    public void addOdds(JSONArray odds, JSONObject object) {
        for (int i = 0; i < odds.size(); i++) {
            JSONObject temp = odds.getJSONObject(i);
            if (temp.getString("ID").equals(object.getString("ID"))) {
                object.put("Odds", temp.getString("Odds"));
            }
        }
    }

}
