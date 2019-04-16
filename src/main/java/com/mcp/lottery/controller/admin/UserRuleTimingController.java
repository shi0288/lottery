package com.mcp.lottery.controller.admin;


import com.mcp.lottery.model.UserRule;
import com.mcp.lottery.model.UserRuleTiming;
import com.mcp.lottery.service.UserRuleTimingService;
import com.mcp.lottery.util.*;
import com.mcp.lottery.util.list.UserRuleTimingList;
import com.mcp.validate.annotation.Check;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("lqmJHTqixle2eWaB/userRuleTiming")
public class UserRuleTimingController extends BaseController {

    @Autowired
    private UserRuleTimingService ruleTimingService;

    @Autowired
    private TemplateUtils templateUtils;

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @ResponseBody
    Result getList(
            @Check Long uid,
            @Check String game
    ) {
        return result.format(templateUtils.parse("userRule/userRuleTimingList", new HashMap() {{
            put("list", ruleTimingService.getList(uid, game));
        }}));
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    Result save(UserRuleTimingList userRuleTimingList, @Check(name = "游戏代码") String game, @Check(name = "用户ID") Long uid) {
        List<UserRuleTiming> list = userRuleTimingList.getUserRuleTimingList();
        if (list == null || list.size() == 0) {
            return result.format();
        }
        for (int i = 0; i < list.size(); i++) {
            UserRuleTiming userRuleTiming = list.get(i);
            if (userRuleTiming == null) {
                return result.format(ResultCode.ERROR, "信息不完整");
            }
            if (StringUtils.isEmpty(userRuleTiming.getStartTime())) {
                return result.format(ResultCode.ERROR, "开始时间不能为空");
            }
            if (StringUtils.isEmpty(userRuleTiming.getEndTime())) {
                return result.format(ResultCode.ERROR, "结束时间不能为空");
            }
            if (userRuleTiming.getUpPoint() == null) {
                return result.format(ResultCode.ERROR, "上穿点数不能为空");
            }
            if (userRuleTiming.getDownPoint() == null) {
                return result.format(ResultCode.ERROR, "下穿点数不能为空");
            }
            if (userRuleTiming.getIsBullAxisMove() == null) {
                userRuleTiming.setIsBullAxisMove(0);
            }
            if (userRuleTiming.getIsTradeBeforeAxisMove() == null) {
                userRuleTiming.setIsTradeBeforeAxisMove(0);
            }
        }
        list.forEach(e -> {
            e.setGame(game);
            e.setUid(uid);
            e.setOriginUpPoint(e.getUpPoint());
            e.setOriginDownPoint(e.getDownPoint());
            ruleTimingService.saveOrUpdate(e);
        });
        ruleTimingService.updateBottomwin(uid, game);
        return result.format();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    Result delete(@Check Long id) {
        ruleTimingService.delete(id);
        return result.format();
    }


}
