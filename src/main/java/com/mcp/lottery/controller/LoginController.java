package com.mcp.lottery.controller;


import com.mcp.lottery.model.Manage;
import com.mcp.lottery.service.ManageService;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.Result;
import com.mcp.validate.annotation.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("JiswyAaKgoJmqutA")
public class LoginController extends BaseController{

    @Autowired
    private ManageService manageService;

    /**
     * 登陆页
     */
    @RequestMapping("login")
    String login() {
        return "login";
    }

    /**
     * 登陆页
     */
    @RequestMapping("logout")
    String logout() {
        this.httpSession.removeAttribute("manage");
        return "redirect:login";
    }



    /**
     * 登陆验证
     */
    @RequestMapping("checkUser")
    @ResponseBody
    Result checkUser(
            @Check(name = "用户名") String username,
            @Check(name = "密码") String password
    ) {
        Manage manage=manageService.checkUser(username,password);
        this.httpSession.setAttribute("manage",manage);
        return result.format();
    }




}
