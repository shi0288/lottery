package com.mcp.lottery.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.util.*;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.annotation.Type;

import com.mcp.lottery.util.cons.Cons;
import com.mcp.lottery.util.cons.YingkaCons;
import com.mcp.lottery.util.exception.ApiException;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;


@Type(name = "赢咖", value = "yingka")
public class YingkaPlugin extends Plugin {

    @Log
    private Logger logger;


    @Override
    public void getAuthor(Plat plat) {
        for (int i = 0; i < 3; i++) {
            String cookie = getUserCookies(plat);
            if (!StringUtils.isEmpty(cookie)) {
                plat.setCookies(cookie);
                break;
            }
        }
    }


    public HttpResult getCookies(String url) {
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        header.put("referer", url);
        Map<String, Object> params = new HashMap<>();
        params.put("url", "/Account/Login/");
        HttpResult httpResult = HttpClientWrapper.sendGet(url + "/auth", header, params);
        List<String> cookies = httpResult.getCookies();
        header.put("cookie", org.apache.commons.lang.StringUtils.join(httpResult.getCookies(), ""));
        httpResult = HttpClientWrapper.sendGet(url + "/Account/Login/", header, null);
        httpResult.getCookies().addAll(cookies);
        httpResult.setParam("__RequestVerificationToken", this.getRequestVerificationToken(httpResult.getResult()));
        return httpResult;
    }

    public String getCodeSrc(String result) {
        Pattern p_script = Pattern.compile("(?<=id=\"CaptchaImage\" src=\")(.*?)(?=\")");
        Matcher m_script = p_script.matcher(result);
        while (m_script.find()) {
            String temp = m_script.group();
            return temp;
        }
        return null;
    }

    public String getRequestVerificationToken(String result) {
        Pattern p_script = Pattern.compile("(?<=name=\"__RequestVerificationToken\" type=\"hidden\" value=\")(.*?)(?=\")");
        Matcher m_script = p_script.matcher(result);
        while (m_script.find()) {
            String temp = m_script.group();
            return temp;
        }
        return null;
    }

    public String getUserCookies(Plat plat) {
        String url = plat.getLoginUrl();
        //获取访问权cookis
        HttpResult httpResult = this.getCookies(url);
        List<String> cookies = httpResult.getCookies();
        Map<String, String> loginParam = httpResult.getParam();
        loginParam.put("SkipTradeAgreement", "true");
        loginParam.put("LoginID", plat.getUsername());
        loginParam.put("Password", plat.getPassword());
        //获取验证码标示和session
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("referer", url + "/Account/Login/");
        Map<String, String> params = new HashMap<>();
        params.put("CaptchaError", "False");
        httpResult = HttpClientWrapper.sendPost(url + "/Account/Captcha", header, params);
        cookies.addAll(httpResult.getCookies());
        String cookie = org.apache.commons.lang.StringUtils.join(cookies, "");
        header.put("cookie", cookie);
        String imgUrl = this.getCodeSrc(httpResult.getResult());
        loginParam.put("CaptchaDeText", imgUrl.split("t=")[1]);
        //识别验证码
        ValidResult validResult = this.getValid(url + "/" + imgUrl, header, null);
        loginParam.put("CaptchaInputText", validResult.getCode());
        //登陆
        httpResult = HttpClientWrapper.sendPost(url + "/Account/LoginVerify", header, loginParam);
        cookies.addAll(httpResult.getCookies());
        if (httpResult.getResult().indexOf("<title>Object moved</title>") > -1) {
            logger.info("赢咖登陆成功");
            return org.apache.commons.lang.StringUtils.join(cookies, "");
        } else if (httpResult.getResult().indexOf("验证码错误") > -1) {
            returnError(validResult.getPicId());
        }
        return null;
    }

    @Async
    public void returnError(String id) {
        logger.info("识别错误失败，返回校验码积分");
        logger.info(HttpClientWrapper.orcError(id));
    }


    @Override
    public Double getBalance(Plat plat) {
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("referer", plat.getLoginUrl() + "/Bet/Index/60");
        header.put("cookie", plat.getCookies());
        Map<String, String> params = new HashMap<>();
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl() + "/Home/GetWalletAmount", header, params);
        return Double.valueOf(httpResult.getResult());
    }

    @Override
    public LotteryResult send(Plat plat, String game, String termCode, JSONArray list) {
        switch (game) {
            case Cons.Game.CQSSC: {
                return sendTerminal(plat, list, termCode, YingkaCons.PLAY_SSC);
            }
            case Cons.Game.TXFFC: {
                return sendTerminal(plat, list, termCode, YingkaCons.PLAY_FFC);
            }
        }
        return null;
    }

    public LotteryResult sendTerminal(Plat plat, JSONArray list, String termCode, String gameCode) {
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        header.put("content-type", "application/json; charset=UTF-8");
        header.put("referer", plat.getLoginUrl() + "/Bet/Index/" + gameCode);
        header.put("cookie", plat.getCookies());
        String uuid = UUID.randomUUID().toString();
        JSONObject parent = new JSONObject();
        parent.put("LotteryGameID", gameCode);
        parent.put("SerialNumber", termCode);
        parent.put("Bets", this.transform(list));
        parent.put("Schedules", new JSONArray());
        parent.put("StopIfWin", false);
        parent.put("BetMode", 0);
        parent.put("Guid", uuid);
        parent.put("IsLoginByWeChat", false);
        logger.info("赢咖" + gameCode + "投注:");
        logger.info(JSON.toJSONString(parent));
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl() + "/Bet/Confirm?tgid=" + uuid, header, parent);
        return getLotteryResult(httpResult);
    }

    public JSONArray transform(JSONArray list) {
        Map<String, JSONObject> sumMap = this.tranSum(list);
        JSONArray lotteryArr = new JSONArray();
        for (String key : sumMap.keySet()) {
            JSONObject temp = sumMap.get(key);
            int money = temp.getIntValue("money");
            JSONObject lottery = new JSONObject();
            lottery.put("BetTypeCode", 267);
            lottery.put("BetTypeName", "");
            lottery.put("Number", YingkaCons.TOU_ZHU.get(key));
            lottery.put("Position", "");
            lottery.put("Unit", 1);
            lottery.put("Multiple", money);
            lottery.put("ReturnRate", 0);
            lottery.put("IsCompressed", false);
            lottery.put("NoCommission", false);
            lotteryArr.add(lottery);
        }
        return lotteryArr;
    }

    public LotteryResult getLotteryResult(HttpResult httpResult) {
        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setResponse(httpResult.getResult());
        if (httpResult.getResult().indexOf("CreateDate") > -1) {
            lotteryResult.setResponse("投注成功");
            lotteryResult.setSuccess(true);
            return lotteryResult;
        } else if (httpResult.getCode() == 401) {
            throw new ApiException("登录已超时");
        } else {
            lotteryResult.setSuccess(false);
            return lotteryResult;
        }
    }


}
