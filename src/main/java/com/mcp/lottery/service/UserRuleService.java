package com.mcp.lottery.service;

import com.mcp.lottery.mapper.UserRuleMapper;
import com.mcp.lottery.model.UserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRuleService {

    @Autowired
    private UserRuleMapper userRuleMapper;


    public int saveOrUpdate(UserRule userRule){
        if(userRule.getId()==null){
            return userRuleMapper.insertSelective(userRule);
        }
        return userRuleMapper.updateByPrimaryKeySelective(userRule);
    }


    public void saveGame(Long uid, String... games) {
        if (games != null && games.length > 0) {
            for (int i = 0; i < games.length; i++) {
                UserRule userRule = new UserRule();
                userRule.setGame(games[i]);
                userRule.setUid(uid);
                //如果已经存在
                if (isExist(userRule)) {
                    continue;
                }
                userRule.setInitMoney(0D);
                userRule.setIsOpen(0);
                userRuleMapper.insertSelective(userRule);
            }
        }
    }

    public boolean isExist(UserRule userRule) {
        if (userRuleMapper.selectCount(userRule) > 0) {
            return true;
        }
        return false;
    }

    public UserRule get(Long id){
        return userRuleMapper.selectByPrimaryKey(id);
    }


}
