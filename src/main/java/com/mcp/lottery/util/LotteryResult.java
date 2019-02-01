package com.mcp.lottery.util;

import com.alibaba.fastjson.JSONArray;

public class LotteryResult {

    private boolean success;

    private String response;

    private JSONArray data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
