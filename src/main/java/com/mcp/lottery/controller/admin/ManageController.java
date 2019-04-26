package com.mcp.lottery.controller.admin;

import com.mcp.lottery.model.Manage;
import com.mcp.lottery.service.ManageService;
import com.mcp.lottery.service.UserService;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.Result;
import com.mcp.validate.annotation.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("lqmJHTqixle2eWaB/manage")
public class ManageController extends BaseController {

    @Autowired
    private ManageService manageService;

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    void list(ModelMap map){
        Map param =  this.getParamMap(map);
        map.put("list", manageService.getAll());
        map.put("userList", userService.getAll());
    }


    /**
     * 登陆验证
     */
    @RequestMapping("save")
    @ResponseBody
    Result save(
            @Check(name = "id") Integer id,
            String uids
    ) {
        manageService.updateUids(id, uids);
        return result.format();
    }
}
