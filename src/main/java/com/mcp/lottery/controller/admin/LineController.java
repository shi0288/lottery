package com.mcp.lottery.controller.admin;

import com.mcp.lottery.util.BaseController;
import com.mcp.lottery.util.HttpClientWrapper;
import com.mcp.lottery.util.HttpResult;
import com.mcp.lottery.util.Result;
import com.mcp.lottery.util.cons.Cons;
import com.mcp.validate.annotation.Check;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mcp.lottery.util.ResultCode.ERROR;

@Controller
@RequestMapping("lqmJHTqixle2eWaB/line")
public class LineController extends BaseController {


    @RequestMapping("penetrated")
    void penetrated() {
    }

    @RequestMapping(value = "penetratedData", method = RequestMethod.POST)
    @ResponseBody
    Result save(
            @Check(defaultValue = Cons.Game.TXFFC) String game,
            @Check(name = "时间") String startTime,
            @Check(name = "时间") String endTime,
            @Check(name = "轴范围") String downPoint,
            @Check(name = "轴范围") String upPoint

    ) {
        if (Integer.parseInt(downPoint) > Integer.parseInt(upPoint)) {
            return result.format(ERROR, "轴范围应该从小到大");
        }
        Map param = new HashMap();
        param.put("start", startTime);
        param.put("end", endTime);
        param.put("game", game);
        List<String> list=new ArrayList<>();
        for (int i = Integer.parseInt(downPoint); i <= Integer.parseInt(upPoint); i++) {
            param.put("up_point", String.valueOf(i));
            param.put("down_point", String.valueOf(i));
            HttpResult httpResult = HttpClientWrapper.sendGet("http://47.93.121.107:4000/api/line/penetrated/simulate", null, param);
            list.add(httpResult.getResult());
        }
        return result.format(list);
    }


}
