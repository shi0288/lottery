package com.mcp.lottery.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    /**
     * 得到请求的根目录
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }

    /**
     * 得到结构目录
     *
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path;
    }


}
