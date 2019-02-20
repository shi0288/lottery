package com.mcp.lottery.util;


import com.mcp.lottery.util.annotation.Log;
import com.mcp.lottery.util.exception.ApiException;
import com.mcp.validate.BindResult;
import com.mcp.validate.exception.ValidateException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.mcp.lottery.util.ResultCode.ERROR;


public class BaseController {

    @Log
    protected Logger logger;

    @Autowired
    protected Result result;

    @Autowired
    protected HttpSession httpSession;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected HttpServletResponse httpServletResponse;



    @ExceptionHandler({Exception.class})
    public Object handleException(HttpServletRequest req, Exception ex) {
        Throwable e = ExceptionUtils.getRootCause(ex);
        if (null != e) {
            ex = (Exception) e;
        }
        if (ex.getClass().isAssignableFrom(MissingServletRequestParameterException.class)) {
            MissingServletRequestParameterException msrpException = (MissingServletRequestParameterException) ex;
            this.logger.warn("请求:" + req.getRequestURI() + " 缺少参数：" + msrpException.getParameterName());
            result.format(ERROR, "请求参数错误");
        } else if (ex.getClass().isAssignableFrom(ValidateException.class)) {
            ValidateException validateException = (ValidateException) ex;
            BindResult bindResult = validateException.getBindResult();
            result.format(ERROR, bindResult.getMessage());
        } else if (ex.getClass().isAssignableFrom(NumberFormatException.class)) {
            this.logger.warn("请求:" + req.getRequestURI() + " 参数类型错误");
            result.format(ERROR, "传入的数据类型有误");
        } else if (ex.getClass().isAssignableFrom(ApiException.class)) {
            result.format(ERROR, ex.getMessage());
        }else {
            this.logger.error(ExceptionUtils.getStackTrace(ex));
            result.format(ERROR, "未知错误");
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    protected Map getParamMap() {
        Map<String, Object> map = new HashMap();
        Enumeration<String> paramMap = httpServletRequest.getParameterNames();
        while (paramMap.hasMoreElements()) {
            String paramName = paramMap.nextElement();
            String paramValue = httpServletRequest.getParameter(paramName);
            //形成键值对应的map
            if (!StringUtils.isEmpty(paramValue)) {
                map.put(paramName, paramValue);
            }
        }
        map.remove("page");
        map.remove("limit");
        return map;
    }

    protected Map getParamMap(ModelMap modelMap) {
        Map param = this.getParamMap();
        modelMap.putAll(param);
        return param;
    }

}
