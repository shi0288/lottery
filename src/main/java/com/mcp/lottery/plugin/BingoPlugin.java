package com.mcp.lottery.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.service.TermService;
import com.mcp.lottery.util.*;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.annotation.Type;
import com.mcp.lottery.util.cons.BingoCons;
import com.mcp.lottery.util.cons.Cons;
import com.mcp.lottery.util.exception.ApiException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.util.*;


@Type(name = "宾果", value = "bingo")
public class BingoPlugin extends Plugin {

    @Log
    private Logger logger;

    @Autowired
    private TermService termService;


    @Override
    public void getAuthor(Plat plat) {
        for (int i = 0; i < 10; i++) {
            String cookie = getUserCookies(plat);
            if (!StringUtils.isEmpty(cookie)) {
                plat.setCookies(cookie);
                break;
            }
        }
    }

    public String getUserCookies(Plat plat) {
        ValidResult validResult = this.getValid(plat.getLoginUrl());
        logger.info("宾果ORC:" + validResult);
        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", validResult.getCookies());
        Map<String, String> params = new HashMap<>();
        params.put("username", plat.getUsername());
        params.put("password", plat.getPassword());
        params.put("fanbu", "3156242712");
        params.put("vcode", validResult.getCode());
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl(), header, params);
        if (httpResult.getCode() == 200) {
            logger.info("宾果登陆成功");
            return validResult.getCookies();
        }
        returnError(validResult.getPicId());
        return null;
    }

    @Async
    public void returnError(String id) {
        logger.info("识别错误失败，返回校验码积分");
        logger.info(HttpClientWrapper.orcError(id));
    }


    public ValidResult getValid(String loginUrl) {
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        ValidResult validResult = new ValidResult();
        for (int i = 0; i < 3; i++) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put("tnt", "vcode");
                params.put("rmt", String.valueOf(new Date().getTime()));
                HttpResult httpResult = HttpClientWrapper.sendGetForBase64(loginUrl.split("\\?")[0], header, params);
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

    public String getOrderId() {
        long a = new Date().getTime();
        long b = 2147483647L * 623;
        return String.valueOf(a - b);
    }


    @Override
    public Double getBalance(Plat plat) {
        return null;
    }

    @Override
    public LotteryResult send(Plat plat, String game, String termCode, JSONArray list) {
        switch (game) {
            case Cons.Game.CQSSC: {
                return sendSSC(plat, list, termCode);
            }
            case Cons.Game.TXFFC: {
                return sendFFC(plat, list, termCode);
            }
        }
        return null;
    }


    public LotteryResult sendSSC(Plat plat, JSONArray list, String termCode) {
        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        header.put("Referer", plat.getTouzhuUrl().split("\\?")[0] + "?tnt=igm&type=" + BingoCons.TYPE_SSC + "&groupId=" + BingoCons.GROUP_SSC + "&played=" + BingoCons.PLAY_SSC);
        Map<String, String> params = this.transform(list, BingoCons.TOU_ZHU_FFC, BingoCons.GROUP_SSC, BingoCons.PLAY_SSC, BingoCons.TYPE_SSC);
        Term query = new Term();
        query.setGame(Cons.Game.CQSSC);
        query.setTermCode(termCode);
        Term term = termService.get(query);
        params.put("para[type]", BingoCons.TYPE_SSC);
        params.put("para[actionNo]", termCode);
        params.put("para[kjTime]", DateUtil.getTimestamp(term.getCloseAt()));
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl(), header, params);
        logger.info("宾果时时彩投注:");
        logger.info(JSON.toJSONString(params));
        return getLotteryResult(httpResult);

    }


    public LotteryResult sendFFC(Plat plat, JSONArray list, String termCode) {
        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        header.put("Referer", plat.getTouzhuUrl().split("\\?")[0] + "?tnt=igm&type=" + BingoCons.TYPE_FFC + "&groupId=" + BingoCons.GROUP_FFC + "&played=" + BingoCons.PLAY_FFC);
        Map<String, String> params = this.transform(list, BingoCons.TOU_ZHU_FFC, BingoCons.GROUP_FFC, BingoCons.PLAY_FFC, BingoCons.TYPE_FFC);
        Term query = new Term();
        query.setGame(Cons.Game.TXFFC);
        query.setTermCode(termCode);
        Term term = termService.get(query);
        params.put("para[type]", BingoCons.TYPE_FFC);
        params.put("para[actionNo]", termCode);
        params.put("para[kjTime]", DateUtil.getTimestamp(term.getCloseAt()));
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl(), header, params);
        logger.info("宾果分分彩投注:");
        logger.info(JSON.toJSONString(params));
        return getLotteryResult(httpResult);
    }

    public LotteryResult getLotteryResult(HttpResult httpResult) {
        LotteryResult lotteryResult = new LotteryResult();
        if (httpResult.getCode() == 200) {
            lotteryResult.setSuccess(true);
            lotteryResult.setResponse("投注成功");
            return lotteryResult;
        } else {
            throw new ApiException("登录已超时");
        }
    }

    public Map transform(JSONArray list, Map<String, String> gameTrans, String group, String play, String type) {
        Map<String, JSONObject> sumMap = this.tranSum(list);
        Map<String, String> params = new HashMap<>();
        int i = 0;
        for (String key : sumMap.keySet()) {
            JSONObject temp = sumMap.get(key);
            int money = temp.getIntValue("money");
            if (money % 5 == 0) {
                params.put("code[" + i + "][mode]", "1");
                params.put("code[" + i + "][beiShu]", String.valueOf(money / 5));
            } else {
                params.put("code[" + i + "][mode]", "0.1");
                params.put("code[" + i + "][beiShu]", String.valueOf(money * 10 / 5));
            }
            params.put("code[" + i + "][fanDian]", "0");
            params.put("code[" + i + "][bonusProp]", "20.000");
            params.put("code[" + i + "][orderId]", getOrderId());
            params.put("code[" + i + "][actionData]", gameTrans.get(key));
            params.put("code[" + i + "][actionNum]", "5");
            params.put("code[" + i + "][weiShu]", "0");
            params.put("code[" + i + "][playedGroup]", group);
            params.put("code[" + i + "][playedId]", play);
            params.put("code[" + i + "][type]", type);
            i++;
        }
        return params;
    }


}
