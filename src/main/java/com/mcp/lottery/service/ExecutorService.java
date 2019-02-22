package com.mcp.lottery.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.mapper.UserOrderLogMapper;
import com.mcp.lottery.model.Plat;
import com.mcp.lottery.model.UserOrderLog;
import com.mcp.lottery.util.LotteryResult;
import com.mcp.lottery.util.Plugin;
import com.mcp.lottery.util.SpringIocUtil;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.exception.ApiException;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class ExecutorService {

    @Autowired
    private PlatService platService;

    @Autowired
    private UserOrderLogMapper userOrderLogMapper;

    @Log
    private Logger logger;

    @RabbitListener(queues = "gen_server_test_queue", containerFactory = "taskContainerFactory")
    @RabbitHandler
    public void process(String data) {
        String[] chars = data.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            stringBuffer.append(String.valueOf((char) Integer.parseInt(chars[i])));
        }
        logger.info("接收：" + stringBuffer.toString());
        JSONObject params = JSONObject.parseObject(stringBuffer.toString());
        Long pid = Long.valueOf(params.get("pid").toString());
        String game = null;
        if (params.containsKey("game")) {
            game = params.get("game").toString();
        }else{
            logger.info("没有游戏参数");
            return;
        }
        String term = null;
        if (params.containsKey("term")) {
            term = params.get("term").toString();
        }else{
            logger.info("没有期次参数");
            return;
        }
        Plat plat = platService.get(pid);
        JSONArray list = params.getJSONArray("list");
        Plugin plugin = (Plugin) SpringIocUtil.getBean(plat.getPlatCategory().getExecutor());
        LotteryResult lotteryResult;
        try {
            lotteryResult = plugin.send(plat, game,term, list);
            logger.info("返回：" + lotteryResult.getResponse());
        } catch (ApiException e) {
            logger.info("更新账户信息后重试");
            //更新账户信息重新试下
            plugin.getAuthor(plat);
            if (platService.update(plat)) {
                try {
                    lotteryResult = plugin.send(plat, game,term, list);
                    logger.info("返回：" + lotteryResult.getResponse());
                } catch (Exception ex) {
                    //真的失败了。。
                    ex.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    public void updateLottery(LotteryResult lotteryResult) {
        if (lotteryResult == null) {
            return;
        }
        JSONArray data = lotteryResult.getData();
        logger.info(lotteryResult.getResponse());
        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = data.getJSONObject(i);
            UserOrderLog userOrderLog = new UserOrderLog();
            userOrderLog.setId(obj.getLong("log_id"));
            userOrderLog.setRate(obj.getDouble("odds"));
            userOrderLogMapper.updateByPrimaryKeySelective(userOrderLog);
            //todo 增加出票状态和结果报文
//            if (lotteryResult.isSuccess()) {
//            } else {
//            }
        }

    }


}
