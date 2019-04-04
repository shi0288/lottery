package com.mcp.lottery.task;


import com.mcp.lottery.service.OnlineService;
import com.mcp.lottery.service.QiaoqiaoyingService;
import com.mcp.lottery.util.DateUtil;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.cons.Cons;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class QiaoqiaoyingTask {

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;

    @Autowired
    private OnlineService onlineService;

    @Log
    private Logger logger;




    /**
     * 获取时时彩预测
     */
    @Scheduled(fixedDelay = 180000)// 180秒执行一次
    public void updateChongqi() {
        qiaoqiaoyingService.updatePrediction(Cons.Game.CQSSC);
    }

    /**
     * 获取时时彩开奖结果
     */
    @Scheduled(fixedDelay = 600000)// 600秒执行一次
    public void updatePrizeChongqi() {
        try {
            qiaoqiaoyingService.updatePrize(Cons.Game.CQSSC,new Date());
        }catch (Exception e){}
    }

    /**
     * 获取分分彩预测
     */
    @Scheduled(fixedDelay = 10000)
    public void updateFenfencai() {
        qiaoqiaoyingService.updatePrediction(Cons.Game.TXFFC);
    }


    /**
     * 获取分分彩开奖结果
     */
    @Scheduled(fixedDelay = 5000) // 5秒执行一次
    public void updatePrizeFenfencai() {
        try {
            onlineService.updatePrizeNumber();
        }catch (Exception e){}
    }


    /**
     * 获取时时彩前一天开奖结果
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void updatePrizeYesterday() {
        try {
            logger.info("前一天开奖处理。。。。");
//            qiaoqiaoyingService.updatePrize(Cons.Game.TXFFC, DateUtil.addDay(new Date(),-1));
            qiaoqiaoyingService.updatePrize(Cons.Game.CQSSC,DateUtil.addDay(new Date(),-1));
        }catch (Exception e){}
    }







}
