package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.ManageMapper;
import com.mcp.lottery.mapper.UserMapper;
import com.mcp.lottery.model.Manage;
import com.mcp.lottery.model.User;
import com.mcp.lottery.model.UserOrderLog;
import com.mcp.lottery.util.Pager;
import com.mcp.lottery.util.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ManageService {

    @Autowired
    private ManageMapper manageMapper;

    @Autowired
    private UserMapper userMapper;

    public Manage checkUser(String username, String password) {
        Manage manage = new Manage();
        manage.setUsername(username);
        manage.setPassword(password);
        try {
            manage = manageMapper.selectOne(manage);
        } catch (Exception e) {
            throw new ApiException("用户名或密码错误！");
        }
        if (manage == null || manage.getStatus()==0) {
            throw new ApiException("用户名或密码错误！");
        }
        return manage;

    }

    public List<Manage> getAll(){
        List<Manage> list = manageMapper.selectAll();
        list.forEach(manage -> {
            List<String> uids = Arrays.asList(manage.getUids().split(","));
            List<User> userList = userMapper.getByIds(uids);
            manage.setUsers(userList);
        });
        return list;
    }

    public void updateUids(Integer id, String uids) {
        manageMapper.updateUids(id, uids);
    }

    public Manage getById(Long id) {
        return manageMapper.selectByPrimaryKey(id);
    }
}
