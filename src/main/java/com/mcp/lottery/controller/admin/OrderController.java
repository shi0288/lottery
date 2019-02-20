package com.mcp.lottery.controller.admin;


import com.mcp.lottery.service.UserOrderLogService;
import com.mcp.lottery.service.UserService;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("lqmJHTqixle2eWaB/order")
public class OrderController extends BaseController {

    @Autowired
    private UserOrderLogService userOrderLogService;

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    void list(ModelMap map, Pager pager){
        Map param =  this.getParamMap(map);
        map.put("users", userService.getAll());
        map.put("page", userOrderLogService.getAllByDays(pager,param));
    }

}
