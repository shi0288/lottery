package com.mcp.lottery.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.OnlineMapper;
import com.mcp.lottery.model.Online;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.HttpClientWrapper;
import com.mcp.lottery.util.HttpResult;
import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.cons.Cons;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


@Service
public class OnlineService {

    @Autowired
    private TermService termService;

    @Autowired
    private OnlineMapper onlineMapper;

    @Log
    private Logger logger;


    public void updatePrizeNumber() {
        PageHelper.startPage(1, 30).setOrderBy("id desc");
        List<Online> list = onlineMapper.selectAll();
        for (int i = 0; i < list.size(); i++) {
            Online online = list.get(i);
            if (StringUtils.isEmpty(online.getPrize())) {
                continue;
            }
            Term term = new Term();
            term.setGame(Cons.Game.TXFFC);
            term.setTermCode(online.getTerm());
            term = termService.get(term);
            if (term == null || !StringUtils.isEmpty(term.getWinNumber())) {
                continue;
            }
            term.setWinNumber(online.getPrize());
            termService.update(term);
        }
    }


    public String getPrize(String onlineNum) {
        String[] bb = onlineNum.split("");
        int result = 0;
        for (int i = 0; i < bb.length; i++) {
            result += Integer.parseInt(bb[i]);
        }
        String temp = String.valueOf(result);
        return temp.substring(temp.length() - 1) + onlineNum.substring(onlineNum.length() - 4);
    }

    public void insertFFCPrizeBtE3sh() {
        Map<String, String> headers = new HashMap();
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        headers.put("referer", "https://www.e3sh.com/article-87-1.html");
        headers.put("cookie", "CqqS_2132_saltkey=N1QHhwOh;CqqS_2132_sid=gTt0bt;CqqS_2132_lastact=1552386719%09plugin.php%09;Hm_lpvt_0860ef575b8bddda9b51699dfd59291e=1552386546;Hm_lvt_0860ef575b8bddda9b51699dfd59291e=1552384879;UM_distinctid=169715962ac216-05a6d18ff84eda-36607102-1fa400-169715962ad6ad;CNZZDATA1273613680=1848860687-1552381690-%7C1552381690");
        Map<String, String> params = new HashMap();
        HttpResult httpResult = HttpClientWrapper.sendGet("https://www.e3sh.com/api.html", headers, params);
        JSONArray array = JSON.parseArray(httpResult.getResult());
        for (int i = 0; i < array.size(); i++) {
            JSONObject temp = array.getJSONObject(i);
            Term term = new Term();
            term.setGame(Cons.Game.TXFFC);
            term.setCloseAt(temp.getDate("onlinetime"));
            term = termService.get(term);
            if (term == null || !StringUtils.isEmpty(term.getWinNumber())) {
                continue;
            }
            String onlineNum = temp.getString("onlinenumber");
            String winNum = this.getPrize(onlineNum);
            winNum = StringUtils.join(winNum.split(""), ",");
            term.setWinNumber(winNum);
            if (termService.update(term)) {
                logger.info("更新中奖号:" + term.getTermCode() + "---" + term.getWinNumber());
            }
        }
    }


    public void insertFFCPrizeBtTxzx() {
        Map<String, String> headers = new HashMap();
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        headers.put("referer", "http://www.txzxtj.com/");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> params = new HashMap();
        params.put("jqueryGridPage", "1");
        params.put("jqueryGridRows", "15");
        params.put("code", "tx");
        HttpResult httpResult = HttpClientWrapper.sendGet("http://www.txzxtj.com/ct-data/onlineReportList", headers, params);
        JSONObject result = JSON.parseObject(httpResult.getResult());
        JSONArray array = result.getJSONArray("data");
        for (int i = 0; i < array.size(); i++) {
            JSONObject temp = array.getJSONObject(i);
            Term term = new Term();
            term.setGame(Cons.Game.TXFFC);
            term.setCloseAt(temp.getDate("openTime"));
            term = termService.get(term);
            if (term == null || !StringUtils.isEmpty(term.getWinNumber())) {
                continue;
            }
            String onlineNum = temp.getString("onlineCount");
            String winNum = this.getPrize(onlineNum);
            winNum = StringUtils.join(winNum.split(""), ",");
            term.setWinNumber(winNum);
            if (termService.update(term)) {
                logger.info("更新中奖号:" + term.getTermCode() + "---" + term.getWinNumber());
            }
        }
    }


}
