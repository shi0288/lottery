package com.mcp.lottery.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.mcp.lottery.model.Manage;
import com.mcp.lottery.model.User;
import com.mcp.lottery.model.UserRule;
import com.mcp.lottery.service.*;
import com.mcp.lottery.util.*;
import com.mcp.validate.annotation.Check;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @Autowired
    private PlatService platService;

    @Autowired
    private ManageService manageService;


    @RequestMapping("list")
    void list(ModelMap map, Pager pager) {
        Manage manage = (Manage) this.httpSession.getAttribute("manage");
        manage = manageService.getById(manage.getId());
        map.put("page", userService.getAll(pager, manage.getUids()));
    }


    @RequestMapping("add")
    void add() {
    }

    @RequestMapping("edit")
    void edit(
            ModelMap map,
            @Check Long id
    ) {
        map.put("users", userService.getAll());
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
            String password,
            Long parent,
            String[] games
    ) {
        User user = new User();
        user.setId(id);
        user.setRealname(realname);
        if(!StringUtils.isEmpty(password)){
            user.setPassword(MD5Encoder.encode(password));
        }
        user.setParentId(parent);
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


    @RequestMapping(value = "setting_open", method = RequestMethod.POST)
    @ResponseBody
    Result setting_open(
            @Check Long id
    ) {
        User user = new User();
        user.setId(id);
        user.setSetting(1);
        if (userService.saveOrUpdate(user)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "setting_close", method = RequestMethod.POST)
    @ResponseBody
    Result setting_close(
            @Check Long id
    ) {
        User user = new User();
        user.setId(id);
        user.setSetting(0);
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

    @RequestMapping(value = "updateLimitLose", method = RequestMethod.POST)
    @ResponseBody
    Result updateLimitLose(
            @Check Long id,
            @Check(number = true, min = "0") Double limitLoseMoney
    ) {
        if (userService.updateLimitLose(id, limitLoseMoney)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "updateLimitWin", method = RequestMethod.POST)
    @ResponseBody
    Result updateLimitWin(
            @Check Long id,
            @Check(number = true, min = "0") Double limitWinMoney
    ) {
        if (userService.updateLimitWin(id, limitWinMoney)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "openDividing", method = RequestMethod.POST)
    @ResponseBody
    Result openDividing(
            @Check Long id
    ) {
        if (userService.openDividing(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "closeDividing", method = RequestMethod.POST)
    @ResponseBody
    Result closeDividing(
            @Check Long id
    ) {
        if (userService.closeDividing(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



    @RequestMapping(value = "openTraceLose", method = RequestMethod.POST)
    @ResponseBody
    Result openTraceLose(
            @Check Long id
    ) {
        if (userService.openTraceLose(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "closeTraceLose", method = RequestMethod.POST)
    @ResponseBody
    Result closeTraceLose(
            @Check Long id
    ) {
        if (userService.closeTraceLose(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



    @RequestMapping(value = "getPlat", method = RequestMethod.POST)
    @ResponseBody
    Result getPlat(
            @Check Long id
    ) {
        UserRule rule=userRuleService.get(id);
        Map map=new HashMap();
        map.put("game",rule.getGame());
        JSONObject object=new JSONObject();
        object.put("list",platService.getAllForTerminal(map));
        object.put("target",rule);
        return result.format(object);
    }


    @RequestMapping(value = "chosePlat", method = RequestMethod.POST)
    @ResponseBody
    Result chosePlat(
            @Check Long id,
            @Check Long platGameId
    ) {
        UserRule userRule=new UserRule();
        userRule.setId(id);
        userRule.setPlatGameId(platGameId);
        if(userRuleService.saveOrUpdate(userRule)==1){
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "updateLimitMaxWin", method = RequestMethod.POST)
    @ResponseBody
    Result updateLimitMaxWin(
            @Check Long id,
            @Check(number = true, min = "0") Double money
    ) {
        if (userService.updateLimitMaxWin(id, money)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "updateLimitMinWin", method = RequestMethod.POST)
    @ResponseBody
    Result updateLimitMinWin(
            @Check Long id,
            @Check(number = true, min = "0") Double money
    ) {
        if (userService.updateLimitMinWin(id, money)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "updateDirection", method = RequestMethod.POST)
    @ResponseBody
    Result updateDirection(
            @Check Long id,
            @Check int direction
    ) {
        if (userService.updateDirection(id, direction)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



    @RequestMapping(value = "openBottomWin", method = RequestMethod.POST)
    @ResponseBody
    Result openBottomWin(
            @Check Long id
    ) {
        if (userService.openBottomWin(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "closeBottomWin", method = RequestMethod.POST)
    @ResponseBody
    Result closeBottomWin(
            @Check Long id
    ) {
        if (userService.closeBottomWin(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



    @RequestMapping(value = "openIsTiming", method = RequestMethod.POST)
    @ResponseBody
    Result openIsTiming(
            @Check Long id
    ) {
        if (userService.openIsTiming(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

    @RequestMapping(value = "closeIsTiming", method = RequestMethod.POST)
    @ResponseBody
    Result closeIsTiming(
            @Check Long id
    ) {
        if (userService.closeIsTiming(id)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


}
