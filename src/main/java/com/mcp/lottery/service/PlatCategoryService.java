package com.mcp.lottery.service;


import com.mcp.lottery.mapper.PlatCategoryMapper;
import com.mcp.lottery.model.PlatCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlatCategoryService {

    @Autowired
    private PlatCategoryMapper platCategoryMapper;

    public boolean save(PlatCategory platCategory) {
        if (platCategoryMapper.insertSelective(platCategory) == 1) {
            return true;
        }
        return false;
    }

    public void createExecutor(String executor,String name) {
        PlatCategory query = new PlatCategory();
        query.setExecutor(executor);
        if (platCategoryMapper.selectCount(query) == 0) {
            query.setName(name);
            platCategoryMapper.insertSelective(query);
        }
    }

    public List<PlatCategory> getAll(){
        return platCategoryMapper.selectAll();
    }


}
