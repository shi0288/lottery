package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.PlatMapper;
import com.mcp.lottery.model.Plat;
import java.util.List;

import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatService {

    @Autowired
    private PlatMapper platMapper;


    public Plat get(Long id){
        return platMapper.get(id);
    }

    public boolean update(Plat plat){
        if(platMapper.updateByPrimaryKeySelective(plat)==1){
            return true;
        }
        return false;
    }

    public PageInfo<Plat> getAll(Pager pager){
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> platMapper.getAll());
        return pageInfo;
    }

    public boolean save(Plat plat){
        if(platMapper.insertSelective(plat)==1){
            return true;
        }
        return false;
    }








}
