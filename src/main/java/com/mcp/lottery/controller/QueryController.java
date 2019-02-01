package com.mcp.lottery.controller;

import com.mcp.lottery.model.Plat;
import com.mcp.lottery.service.PlatService;
import com.mcp.lottery.service.QiaoqiaoyingService;
import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.Plugin;
import com.mcp.lottery.util.Result;
import com.mcp.lottery.util.SpringIocUtil;
import com.mcp.validate.annotation.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mcp.lottery.util.ResultCode.ERROR;


/**
 * 查询相关
 */

@RestController
@RequestMapping("query")
public class QueryController extends BaseController {

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;

    @Autowired
    private PlatService platService;



    @RequestMapping("balance")
    public Result getBalance(
            @Check Long id
    ) {
        Plat plat = platService.get(id);
        Plugin plugin = (Plugin) SpringIocUtil.getBean(plat.getPlatCategory().getExecutor());
        Double money = plugin.getBalance(plat);
        if (money != null) {
            result.format(money);
        }
        return result.format(ERROR, "登陆失效");
    }

    @RequestMapping("updateAuthor")
    public Result updateAuthor(
            @Check Long id
    ) {
        Plat plat = platService.get(id);
        Plugin plugin = (Plugin) SpringIocUtil.getBean(plat.getPlatCategory().getExecutor());
        plugin.getAuthor(plat);
        if (platService.update(plat)) {
            return result.format();
        }
        return result.format(ERROR, "更新失败");
    }




}
