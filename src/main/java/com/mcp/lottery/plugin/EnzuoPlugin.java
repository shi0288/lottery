//package com.mcp.lottery.plugin;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mcp.lottery.model.Plat;
//import com.mcp.lottery.util.HttpClientWrapper;
//import com.mcp.lottery.util.HttpResult;
//import com.mcp.lottery.util.LotteryResult;
//import com.mcp.lottery.util.Plugin;
//import com.mcp.lottery.util.annotation.Log;
//import com.mcp.lottery.util.annotation.Type;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//
//import javax.script.Invocable;
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Type(name = "恩佐", value = "enzuo")
//public class EnzuoPlugin extends Plugin {
//
//    @Log
//    private Logger logger;
//
//    @Override
//    public void getAuthor(Plat plat) {
//        Map<String, String> header = new HashMap<>();
//        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
//        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
//        header.put("referer", plat.getLoginUrl()+"?s=/WebPublic/login");
////        header.put("x-forwarded-for", "https://888.enzuo88.com/?s=/WebPublic/login");
//        Map<String, String> params = new HashMap<>();
//        params.put("zipinfo", this.zipInfo(plat.getUsername(), plat.getPassword()));
//        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl()+"?s=/ApiPublic/login/", header, params);
//        if (httpResult.getResult().indexOf("\\u767b\\u5f55\\u6210\\u529f") > -1) {
//            List<String> cookies = httpResult.getCookies();
//            String setCookies = "";
//            for (int i = 0; i < cookies.size(); i++) {
//                String cookie = cookies.get(i);
//                String[] attr = cookie.split(";");
//                setCookies += (attr[0] + ";");
//            }
//            plat.setCookies(setCookies);
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        Plat plat = new Plat();
//        plat.setLoginUrl("https://888.enzuo88.com/");
//        plat.setUsername("papa001");
//        plat.setPassword("a1234567");
//        EnzuoPlugin enzuoPlugin = new EnzuoPlugin();
//        enzuoPlugin.getAuthor(plat);
//    }
//
//    public String zipInfo(String username, String password) {
//        try {
//            File file = new File("/data/code/workspace/lottery/src/main/resources/deflate.js");
//            InputStream input = new FileInputStream(file);
//            String jsStr = IOUtils.toString(input, "utf8");
//            ScriptEngineManager mgr = new ScriptEngineManager();
//            ScriptEngine engine = mgr.getEngineByName("javascript");
//            engine.eval(jsStr);
//            Invocable inv = (Invocable) engine;
//            JSONObject obj = new JSONObject();
//            obj.put("user_account", username);
//            obj.put("user_password", password);
//            obj.put("captcha", "ha");
//            Object res = inv.invokeFunction("zip_deflate", obj.toString());
//            res = inv.invokeFunction("base64_encode", res);
//            return res.toString();
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    @Override
//    public Double getBalance(Plat plat) {
//        return null;
//    }
//
//    @Override
//    public LotteryResult send(Plat plat, String game, String termCode, JSONArray list) {
//        return null;
//    }
//}
