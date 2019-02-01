//package com.mcp.lottery.service;
//
//import com.mcp.lottery.mapper.TermMapper;
//import com.mcp.lottery.model.Term;
//import com.mcp.lottery.util.ArithUtil;
//import com.mcp.lottery.util.DateUtil;
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
//
//    @Test
//    public void test1() throws Exception {
//        String str="2019-02-11";
//        String str1="2019-03-01";
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        Calendar startDay = Calendar.getInstance();
//        Calendar endDay = Calendar.getInstance();
//        try {
//            startDay.setTime(format.parse(str));
//            endDay.setTime(format.parse(str1));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        while(startDay.before(endDay))
//        {
//            String time = DateUtil.DateToString(startDay.getTime(),"yyyyMMdd 00:00:00");
//            Date date = DateUtil.StringToDate(time,"yyyyMMdd 00:00:00");
//            DecimalFormat df = new DecimalFormat("#");
//            int index=1;
//            double start=Double.valueOf(DateUtil.DateToString(date,"yyyyMMdd001"));
//            double end=Double.valueOf(DateUtil.DateToString(date,"yyyyMMdd120"));
//            for (double i = start; i <= end; i++) {
//                Term term = new Term();
//                term.setGame("chongqishishicai");
//                term.setTermCode(df.format(i));
//                term.setNextCode(df.format(ArithUtil.add(i, 1)));
//                term.setOpenAt(date);
//                Date close;
//                if(index<=23){
//                    close=DateUtil.addMinute(date, 5);
//                }else if(index==24){
//                    close=DateUtil.addMinute(date, 485);
//                }else if(index>24 && index<=96){
//                    close=DateUtil.addMinute(date, 10);
//                }else{
//                    close=DateUtil.addMinute(date, 5);
//                }
//                if(index==120){
//                    term.setNextCode(DateUtil.DateToString(DateUtil.addDay(date,1),"yyyyMMdd001"));
//                }
//                term.setCloseAt(close);
//                termMapper.insertSelective(term);
//                date = close;
//                index++;
//            }
//            startDay.add(Calendar.DAY_OF_MONTH,1);
//        }
//
//
//
//    }
//
//
//}