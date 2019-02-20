package com.mcp.lottery.task;


import com.mcp.lottery.service.QiaoqiaoyingService;
import com.mcp.lottery.util.cons.Cons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QiaoqiaoyingTask {

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;

    @Scheduled(fixedDelay = 60000)// 60秒执行一次
    public void updateChongqi() {
        qiaoqiaoyingService.updatePrediction(Cons.Game.CQSSC);
    }

    @Scheduled(fixedDelay = 120000)// 120秒执行一次
    public void updatePrizeChongqi() {
        qiaoqiaoyingService.updatePrize(Cons.Game.CQSSC);
    }


    @Scheduled(fixedDelay = 5000)
    public void updateFenfencai() {
        qiaoqiaoyingService.updatePrediction(Cons.Game.TXFFC);
    }

    @Scheduled(fixedDelay = 30) // 30秒执行一次
    public void updatePrizeFenfencai() {
        qiaoqiaoyingService.updatePrize(Cons.Game.TXFFC);
    }






}
