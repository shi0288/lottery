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
import com.mcp.lottery.util.cons.Cons;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, String> params = new HashMap<>();
            //区分游戏种类
            String url = null;
            if (game.equals(Cons.Game.CQSSC)) {
                url = qiaoqiaoying.getDataUrl().replace("$data$", DateUtil.DateToString(new Date(), "yyyyMMdd"));
            } else if (game.equals(Cons.Game.TXFFC)) {
                url = qiaoqiaoying.getDataUrlFfc().replace("$data$", DateUtil.DateToString(new Date(), "yyyyMMdd"));
            }
            HttpResult httpResult = HttpClientWrapper.sendGet(url, headers, params);
            return httpResult.getResult();
        }
        return null;
    }


    public String getPrize(String game,Date date) {
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
            if (dataObj == null || !dataObj.containsKey("subtotal")) {
                return;
            }
            if (dataObj.getIntValue("subtotal") > 0) {
                //如果当前有预测结果则不再执行
                return;
            }
        }
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
        prediction.setTerm(formatGameTermCode(game,issue));
        if(prediction.getTerm()==null){
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


    public void updatePrize(String game) {
        //获取当前期次
        Term term = termService.getOpenTerm(game);
        if (term == null) {
            //当前没有可售期次则不再执行
            return;
        }
        Term preTerm = new Term();
        preTerm.setGame(game);
        preTerm.setCloseAt(term.getOpenAt());
        preTerm = termService.get(preTerm);
        if (preTerm == null || !StringUtils.isEmpty(preTerm.getWinNumber())) {
            return;
        }
        String result = this.getPrize(game,preTerm.getOpenAt());
        if (StringUtils.isEmpty(result)) {
            return;
        }
        JSONObject resultObj = JSONObject.parseObject(result).getJSONObject("result");
        JSONArray jsonArray = resultObj.getJSONArray("hisLotteryInfo");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            if (temp.getString("preDrawIssue").equals(preTerm.getTermCode())) {
                Term update = new Term();
                update.setId(preTerm.getId());
                update.setWinNumber(temp.getString("preDrawCode"));
                termService.update(update);
                return;
            }
        }
    }



}
