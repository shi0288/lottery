package com.mcp.lottery.service;


import com.mcp.lottery.mapper.UserMapper;
import com.mcp.lottery.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public boolean exist(User user) {
        if (userMapper.selectCount(user) > 0) {
            return true;
        }
        return false;
    }


    public boolean saveOrUpdate(User user) {
        int i = 0;
        if (user.getId() == null) {
            i = userMapper.insertSelective(user);
        } else {
            i = userMapper.updateByPrimaryKeySelective(user);
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }

    public User get(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    public boolean addBalance(Long id,Double balance){
        if(userMapper.addBalance(id,balance)==1){
            return true;
        }
        return false;
    }

    public boolean openTouzhu(Long uid){
        if(userMapper.openTouzhu(uid)==1){
            return true;
        }
        return false;
    }
    public boolean closeTouzhu(Long uid){
        if(userMapper.closeTouzhu(uid)==1){
            return true;
        }
        return false;
    }

    public boolean updateInitMoney(Long uid,Double initMoney){
        if(userMapper.updateInitMoney(uid,initMoney)==1){
            return true;
        }
        return false;
    }



}
