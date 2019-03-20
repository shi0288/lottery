package com.mcp.lottery.service;


import com.mcp.lottery.mapper.UserRuleTimingMapper;
import com.mcp.lottery.model.UserRuleTiming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRuleTimingService {

    @Autowired
    private UserRuleTimingMapper userRuleTimingMapper;


    public List<UserRuleTiming> getList(Long uid, String game) {
        return userRuleTimingMapper.getList(uid, game);
    }

    public void saveOrUpdate(UserRuleTiming userRuleTiming){
        if(userRuleTiming.getId()==null){
            userRuleTimingMapper.insertSelective(userRuleTiming);
        }else{
            userRuleTimingMapper.updateByPrimaryKeySelective(userRuleTiming);
        }
    }

    public void delete(Long id){
        userRuleTimingMapper.deleteByPrimaryKey(id);
    }

}
