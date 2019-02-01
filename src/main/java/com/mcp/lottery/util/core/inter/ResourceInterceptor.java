package com.mcp.lottery.util.core.inter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String base = getBasePath(httpServletRequest);
        String jsPath = base + "/script";
        String cssPath = base + "/style";
        String imgPath = base + "/images";
        httpServletRequest.setAttribute("base", base);
        httpServletRequest.setAttribute("jsPath", jsPath);
        httpServletRequest.setAttribute("cssPath", cssPath);
        httpServletRequest.setAttribute("imgPath", imgPath);
        String prefix = httpServletRequest.getRequestURI();
        httpServletRequest.setAttribute("getRequestUrl", prefix);
    }

    public  String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
