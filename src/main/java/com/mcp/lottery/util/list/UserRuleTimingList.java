package com.mcp.lottery.util.list;

import com.mcp.lottery.model.UserRuleTiming;

import java.io.Serializable;
import java.util.List;

public class UserRuleTimingList  implements Serializable {

    public UserRuleTimingList() {
    }

    private List<UserRuleTiming> userRuleTimingList;

    public List<UserRuleTiming> getUserRuleTimingList() {
        return userRuleTimingList;
    }

    public void setUserRuleTimingList(List<UserRuleTiming> userRuleTimingList) {
        this.userRuleTimingList = userRuleTimingList;
    }
}
