package com.mcp.lottery.service;

import com.mcp.lottery.mapper.TermMapper;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.ArithUtil;
import com.mcp.lottery.util.DateUtil;
import com.mcp.lottery.util.cons.Cons;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TermServiceTest {

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;



    @Test
    public void chongqishishicai() throws Exception {
        String str = "2019-02-12";
        String str1 = "2019-05-01"; //不包含
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        try {
            startDay.setTime(format.parse(str));
            endDay.setTime(format.parse(str1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (startDay.before(endDay)) {
            String time = DateUtil.DateToString(startDay.getTime(), "yyyyMMdd 00:00:00");
            Date date = DateUtil.StringToDate(time, "yyyyMMdd 00:00:00");
            DecimalFormat df = new DecimalFormat("#");
            int index = 1;
            double start = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd001"));
            double end = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd059"));
            for (double i = start; i <= end; i++) {
                Term term = new Term();
                term.setGame(Cons.Game.CQSSC);
                term.setTermCode(df.format(i));
                term.setNextCode(df.format(ArithUtil.add(i, 1)));
                if (index == 1) {
                    term.setOpenAt(DateUtil.addMinute(date, -20));
                } else {
                    term.setOpenAt(date);
                }
                Date close = null;
                if (index == 10) {
                    close = DateUtil.addMinute(date, 260);
                } else {
                    close = DateUtil.addMinute(date, 20);
                }
                if (index == 59) {
                    term.setNextCode(DateUtil.DateToString(DateUtil.addDay(date, 1), "yyyyMMdd001"));
                }
                term.setCloseAt(close);
                termMapper.insertSelective(term);
                date = close;
                index++;
            }
            startDay.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void fenfencai() throws Exception {
        String str = "2019-02-20";
        String str1 = "2019-03-01"; //不包含
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        try {
            startDay.setTime(format.parse(str));
            endDay.setTime(format.parse(str1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (startDay.before(endDay)) {//201902201020
            String time = DateUtil.DateToString(startDay.getTime(), "yyyyMMdd 00:00:00");
            Date date = DateUtil.StringToDate(time, "yyyyMMdd 00:00:00");
            DecimalFormat df = new DecimalFormat("#");
            int index = 1;
            double start = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd0001"));
            double end = Double.valueOf(DateUtil.DateToString(date, "yyyyMMdd1440"));
            for (double i = start; i <= end; i++) {
                Term term = new Term();
                term.setGame(Cons.Game.TXFFC);
                term.setTermCode(df.format(i));
                term.setNextCode(df.format(ArithUtil.add(i, 1)));
                term.setOpenAt(date);
                Date close = DateUtil.addMinute(date, 1);
                if (index == 1440) {
                    term.setNextCode(DateUtil.DateToString(DateUtil.addDay(date, 1), "yyyyMMdd0001"));
                }
                term.setCloseAt(close);
                termMapper.insertSelective(term);
                date = close;
                index++;
            }
            startDay.add(Calendar.DAY_OF_MONTH, 1);
        }
    }


    @Test
    public void updatePrize() throws Exception {
        qiaoqiaoyingService.updatePrize(Cons.Game.CQSSC);
        qiaoqiaoyingService.updatePrize(Cons.Game.TXFFC);
    }




}