package com.mcp.lottery.util;


import java.util.ArrayList;
import java.util.List;

public class HttpResult {

    private String result;

    private int code;

    private List<String> cookies=new ArrayList<>();


    @Override
    public String toString() {
        return "HttpResult{" +
                "result='" + result + '\'' +
                ", code=" + code +
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
