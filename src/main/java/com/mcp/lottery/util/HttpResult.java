package com.mcp.lottery.util;


import java.util.ArrayList;
import java.util.List;

public class HttpResult {

    private String result;

    private List<String> cookies=new ArrayList<>();


    @Override
    public String toString() {
        return "HttpResult{" +
                "result='" + result + '\'' +
                ", cookies=" + cookies +
                '}';
    }

    public void addCookies(String cookie){
        cookies.add(cookie);
    }

    public List<String> getCookies(){
        return cookies;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
