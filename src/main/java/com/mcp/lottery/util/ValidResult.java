package com.mcp.lottery.util;

public class ValidResult {

    private String cookies;

    private String code;

    private String picId;

    @Override
    public String toString() {
        return "ValidResult{" +
                "cookies='" + cookies + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}
