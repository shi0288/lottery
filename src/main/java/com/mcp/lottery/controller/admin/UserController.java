package com.mcp.lottery.controller.admin;


import com.mcp.lottery.model.User;
import com.mcp.lottery.service.UserService;
import com.mcp.lottery.util.ArithUtil;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.MD5Encoder;
import com.mcp.lottery.util.Result;
import com.mcp.validate.annotation.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.mcp.lottery.util.ResultCode.ERROR;

@Controller
@RequestMapping("admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping("list")
    void list(ModelMap map) {
        map.put("list", userService.getAll());
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
            @Check(number = true) Double balance
    ) {
        User user = new User();
        user.setUsername(username);
        if (userService.exist(user)) {
            return result.format(ERROR, "用户名已经存在");
        }
        user.setPassword(MD5Encoder.encode(password));
        user.setBalance(balance);
        if (userService.saveOrUpdate(user)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    Result update(
            @Check Long id,
            @Check String password
    ) {
        User user = new User();
        user.setId(id);
        user.setPassword(MD5Encoder.encode(password));
        if (userService.saveOrUpdate(user)) {
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ResponseBody
    Result recharge(
            @Check Long id,
            @Check(number = true) Double balance
    ) {
        if(userService.addBalance(id,balance)){
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }

}