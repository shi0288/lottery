package com.mcp.lottery.util.core;

import com.mcp.lottery.model.Manage;
import com.mcp.lottery.util.HttpUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="adminFilter",urlPatterns="/lqmJHTqixle2eWaB/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Manage manage = (Manage) request.getSession().getAttribute("manage");
        if(manage==null){
            response.sendRedirect(HttpUtils.getBasePath(request) + "/JiswyAaKgoJmqutA/login");
        }else{
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
