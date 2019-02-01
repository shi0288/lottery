package com.mcp.lottery.util.freemarker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ParamConvert implements TemplateMethodModelEx {

    public String transIndex(int index){
        switch (index){
            case 1:
                return "万位:";
            case 2:
                return "千位:";
            case 3:
                return "百位:";
            case 4:
                return "十位:";
            case 5:
                return "个位:";
        }
        return String.valueOf(index);
    }

    public String transGame(int way,int betResult){
        switch (way){
            case 1:
                switch (betResult){
                    case 1:
                        return "大";
                    case 2:
                        return "小";
                }
                return betResult+"";
            case 2:
                switch (betResult){
                    case 1:
                        return "单";
                    case 2:
                        return "双";
                }
                return betResult+"";
        }
        return way+""+betResult;
    }


    @Override
    public Object exec(List list) throws TemplateModelException {
        if (null != list) {
            String key = list.get(0).toString();
            switch (key) {
                case "ssc":
                    String data = String.valueOf(list.get(1));
                    JSONObject obj = JSON.parseObject(data);
                    JSONArray arr = obj.getJSONArray("items");
                    String[] res = new String[arr.size()];
                    for (int i = 0; i < arr.size(); i++) {
                        JSONObject temp=arr.getJSONObject(i);
                        res[i]=this.transIndex(temp.getIntValue("index"))+this.transGame(temp.getIntValue("way"),temp.getIntValue("betResult"));
                    }
                    return StringUtils.join(res," | ");
            }
        }
        return null;
    }
}