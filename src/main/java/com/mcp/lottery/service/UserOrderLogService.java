package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.UserOrderLogMapper;
import com.mcp.lottery.model.UserOrderLog;
import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserOrderLogService {

    @Autowired
    private UserOrderLogMapper userOrderLogMapper;

    public PageInfo<Map> getAllByDays(Pager pager,Map param){
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> userOrderLogMapper.getAllByDays(param));
        return pageInfo;
    }

    public PageInfo<UserOrderLog> getAll(Pager pager,Map param){
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> userOrderLogMapper.getAll(param));
        return pageInfo;
    }

}
