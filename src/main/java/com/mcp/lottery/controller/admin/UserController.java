package com.mcp.lottery.controller.admin;


import com.mcp.lottery.model.User;
import com.mcp.lottery.model.UserRule;
import com.mcp.lottery.service.UserOrderLogService;
import com.mcp.lottery.service.UserRuleService;
import com.mcp.lottery.service.UserService;
import com.mcp.lottery.util.*;
import com.mcp.validate.annotation.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.mcp.lottery.util.ResultCode.ERROR;

@Controller
@RequestMapping("lqmJHTqixle2eWaB/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderLogService userOrderLogService;

    @Autowired
    private UserRuleService userRuleService;


    @RequestMapping("list")
    void list(ModelMap map, Pager pager) {
        map.put("page", userService.getAll(pager));
    }


    @RequestMapping("add")
    void add() {
    }

    @RequestMapping("edit")
    void edit(
            ModelMap map,
            @Check Long id
    ) {
        map.put("e", userService.get(id));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    Result save(
            @Check String username,
            @Check(defaultValue = "123456") String password,
            @Check(number = true) Double balance,
            @Check String realname,
            String[] games
    ) {
        User user = new User();
        user.setUsername(username);
        if (userService.exist(user)) {
            return result.format(ERROR, "用户名已经存在");
        }
        user.setPassword(MD5Encoder.encode(password));
        user.setBalance(balance);
        user.setRealname(realname);
        if (userService.saveOrUpdate(user)) {
            try {
                userRuleService.saveGame(user.getId(), games);
            } catch (Exception e) {
            }
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    Result update(
            @Check Long id,
            @Check String realname,
            String[] games
    ) {
        User user=new User();
        user.setId(id);
        user.setRealname(realname);
        userService.saveOrUpdate(user);
        userRuleService.saveGame(id, games);
        return result.format();
    }


    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ResponseBody
    Result recharge(
            @Check Long id,
            @Check(number = true, min = "0") Double balance
    ) {
        if (userService.addBalance(id, balance)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "frozen", method = RequestMethod.POST)
    @ResponseBody
    Result frozen(
            @Check Long id
    ) {
        User user = new User();
        user.setId(id);
        user.setStatus(0);
        if (userService.saveOrUpdate(user)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "thaw", method = RequestMethod.POST)
    @ResponseBody
    Result thaw(
            @Check Long id
    ) {
        User user = new User();
        user.setId(id);
        user.setStatus(1);
        if (userService.saveOrUpdate(user)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "mul", method = RequestMethod.POST)
    @ResponseBody
    Result mul(
            @Check Long id,
            @Check(number = true, min = "0") Double balance
    ) {
        if (userService.addBalance(id, -balance)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping("dayList/{uid}")
    String dayList(ModelMap map, Pager pager, @PathVariable(value = "uid") Long uid) {
        Map param = this.getParamMap(map);
        param.put("uid", uid);
        map.put("page", userOrderLogService.getAllByDays(pager, param));
        map.put("uid", uid);
        return "lqmJHTqixle2eWaB/user/dayList";
    }


    @RequestMapping("dayList/{uid}/{day}")
    String logList(ModelMap map, Pager pager, @PathVariable(value = "uid") Long uid, @PathVariable(value = "day") String day) {
        Map param = this.getParamMap(map);
        param.put("uid", uid);
        param.put("day", day);
        map.put("page", userOrderLogService.getAll(pager, param));
        return "lqmJHTqixle2eWaB/user/logList";
    }


    @RequestMapping(value = "closeTouzhu", method = RequestMethod.POST)
    @ResponseBody
    Result closeTouzhu(
            @Check Long id
    ) {
        if (userService.closeTouzhu(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "openTouzhu", method = RequestMethod.POST)
    @ResponseBody
    Result openTouzhu(
            @Check Long id
    ) {
        if (userService.openTouzhu(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "updateInitMoney", method = RequestMethod.POST)
    @ResponseBody
    Result updateInitMoney(
            @Check Long id,
            @Check(number = true, min = "1") Double initMoney
    ) {
        if (userService.updateInitMoney(id, initMoney)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


}
