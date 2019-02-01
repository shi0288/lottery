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
        return userMapper.selectAll();
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


}
