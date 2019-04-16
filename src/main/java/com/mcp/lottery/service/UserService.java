package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.UserMapper;
import com.mcp.lottery.mapper.UserRuleMapper;
import com.mcp.lottery.model.User;
import com.mcp.lottery.model.UserRule;
import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRuleMapper userRuleMapper;


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

    public PageInfo<User> getAll(Pager pager) {
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> userMapper.getAll());
        return pageInfo;
    }

    public List<User> getAll() {
        List<User> list = userMapper.getAll();
        /*list.forEach((user) -> {
            if (user.getParentId()!=0) {
                User parent = this.get(user.getParentId());
                user.setParent(parent);
            }
        });*/
        return list;
    }

    public User get(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            UserRule query = new UserRule();
            query.setUid(user.getId());
            user.setUserRuleList(userRuleMapper.select(query));
        }
        return user;
    }

    public boolean addBalance(Long id, Double balance) {
        if (userMapper.addBalance(id, balance) == 1) {
            return true;
        }
        return false;
    }

    public boolean openTouzhu(Long id) {
        if (userMapper.openTouzhu(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean closeTouzhu(Long id) {
        if (userMapper.closeTouzhu(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean updateInitMoney(Long id, Double initMoney) {
        if (userMapper.updateInitMoney(id, initMoney) == 1) {
            return true;
        }
        return false;
    }

    public boolean updateLimitLose(Long id, Double limitLose) {
        if (userMapper.updateLimitLose(id, limitLose) == 1) {
            return true;
        }
        return false;
    }

    public boolean updateLimitWin(Long id, Double limitWin) {
        if (userMapper.updateLimitWin(id, limitWin) == 1) {
            return true;
        }
        return false;
    }


    public boolean openDividing(Long id) {
        if (userMapper.openDividing(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean closeDividing(Long id) {
        if (userMapper.closeDividing(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean openTraceLose(Long id) {
        if (userMapper.openTraceLose(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean closeTraceLose(Long id) {
        if (userMapper.closeTraceLose(id) == 1) {
            return true;
        }
        return false;
    }


    public boolean updateLimitMaxWin(Long id, Double limitWin) {
        if (userMapper.updateLimitMaxWin(id, limitWin) == 1) {
            return true;
        }
        return false;
    }

    public boolean updateLimitMinWin(Long id, Double limitWin) {
        if (userMapper.updateLimitMinWin(id, limitWin) == 1) {
            return true;
        }
        return false;
    }


    public boolean openBottomWin(Long id) {
        if (userMapper.openBottomWin(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean closeBottomWin(Long id) {
        if (userMapper.closeBottomWin(id) == 1) {
            return true;
        }
        return false;
    }


    public boolean openIsTiming(Long id) {
        if (userMapper.openIsTiming(id) == 1) {
            return true;
        }
        return false;
    }

    public boolean closeIsTiming(Long id) {
        if (userMapper.closeIsTiming(id) == 1) {
            return true;
        }
        return false;
    }


}
