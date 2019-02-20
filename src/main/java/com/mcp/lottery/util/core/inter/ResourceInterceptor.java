package com.mcp.lottery.util.core.inter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


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
        httpServletRequest.setAttribute("curPath", prefix);
        httpServletRequest.setAttribute("curParam", clearPage(httpServletRequest));
    }

    public  String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }

    public String clearPage(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getQueryString()==null){
            return null;
        }
        List<String> list = new ArrayList();
        Enumeration<String> paramMap = httpServletRequest.getParameterNames();
        while (paramMap.hasMoreElements()) {
            String paramName = paramMap.nextElement();
            if (paramName.equals("page") || paramName.equals("page")) {
                continue;
            }
            String paramValue = httpServletRequest.getParameter(paramName);
            //形成键值对应的map
            if (!StringUtils.isEmpty(paramValue)) {
                list.add(paramName + "=" + paramValue);
            }
        }
        if(list.size()==0){
            return null;
        }
        return StringUtils.join(list,"&");
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
