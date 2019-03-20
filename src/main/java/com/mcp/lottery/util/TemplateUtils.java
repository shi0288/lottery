package com.mcp.lottery.util;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by daNuo.
 */

@Component
public class TemplateUtils {


    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    public String parse(String path, Map map) {
        String res = null;
        try {
            Template template = freeMarkerConfigurer.getConfiguration()
                    .getTemplate("lqmJHTqixle2eWaB/" +
                            path +
                            ".ftl");
            Writer out = new StringWriter();
            template.process(map, out);
            res = out.toString();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}
