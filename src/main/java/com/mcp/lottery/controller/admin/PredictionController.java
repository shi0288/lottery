package com.mcp.lottery.controller.admin;

import com.mcp.lottery.model.Qiaoqiaoying;
import com.mcp.lottery.model.User;
import com.mcp.lottery.service.PredictionService;
import com.mcp.lottery.service.QiaoqiaoyingService;
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
@RequestMapping("lqmJHTqixle2eWaB/prediction")
public class PredictionController extends BaseController {

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private QiaoqiaoyingService qiaoqiaoyingService;


    @RequestMapping("list")
    void list(ModelMap map){
        map.put("e", qiaoqiaoyingService.get());
        map.put("list", predictionService.getAll());
    }


    @RequestMapping(value = "setting", method = RequestMethod.POST)
    @ResponseBody
    Result setting(
            @Check Long id,
            @Check String username,
            @Check String password,
            @Check String loginUrl,
            @Check String dataUrl,
            @Check String prizeUrl
    ) {
        Qiaoqiaoying qiaoqiaoying=new Qiaoqiaoying();
        qiaoqiaoying.setId(id);
        qiaoqiaoying.setUsername(username);
        qiaoqiaoying.setPassword(password);
        qiaoqiaoying.setLoginUrl(loginUrl);
        qiaoqiaoying.setDataUrl(dataUrl);
        qiaoqiaoying.setPrizeUrl(prizeUrl);
        if(qiaoqiaoyingService.update(qiaoqiaoying)){
            return result.format();
        }
        return result.format(ERROR, "保存出错");
    }



}
