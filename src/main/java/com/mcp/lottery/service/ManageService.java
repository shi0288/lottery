package com.mcp.lottery.service;


import com.mcp.lottery.mapper.ManageMapper;
import com.mcp.lottery.model.Manage;
import com.mcp.lottery.util.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageService {

    @Autowired
    private ManageMapper manageMapper;

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

}
