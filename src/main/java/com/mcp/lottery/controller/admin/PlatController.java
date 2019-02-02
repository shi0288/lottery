package com.mcp.lottery.controller.admin;


import com.mcp.lottery.model.Plat;
import com.mcp.lottery.service.PlatCategoryService;
import com.mcp.lottery.service.PlatService;
import com.mcp.lottery.util.BaseController;
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
@RequestMapping("lqmJHTqixle2eWaB/plat")
public class PlatController extends BaseController{

    @Autowired
    private PlatService platService;

    @Autowired
    private PlatCategoryService platCategoryService;

    @RequestMapping("list")
    void list(ModelMap map){
        map.put("list", platService.getAll());
    }


    @RequestMapping("add")
    void add(ModelMap map) {
        map.put("cateList",platCategoryService.getAll());
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    Result save(
            @Check Long categoryId,
            @Check String name,
            @Check String username,
            @Check String password,
            @Check String loginUrl,
            @Check String balanceUrl,
            @Check String touzhuUrl
    ) {
        Plat plat=new Plat();
        plat.setCategoryId(categoryId);
        plat.setName(name);
        plat.setUsername(username);
        plat.setPassword(password);
        plat.setLoginUrl(loginUrl);
        plat.setBalanceUrl(balanceUrl);
        plat.setTouzhuUrl(touzhuUrl);
        if(platService.save(plat)){
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }


    @RequestMapping("edit")
    void edit(
            ModelMap map,
            @Check Long id
    ) {
        map.put("e",platService.get(id));
        map.put("cateList",platCategoryService.getAll());
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    Result update(
            @Check Long id,
            @Check Long categoryId,
            @Check String name,
            @Check String username,
            @Check String password,
            @Check String loginUrl,
            @Check String balanceUrl,
            @Check String touzhuUrl
    ) {
        Plat plat=new Plat();
        plat.setId(id);
        plat.setCategoryId(categoryId);
        plat.setName(name);
        plat.setUsername(username);
        plat.setPassword(password);
        plat.setLoginUrl(loginUrl);
        plat.setBalanceUrl(balanceUrl);
        plat.setTouzhuUrl(touzhuUrl);
        if(platService.update(plat)){
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



}
