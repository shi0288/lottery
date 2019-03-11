package com.mcp.lottery.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.*;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.annotation.Type;
import com.mcp.lottery.util.cons.Cons;
import com.mcp.lottery.util.cons.HaomenCons;
import com.mcp.lottery.util.exception.ApiException;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Type(name = "豪门", value = "haomen")
public class HaomenPlugin extends Plugin {

    @Log
    private Logger logger;

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
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        Map<String, String> params = new HashMap<>();
        String url = plat.getLoginUrl().split("api")[0] + "api/utils/loginSecurityCode?" + new Date().getTime();
        ValidResult validResult = this.getValid(url, header, params);
        logger.info("豪门ORC:" + validResult);
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("cookie", validResult.getCookies());
        params.put("username", plat.getUsername());
        params.put("password", plat.getPassword());
        params.put("securityCode", validResult.getCode());
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl(), header, params);
        if (httpResult.getResult().indexOf("请求成功") > -1) {
            logger.info("豪门登陆成功");
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


    @Override
    public Double getBalance(Plat plat) {
        return null;
    }

    @Override
    public LotteryResult send(Plat plat, String game, String termCode, JSONArray list) {
        switch (game) {
            case Cons.Game.CQSSC: {
                return sendTerminal(plat, list, HaomenCons.TOU_ZHU_FFC, HaomenCons.PLAY_SSC);
            }
            case Cons.Game.TXFFC: {
                return sendTerminal(plat, list, HaomenCons.TOU_ZHU_FFC, HaomenCons.PLAY_FFC);
            }
        }
        return null;
    }


    public LotteryResult sendTerminal(Plat plat, JSONArray list, Map<String, String> gameTrans, String game) {
        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/x-www-form-urlencoded");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("cookie", plat.getCookies());
        header.put("Referer", plat.getTouzhuUrl().split("api")[0] + "lottery.html?lottery=" + game + "&type=1");
        JSONArray lotteryArr = this.transform(list, gameTrans, game);
        Map<String, String> params = new HashMap<>();
        params.put("text", lotteryArr.toString());
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl(), header, params);
        logger.info("豪门" + game + "投注:");
        logger.info(JSON.toJSONString(params));
        return getLotteryResult(httpResult);
    }

    public LotteryResult getLotteryResult(HttpResult httpResult) {
        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setResponse(httpResult.getResult());
        if (httpResult.getResult().indexOf("请求成功") > -1) {
            lotteryResult.setSuccess(true);
            return lotteryResult;
        } else if (httpResult.getResult().indexOf("您还没有登录") > -1) {
            throw new ApiException("登录已超时");
        }else{
            lotteryResult.setSuccess(false);
            return lotteryResult;
        }
    }

    public JSONArray transform(JSONArray list, Map<String, String> gameTrans, String game) {
        Map<String, JSONObject> sumMap = this.tranSum(list);
        JSONArray lotteryArr = new JSONArray();
        for (String key : sumMap.keySet()) {
            JSONObject temp = sumMap.get(key);
            int money = temp.getIntValue("money");
            JSONObject lottery = new JSONObject();
            if (money % 5 == 0) {
                lottery.put("model", "yuan");
                lottery.put("multiple", money / 5);
            } else {
                lottery.put("model", "jiao");
                lottery.put("multiple", money * 10 / 5);
            }
            lottery.put("content", gameTrans.get(key));
            lottery.put("lottery", game);
            lottery.put("issue", "");
            lottery.put("method", "dw");
            lottery.put("code", 1992);
            lottery.put("compress", false);
            lotteryArr.add(lottery);
        }
        return lotteryArr;
    }


}
