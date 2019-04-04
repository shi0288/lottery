package com.mcp.lottery.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mcp.lottery.mapper.QiaoqiaoyingMapper;
import com.mcp.lottery.mapper.TermMapper;
import com.mcp.lottery.model.Prediction;
import com.mcp.lottery.model.Qiaoqiaoying;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.DateUtil;
import com.mcp.lottery.util.HttpClientWrapper;
import com.mcp.lottery.util.HttpResult;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.cons.Cons;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class QiaoqiaoyingService {

    @Autowired
    private QiaoqiaoyingMapper qiaoqiaoyingMapper;

    @Autowired
    private TermService termService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private TermMapper termMapper;

    @Log
    private Logger logger;


    public Qiaoqiaoying get() {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Qiaoqiaoying query = new Qiaoqiaoying();
        List<Qiaoqiaoying> list = qiaoqiaoyingMapper.select(query);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public boolean update(Qiaoqiaoying qiaoqiaoying) {
        if (qiaoqiaoyingMapper.updateByPrimaryKeySelective(qiaoqiaoying) == 1) {
            return true;
        }
        return false;
    }

    public String getResult(String game) {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Qiaoqiaoying query = new Qiaoqiaoying();
        List<Qiaoqiaoying> list = qiaoqiaoyingMapper.select(query);
        if (list.size() == 1) {
            Qiaoqiaoying qiaoqiaoying = list.get(0);
            if (qiaoqiaoying.getExpiresIn() == null || qiaoqiaoying.getExpiresIn().getTime() - new Date().getTime() <= 500) {
                if (!updateToken(qiaoqiaoying)) {
                    return null;
                }
            }
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + qiaoqiaoying.getToken());
            headers.put("abp.tenantid", "1");
            Map<String, String> params = new HashMap<>();
            //区分游戏种类
            String url = null;
            if (game.equals(Cons.Game.CQSSC)) {
                url = qiaoqiaoying.getDataUrl().replace("$data$", DateUtil.DateToString(new Date(), "yyyyMMdd"));
            } else if (game.equals(Cons.Game.TXFFC)) {
                url = qiaoqiaoying.getDataUrlFfc().replace("$data$", DateUtil.DateToString(new Date(), "yyyyMMdd"));
            }
            HttpResult httpResult = HttpClientWrapper.sendGet(url, headers, params);
            if (httpResult.getResult().indexOf("prediction") > -1) {
                return httpResult.getResult();
            }
            updateToken(qiaoqiaoying);
        }
        return null;
    }


    public String getPrize(String game, Date date) {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Qiaoqiaoying query = new Qiaoqiaoying();
        List<Qiaoqiaoying> list = qiaoqiaoyingMapper.select(query);
        if (list.size() == 1) {
            Qiaoqiaoying qiaoqiaoying = list.get(0);
            Map<String, String> params = new HashMap<>();
            String url = null;
            if (game.equals(Cons.Game.CQSSC)) {
                if (StringUtils.isEmpty(qiaoqiaoying.getPrizeUrl())) {
                    return null;
                }
                url = qiaoqiaoying.getPrizeUrl().replace("$data$", DateUtil.DateToString(date, "yyyyMMdd"));
            } else if (game.equals(Cons.Game.TXFFC)) {
                if (StringUtils.isEmpty(qiaoqiaoying.getPrizeUrlFfc())) {
                    return null;
                }
                url = qiaoqiaoying.getPrizeUrlFfc().replace("$data$", DateUtil.DateToString(date, "yyyyMMdd"));
            }
            HttpResult httpResult = HttpClientWrapper.sendGet(url, null, params);
            return httpResult.getResult();
        }
        return null;
    }


    public boolean updateToken(Qiaoqiaoying qiaoqiaoying) {
        Map<String, String> headers = new HashMap<>();
        headers.put("abp.tenantid", "1");
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "Admin.Client");
        params.put("client_secret", "admin");
        params.put("grant_type", "password");
        params.put("username", qiaoqiaoying.getUsername());
        params.put("password", qiaoqiaoying.getPassword());
        HttpResult httpResult = HttpClientWrapper.sendPost(qiaoqiaoying.getLoginUrl(), headers, params);
        JSONObject jsonObject = JSON.parseObject(httpResult.getResult());
        if (jsonObject.containsKey("access_token")) {
            qiaoqiaoying.setToken(jsonObject.getString("access_token"));
            qiaoqiaoying.setRefreshToken(jsonObject.getString("refresh_token"));
            qiaoqiaoying.setExpiresIn(DateUtil.addSecond(new Date(), jsonObject.getIntValue("expires_in")));
            qiaoqiaoyingMapper.updateByPrimaryKeySelective(qiaoqiaoying);
            return true;
        }
        return false;
    }


    public void historyPrediction(String game) {
        String res = this.getResult(game);
        if (StringUtils.isEmpty(res)) {
            //获取预测为空则返回
            return;
        }
        JSONObject result = JSONObject.parseObject(res).getJSONObject("result");
        JSONObject history = result.getJSONObject("history");
        JSONArray items = history.getJSONArray("items");
        String termDay = DateUtil.DateToString(new Date(), "yyyyMMdd");
        for (int i = 0; i < items.size(); i++) {
            try {
                JSONObject temp = items.getJSONObject(i);
                String str = temp.getString("drawIssue");
                if (str.equals("0000")) {
                    str = "1440";
                }
                String term = termDay + str;
                Prediction prediction = new Prediction();
                prediction.setGame(game);
                prediction.setTerm(term);
                Prediction target = predictionService.get(prediction);
                if (target != null) {
                    continue;
                }
                JSONObject data = new JSONObject();
                data.put("subtotal", temp.get("betAmounts"));
                JSONArray dataArr = new JSONArray();
                data.put("items", dataArr);
                JSONArray anlsItems = temp.getJSONArray("anlsItems");
                for (int m = 0; m < anlsItems.size(); m++) {
                    JSONObject item = anlsItems.getJSONObject(m);
                    item.remove("resultState");
                    dataArr.add(item);
                }
                prediction.setData(data.toString());
//                predictionService.saveOrUpdate(prediction);
            } catch (Exception e) {
            }
        }
    }


    //todo
    public String getPredictionTxffc() {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Qiaoqiaoying query = new Qiaoqiaoying();
        List<Qiaoqiaoying> list = qiaoqiaoyingMapper.select(query);
        if (list.size() == 1) {
            Qiaoqiaoying qiaoqiaoying = list.get(0);
            if (qiaoqiaoying.getExpiresIn() == null || qiaoqiaoying.getExpiresIn().getTime() - new Date().getTime() <= 500) {
                if (!updateToken(qiaoqiaoying)) {
                    return null;
                }
            }
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + qiaoqiaoying.getToken());
            headers.put("adp.tenantid", "1");
            JSONObject params = new JSONObject();
            params.put("name", "ZhongQingShiShiCai");
            params.put("baseCode", "1");
            params.put("ctlgAnlsSysId", "8c8357d847f8223");
            //区分游戏种类
            String url = "http://www.qiaoqiaoying.cn/api/lottery/forecastToAutoBet";
            HttpResult httpResult = HttpClientWrapper.sendPost(url, headers, params);
            return httpResult.getResult();
        }
        return null;
    }


    public void updatePrediction(String game) {
        //获取当前期次
        Term term = termService.getOpenTerm(game);
        if (term == null) {
            //当前没有可售期次则不再执行
            return;
        }
        //获取当前期次预测结果
        Prediction query = new Prediction();
        query.setGame(game);
        query.setTerm(term.getTermCode());
        query = predictionService.get(query);
        if (query != null) {
            JSONObject dataObj = JSONObject.parseObject(query.getData());
            if (dataObj.containsKey("subtotal")) {
                //如果当前有预测结果则不再执行
                if (dataObj.getIntValue("subtotal") > 0) {
                    return;
                } else {
                    //如果当前秒数大于30 不再获取
                    Calendar c = Calendar.getInstance();
                    int second = c.get(Calendar.SECOND);
                    if (second > 30) {
                        return;
                    }
                }
            }
        }
        logger.info("获取预测：" + game + "_" + term.getTermCode());
        String res = this.getResult(game);
        if (StringUtils.isEmpty(res)) {
            //获取预测为空则返回
            return;
        }
//        logger.error("获取预测结果：" + res);
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONObject result = jsonObject.getJSONObject("result");
        if (!result.containsKey("lastLottery")) {
            return;
        }
        JSONObject lastLottery = result.getJSONObject("lastLottery");
        Integer lastIssue = 0;
        if (lastLottery != null) {
            lastIssue = lastLottery.getInteger("issue");
        }
        Integer issue = lastIssue + 1;
        Prediction prediction = new Prediction();
        prediction.setGame(game);
        prediction.setTerm(formatGameTermCode(game, issue));
        if (prediction.getTerm() == null) {
            return;
        }
        Prediction predictionDB = predictionService.get(prediction);
        if (predictionDB == null) {
            predictionDB = prediction;
            predictionDB.setData(result.getString("prediction"));
            predictionService.saveOrUpdate(predictionDB);
        } else {
            try {
                JSONObject temp = JSONObject.parseObject(result.getString("prediction"));
                JSONObject temp_1 = JSONObject.parseObject(predictionDB.getData());
                if (temp.getIntValue("subtotal") > 0 && temp_1.getIntValue("subtotal") == 0) {
                    predictionDB.setData(result.getString("prediction"));
                    predictionService.saveOrUpdate(predictionDB);
                }
            } catch (Exception e) {
            }
        }
    }


    public String formatGameTermCode(String game, int issue) {
        if (game.equals(Cons.Game.CQSSC)) {
            return DateUtil.DateToString(new Date(), "yyyyMMdd") + new DecimalFormat("000").format(issue);
        } else if (game.equals(Cons.Game.TXFFC)) {
            return DateUtil.DateToString(new Date(), "yyyyMMdd") + new DecimalFormat("0000").format(issue);
        }
        return null;
    }

    public void updatePrize(String game, Date date) {
        String result = this.getPrize(game, date);
        if (StringUtils.isEmpty(result)) {
            return;
        }
        JSONObject resultObj = JSONObject.parseObject(result).getJSONObject("result");
        JSONArray jsonArray = resultObj.getJSONArray("hisLotteryInfo");
        List<Term> list = termService.getNoneWinNumber(game, DateUtil.DateToString(date, "yyyyMMdd"));
        for (int m = 0; m < list.size(); m++) {
            Term targetTerm = list.get(m);
            analysisTerm(targetTerm, jsonArray);
        }
    }

    public void analysisTerm(Term target, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            if (temp.getString("preDrawIssue").equals(target.getTermCode())) {
                Term update = new Term();
                update.setId(target.getId());
                update.setWinNumber(temp.getString("preDrawCode"));
                termService.update(update);
                return;
            }
        }
    }


}
