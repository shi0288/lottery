package com.mcp.lottery.util.command;

import com.mcp.lottery.service.PlatCategoryService;
import com.mcp.lottery.util.PluginCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitPluginType implements CommandLineRunner {

    @Autowired
    private PlatCategoryService platCategoryService;


    @Override
    public void run(String... strings) throws Exception {
        PluginCache.map().keySet().forEach(key -> platCategoryService.createExecutor(key, PluginCache.get(key)));
    }
}
