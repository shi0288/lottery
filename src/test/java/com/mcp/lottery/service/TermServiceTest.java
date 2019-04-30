//package com.mcp.lottery.service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mcp.lottery.mapper.TermMapper;
//import com.mcp.lottery.model.Plat;
//import com.mcp.lottery.model.Term;
//import com.mcp.lottery.util.*;
//import com.mcp.lottery.util.cons.Cons;
//import com.mcp.lottery.util.exception.ApiException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TermServiceTest {
//
//    @Autowired
//    private TermMapper termMapper;
//
//    @Autowired
//    private QiaoqiaoyingService qiaoqiaoyingService;
//
//    @Autowired
//    private PlatService platService;
//
//    @Autowired
//    private OnlineService onlineService;
//
//
//
//    @Test
//    public void chongqishishicai() throws Exception {
//        String str = "2019-02-21";
//        String str1 = "2019-05-01"; //不包含
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar startDay = Calendar.getInstance();
//        Calendar endDay = Calendar.getInstance();
//        try {
//            startDay.setTime(format.parse(str));
//            endDay.setTime(format.parse(str1));
//        } catch (ParseException e) {
////            e.printStackTrace();
//        }
//        while (startDay.before(endDay)) {
//            String time = DateUtil.DateToString(startDay.getTime(), "yyyyMMdd 00:00:00");
//            Date date = DateUtil.StringToDate(time, "yyyyMMdd 00:00:00");
//            DecimalFormat df = new DecimalFormat("#");
//            int index = 1;
//            double start = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd001"));
//            double end = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd059"));
//            for (double i = start; i <= end; i++) {
//                Term term = new Term();
//                term.setGame(Cons.Game.CQSSC);
//                term.setTermCode(df.format(i));
//                term.setNextCode(df.format(ArithUtil.add(i, 1)));
//                if (index == 1) {
//                    term.setOpenAt(DateUtil.addMinute(date, -10));
//                } else {
//                    term.setOpenAt(date);
//                }
//                Date close = null;
//                if (index == 10) {
//                    close = DateUtil.addMinute(date, 260);
//                } else if(index==1){
//                    close = DateUtil.addMinute(date, 30);
//                }else {
//                    close = DateUtil.addMinute(date, 20);
//                }
//                if (index == 59) {
//                    term.setNextCode(DateUtil.DateToString(DateUtil.addDay(date, 1), "yyyyMMdd001"));
//                }
//                term.setCloseAt(close);
//                termMapper.insertSelective(term);
//                date = close;
//                index++;
//            }
//            startDay.add(Calendar.DAY_OF_MONTH, 1);
//        }
//    }
//
//    @Test
//    public void fenfencai() throws Exception {
//        String str = "2019-05-01";
//        String str1 = "2019-07-01"; //不包含
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar startDay = Calendar.getInstance();
//        Calendar endDay = Calendar.getInstance();
//        try {
//            startDay.setTime(format.parse(str));
//            endDay.setTime(format.parse(str1));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        while (startDay.before(endDay)) {//201902201020
//            String time = DateUtil.DateToString(startDay.getTime(), "yyyyMMdd 00:00:00");
//            Date date = DateUtil.StringToDate(time, "yyyyMMdd 00:00:00");
//            DecimalFormat df = new DecimalFormat("#");
//            int index = 1;
//            double start = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd0001"));
//            double end = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd1440"));
//            for (double i = start; i <= end; i++) {
//                Term term = new Term();
//                term.setGame(Cons.Game.TXFFC);
//                term.setTermCode(df.format(i));
//                term.setNextCode(df.format(ArithUtil.add(i, 1)));
//                term.setOpenAt(date);
//                Date close = DateUtil.addMinute(date, 1);
//                if (index == 1440) {
//                    term.setNextCode(DateUtil.DateToString(DateUtil.addDay(date, 1), "yyyyMMdd0001"));
//                }
//                term.setCloseAt(close);
//                termMapper.insertSelective(term);
//                date = close;
//                index++;
//            }
//            startDay.add(Calendar.DAY_OF_MONTH, 1);
//        }
//    }
//
//
//    @Test
//    public void updatePrize() throws Exception {
//       // qiaoqiaoyingService.updatePrize(Cons.Game.CQSSC);
////        qiaoqiaoyingService.updatePrize(Cons.Game.TXFFC,new Date());
////        onlineService.updatePrizeNumber();
//
//    }
//
//    @Test
//    public void testJinzhi(){
//        String str="{\"timestamp\":1550750017762120,\"term\":\"20190221048\",\"pid\":2,\"list\":[{\"value\":111,\"money\":5,\"log_id\":2248}],\"game\":\"chongqingshishicai\"}";
//        JSONObject params = JSONObject.parseObject(str);
//        Long pid = Long.valueOf(params.get("pid").toString());
//        String game = null;
//        if (params.containsKey("game")) {
//            game = params.get("game").toString();
//        }else{
//            return;
//        }
//        String term = null;
//        if (params.containsKey("term")) {
//            term = params.get("term").toString();
//        }else{
//            return;
//        }
//        Plat plat = platService.get(pid);
//
//        JSONArray list = params.getJSONArray("list");
//        Plugin plugin = (Plugin) SpringIocUtil.getBean(plat.getPlatCategory().getExecutor());
//        plugin.getAuthor(plat);
//
////        LotteryResult lotteryResult;
////        try {
////            lotteryResult = plugin.send(plat, game,term, list);
////            System.out.println(lotteryResult.getResponse());
//////            updateLottery(lotteryResult);
////        } catch (ApiException e) {
////            System.out.println("更新用户信息");
////            //更新账户信息重新试下
////            plugin.getAuthor(plat);
////            if (platService.update(plat)) {
////                try {
////                    lotteryResult = plugin.send(plat, game,term, list);
////                    System.out.println(lotteryResult.getResponse());
//////                    updateLottery(lotteryResult);
////                } catch (Exception ex) {
////                    //真的失败了。。
////                    ex.printStackTrace();
////                }
////            } else {
////                e.printStackTrace();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//
////    @Test
////    public void updateHistory(){
////
//////         qiaoqiaoyingService.updatePrize(Cons.Game.TXFFC, new Date());
////
////        qiaoqiaoyingService.historyPrediction(Cons.Game.TXFFC);
//////        System.out.println(qiaoqiaoyingService.getResult(Cons.Game.CQSSC));
////    }
//
//    @Test
//    public void testBalance(){
//
//        Plat plat = platService.get(11L);
//        Plugin plugin = (Plugin) SpringIocUtil.getBean(plat.getPlatCategory().getExecutor());
//        System.out.println(plugin.getBalance(plat));
//
//
//
//    }
//
//
//
//
//}