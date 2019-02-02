package com.mcp.lottery.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mcp.lottery.mapper.QiaoqiaoyingMapper;
import com.mcp.lottery.model.Qiaoqiaoying;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.DateUtil;
import com.mcp.lottery.util.HttpClientWrapper;
import com.mcp.lottery.util.HttpResult;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QiaoqiaoyingService {

    @Autowired
    private QiaoqiaoyingMapper qiaoqiaoyingMapper;


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

    public String getResult() {
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
            String url = qiaoqiaoying.getDataUrl().replace("$data$", DateUtil.DateToString(new Date(), "yyyyMMdd"));
            HttpResult httpResult = HttpClientWrapper.sendGet(url, headers, params);
            return httpResult.getResult();
        }
        return null;
    }



    public String getPrize(Date date) {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Qiaoqiaoying query = new Qiaoqiaoying();
        List<Qiaoqiaoying> list = qiaoqiaoyingMapper.select(query);
        if (list.size() == 1) {
            Qiaoqiaoying qiaoqiaoying = list.get(0);
            if (StringUtils.isEmpty(qiaoqiaoying.getPrizeUrl())) {
                return null;
            }
            Map<String, String> params = new HashMap<>();
            String url = qiaoqiaoying.getPrizeUrl().replace("$data$", DateUtil.DateToString(date, "yyyyMMdd"));
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


}
