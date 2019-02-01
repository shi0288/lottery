package com.mcp.lottery.util;


import com.alibaba.fastjson.JSONArray;
import com.mcp.lottery.model.Plat;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Plugin {


    public abstract void getAuthor(Plat plat);

    public abstract Double getBalance(Plat plat);

    public abstract LotteryResult send(Plat plat,JSONArray list);





}
