//package com.mcp.lottery.plugin;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mcp.lottery.model.Plat;
//import com.mcp.lottery.util.*;
//import com.mcp.lottery.util.annotation.Log;
//import com.mcp.lottery.util.annotation.Type;
//import org.slf4j.Logger;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.List;
//
//
//@Type(name = "宾果", value = "jinzhizun")
//public class BingoPlugin extends Plugin {
//
//    @Log
//    private Logger logger;
//
//
//    @Override
//    public void getAuthor(Plat plat) {
//        ValidResult validResult = this.getValid();
//        System.out.println(validResult);
//        Map<String, String> header = new HashMap<>();
//        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
//        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
//        header.put("cookie", validResult.getCookies());
//        Map<String, String> params = new HashMap<>();
//        params.put("username", "papa001");
//        params.put("password", "wu789789");
//        params.put("fanbu", "3156242712");
//        params.put("vcode", validResult.getCode());
//        HttpResult httpResult = HttpClientWrapper.sendPost("http://www.503319.com/index.php?tnt=uld", header, params);
//        System.out.println(httpResult.getResult());
//    }
//
//    public ValidResult getValid() {
//        Map<String, String> header = new HashMap<>();
//        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
//        ValidResult validResult = new ValidResult();
//        for (int i = 0; i < 3; i++) {
//            try {
//                Map<String, String> params = new HashMap<>();
//                params.put("tnt", "vcode");
//                params.put("rmt", String.valueOf(new Date().getTime()));
//                HttpResult httpResult = HttpClientWrapper.sendGetForBase64("http://www.503319.com/index.php", header, params);
//                List<String> cookies = httpResult.getCookies();
//                String setCookies = "";
//                for (int m = 0; m < cookies.size(); m++) {
//                    String cookie = cookies.get(m);
//                    String[] attr = cookie.split(";");
//                    setCookies += (attr[0] + ";");
//                }
//                validResult.setCookies(setCookies);
//                String json = HttpClientWrapper.orcValid(httpResult.getResult());
//                JSONObject jsonObject = JSONObject.parseObject(json);
//                if (jsonObject.getString("err_str").equals("OK")) {
//                    validResult.setCode(jsonObject.getString("pic_str"));
//                    return validResult;
//                }
//            } catch (Exception e) {
//            }
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//        Map<String, String> header = new HashMap<>();
//        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
//        header.put("cookie", "PHPSESSID=5819008ecfa00fd7f539dc0da10611e6;");
//        Map<String, String> params = new HashMap<>();
//        HttpResult httpResult = HttpClientWrapper.sendPost("http://www.503319.com/index.php", header, params);
//        System.out.println(httpResult.getResult());
//
////
////        BingoPlugin bingoPlugin=new BingoPlugin();
////        bingoPlugin.getAuthor(null);
//
//
//
//
//
//
//    }
//
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
