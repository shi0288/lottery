package com.mcp.lottery.task;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Prediction;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.service.PredictionService;
import com.mcp.lottery.service.QiaoqiaoyingService;
import com.mcp.lottery.service.TermService;
import com.mcp.lottery.util.DateUtil;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.cons.Cons;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;

@Component
public class QiaoqiaoyingTask {

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private TermService termService;

    @Log
    private Logger logger;


    @Scheduled(fixedDelay = 60000)// 60秒执行一次
    public void updateChongqi() {
        //获取当前期次
        Term term = termService.getOpenTerm();
        if (term == null) {
            //当前没有可售期次则不再执行
            return;
        }
        //获取当前期次预测结果
        Prediction query = new Prediction();
        query.setGame(Cons.Game.CQSSC);
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
        String res = qiaoqiaoyingService.getResult();
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
        prediction.setGame(Cons.Game.CQSSC);
        prediction.setTerm(DateUtil.DateToString(new Date(), "yyyyMMdd") + new DecimalFormat("000").format(issue));
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


    @Scheduled(fixedDelay = 120000)// 120秒执行一次
    public void updatePrize() {
        //获取当前期次
        Term term = termService.getOpenTerm();
        if (term == null) {
            //当前没有可售期次则不再执行
            return;
        }
        Term preTerm = new Term();
        preTerm.setGame(Cons.Game.CQSSC);
        preTerm.setCloseAt(term.getOpenAt());
        preTerm = termService.get(preTerm);
        if (preTerm == null || !StringUtils.isEmpty(preTerm.getWinNumber())) {
            return;
        }
        String result = qiaoqiaoyingService.getPrize(preTerm.getOpenAt());
        if (result == null) {
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
