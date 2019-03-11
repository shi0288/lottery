package com.mcp.lottery.plugin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.util.*;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.annotation.Type;
import com.mcp.lottery.util.cons.Cons;
import com.mcp.lottery.util.cons.TaiyangCons;
import com.mcp.lottery.util.exception.ApiException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.util.*;


@Type(name = "太阳", value = "taiyang")
public class TaiyangPlugin extends Plugin {

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
        header.put("content-type", "application/x-www-form-urlencoded");
        header.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        Map<String, String> params = new HashMap<>();
        params.put("username", plat.getUsername());
        params.put("password", plat.getPassword());
        params.put("captcha", "");
        params.put("captchaId", UUID.randomUUID().toString());
        params.put("grant_type", "password");
        params.put("client_id", "legend");
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getLoginUrl(), header, params);
        List<String> cookies = httpResult.getCookies();
        String setCookies = "";
        for (int i = 0; i < cookies.size(); i++) {
            String cookie = cookies.get(i);
            String[] attr = cookie.split(";");
            setCookies += (attr[0] + ";");
        }
        plat.setCookies(setCookies);
        String access_token = JSONObject.parseObject(httpResult.getResult()).getString("access_token");
        plat.setAssist(access_token);
    }

    @Override
    public Double getBalance(Plat plat) {
        String[] urlArr = plat.getBalanceUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "GET");
        header.put(":path:", "/" + urlArr[1]);
        header.put(":scheme:", plat.getBalanceUrl().split(":")[0]);
        header.put("content-type", "text/plain");
        header.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        header.put("cookie", plat.getCookies());
        header.put("authorization", "Bearer " + plat.getAssist());
        HttpResult httpResult = HttpClientWrapper.sendGet(plat.getBalanceUrl(), header, null);
        try {
            JSONObject jsonObject = JSONObject.parseObject(httpResult.getResult());
            if (jsonObject.containsKey("UserMoney")) {
                return jsonObject.getDouble("UserMoney");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String transTermCodeForFFC(String termCode) {
        String day = termCode.substring(0, 8);
        int num = Integer.parseInt(termCode.substring(8, termCode.length()));
        if (num < 10) {
            return day+"00"+num;
        }
        if (num < 100) {
            return day+"0"+num;
        }
        if (num < 1000) {
            return day+num;
        }
        return termCode;
    }


    @Override
    public LotteryResult send(Plat plat, String game, String termCode, JSONArray list) {
        switch (game) {
            case Cons.Game.CQSSC: {
                return sendSSC(plat, list, termCode);
            }
            case Cons.Game.TXFFC: {
                termCode = transTermCodeForFFC(termCode);
                return sendFFC(plat, list, termCode);
            }
        }
        return null;
    }

    public Map getHeader(String url, Plat plat) {
        String[] urlArr = url.replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "POST");
        header.put(":path:", "/" + urlArr[1]);
        header.put(":scheme:", url.split(":")[0]);
        header.put("content-type", "application/json");
        header.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        header.put("authorization", "Bearer " + plat.getAssist());
        header.put("cookie", plat.getCookies());
        return header;
    }

    public LotteryResult sendSSC(Plat plat, JSONArray list, String termCode) {
        //赔率获取
//        Double rate = this.getNewOdds(plat, TaiyangCons.SSC_CODE);
        Double rate = TaiyangCons.RATE;
        String touzhuStr = transform(list, TaiyangCons.TOU_ZHU_SSC, rate);
        String[] arr = touzhuStr.split("\\|");
        int amount = 0;
        for (int i = 0; i < arr.length; i++) {
            amount += Integer.parseInt(arr[i].split("@")[4]);
        }
        JSONObject param = new JSONObject();
        param.put("BettingInfoIssueList", new JSONArray());
        //todo
        param.put("BettingInfoIssueString", termCode + "@1@1@" + amount);
        param.put("BettingItemList", new JSONArray());
        param.put("BettingItemString", touzhuStr);
        //todo
        param.put("BettingMoney", amount);
        param.put("IsChaseNo", 0);
        param.put("IsStopAfterWinning", 0);
        //todo
        param.put("IssueNo", termCode);
        param.put("LotteryCode", TaiyangCons.SSC_CODE);
        param.put("LotteryPlayModeCode", TaiyangCons.SSC_MODE);
        //todo
        param.put("noteTotal", arr.length);
        logger.info("太阳时时彩投注:" + param.toString());
        Map<String, String> header = this.getHeader(plat.getTouzhuUrl(), plat);
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl(), header, param);
        LotteryResult lotteryResult = new LotteryResult();
        if (httpResult.getResult().indexOf("投注成功") > -1 || httpResult.getResult().indexOf("截止") > -1) {
            lotteryResult.setSuccess(true);
        } else {
            throw new ApiException("登录已超时");
        }
        lotteryResult.setResponse(httpResult.getResult());
        return lotteryResult;
    }


    public LotteryResult sendFFC(Plat plat, JSONArray list, String termCode) {
        //赔率获取
//        Double rate = this.getNewOdds(plat, TaiyangCons.FFC_CODE);
        Double rate = TaiyangCons.RATE;
        String touzhuStr = transform(list, TaiyangCons.TOU_ZHU_FFC, rate);
        String[] arr = touzhuStr.split("\\|");
        int amount = 0;
        for (int i = 0; i < arr.length; i++) {
            amount += Integer.parseInt(arr[i].split("@")[4]);
        }
        JSONObject param = new JSONObject();
        param.put("BettingInfoIssueList", new JSONArray());
        //todo
        param.put("BettingInfoIssueString", termCode + "@1@1@" + amount);
        param.put("BettingItemList", new JSONArray());
        param.put("BettingItemString", touzhuStr);
        //todo
        param.put("BettingMoney", amount);
        param.put("IsChaseNo", 0);
        param.put("IsStopAfterWinning", 0);
        //todo
        param.put("IssueNo", termCode);
        param.put("LotteryCode", TaiyangCons.FFC_CODE);
        param.put("LotteryPlayModeCode", TaiyangCons.FFC_MODE);
        //todo
        param.put("noteTotal", arr.length);
        logger.info("太阳分分彩投注:" + param.toString());
        Map<String, String> header = this.getHeader(plat.getTouzhuUrl(), plat);
        HttpResult httpResult = HttpClientWrapper.sendPost(plat.getTouzhuUrl(), header, param);
        LotteryResult lotteryResult = new LotteryResult();
        if (httpResult.getResult().indexOf("投注成功") > -1 || httpResult.getResult().indexOf("截止") > -1) {
            lotteryResult.setSuccess(true);
        } else {
            throw new ApiException("登录已超时");
        }
        lotteryResult.setResponse(httpResult.getResult());
        return lotteryResult;
    }


    public Double getNewOdds(Plat plat, String code) {
        String getUrl = "api/Lottery/GetLotterys";
        String[] urlArr = plat.getTouzhuUrl().replace("https://", "").replace("http://", "").split("\\/");
        Map<String, String> header = new HashMap<>();
        header.put(":authority:", urlArr[0]);
        header.put(":method:", "GET");
        header.put(":path:", "/" + getUrl);
        header.put(":scheme:", plat.getTouzhuUrl().split(":")[0]);
        header.put("content-type", "text/plain");
        header.put("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        header.put("authorization", "Bearer " + plat.getAssist());
        header.put("cookie", plat.getCookies());
        HttpResult httpResult = HttpClientWrapper.sendGet(plat.getTouzhuUrl().split(":")[0] + "://" + urlArr[0] + "/" + getUrl, header, null);
        try {
            JSONArray jsonArray = JSONArray.parseArray(httpResult.getResult());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                if (temp.getString("LotteryCode").equals(code)) {
                    return temp.getDouble("RewardUp");
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    public String transform(JSONArray list, Map<String, String> gameTrans, Double rate) {
        DecimalFormat rateIntFormat = new DecimalFormat("#");
        String rateIntStr = rateIntFormat.format(rate);
        DecimalFormat rateFormat = new DecimalFormat("#0.000");
        String rateStr = rateFormat.format(ArithUtil.div(rate, 1000));
        List<String> arr = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            JSONObject temp = list.getJSONObject(i);
            String touUrl = gameTrans.get(temp.getString("value"));
            touUrl = touUrl.replace("money", String.valueOf(temp.getIntValue("money")));
            touUrl = touUrl.replace("rateInt", rateIntStr);
            touUrl = touUrl.replace("rate", rateStr);
            arr.add(touUrl);
        }
        //合单
        Map<String, String> result = new HashMap();
        for (int i = 0; i < arr.size(); i++) {
            String parent = arr.get(i);
            String[] parentArr = parent.split("@");
            if (result.containsKey(parentArr[0])) {
                String temp = result.get(parentArr[0]);
                String[] tempArr = temp.split("@");
                int money = Integer.parseInt(tempArr[4]) + Integer.parseInt(parentArr[4]);
                tempArr[4] = String.valueOf(money);
                result.put(parentArr[0], StringUtils.join(tempArr, "@"));
            } else {
                result.put(parentArr[0], parent);
            }
        }
        List<String> trans = new ArrayList();
        int index = 1;
        for (String key : result.keySet()) {
            trans.add(result.get(key).replace("index", String.valueOf(index)));
            index++;
        }
        return StringUtils.join(trans, "|");
    }


}
