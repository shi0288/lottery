package com.mcp.lottery.controller.admin;


import com.mcp.lottery.model.Plat;
import com.mcp.lottery.model.PlatGame;
import com.mcp.lottery.service.PlatCategoryService;
import com.mcp.lottery.service.PlatService;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.Pager;
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
    void list(ModelMap map,Pager pager){
        map.put("page", platService.getAll(pager));
    }


    @RequestMapping("listTerminal")
    void listTerminal(ModelMap map,Pager pager){
        map.put("page", platService.getAllForTerminal(pager));
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
        plat.setName(name.trim());
        plat.setUsername(username.trim());
        plat.setPassword(password.trim());
        plat.setLoginUrl(loginUrl.trim());
        plat.setBalanceUrl(balanceUrl.trim());
        plat.setTouzhuUrl(touzhuUrl.trim());
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


    @RequestMapping(value = "update_convert", method = RequestMethod.POST)
    @ResponseBody
    Result update_convert(
            @Check Long id
    ) {
        platService.updateConvert(id);
        return result.format();
    }


    @RequestMapping(value = "del_plat_game", method = RequestMethod.POST)
    @ResponseBody
    Result del(
            @Check Long id
    ) {
        platService.delPlatGame(id);
        return result.format();
    }


    @RequestMapping("addTerminal")
    void addTerminal(ModelMap map) {
        map.put("platList",platService.getAll());
    }


    @RequestMapping(value = "savePlatGame", method = RequestMethod.POST)
    @ResponseBody
    Result savePlatGame(
            @Check(name = "平台") Long platId,
            @Check(name = "游戏") String game
    ) {
        PlatGame platGame=new PlatGame();
        platGame.setPlatId(platId);
        platGame.setGame(game);
        if(platService.isExistPlatGame(platGame)){
            return result.format(ERROR, "已经存在");
        }
        platGame.setDirection(1);
        platService.savePlatGame(platGame);
        return result.format();
    }




}
