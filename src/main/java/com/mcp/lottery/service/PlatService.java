package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.PlatGameMapper;
import com.mcp.lottery.mapper.PlatMapper;
import com.mcp.lottery.model.Plat;

import java.util.List;

import com.mcp.lottery.model.PlatGame;
import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatService {

    @Autowired
    private PlatMapper platMapper;

    @Autowired
    private PlatGameMapper platGameMapper;


    public Plat get(Long id) {
        return platMapper.get(id);
    }

    public boolean update(Plat plat) {
        if (platMapper.updateByPrimaryKeySelective(plat) == 1) {
            return true;
        }
        return false;
    }

    public PageInfo<Plat> getAll(Pager pager) {
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> platMapper.getAll());
        return pageInfo;
    }

    public List<Plat> getAll() {
        return platMapper.getAll();
    }

    public PageInfo<Plat> getAllForTerminal(Pager pager) {
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit()).doSelectPageInfo(() -> platMapper.getAllForTerminal());
        return pageInfo;
    }


    public boolean save(Plat plat) {
        if (platMapper.insertSelective(plat) == 1) {
            return true;
        }
        return false;
    }

    public void updateConvert(Long id) {
        PlatGame platGame = platGameMapper.selectByPrimaryKey(id);
        PlatGame update = new PlatGame();
        update.setId(platGame.getId());
        if (platGame.getDirection() == 1) {
            update.setDirection(-1);
        } else {
            update.setDirection(1);
        }
        platGameMapper.updateByPrimaryKeySelective(update);
    }

    public void delPlatGame(Long id) {
        platGameMapper.deleteByPrimaryKey(id);
    }


    public boolean isExistPlatGame(PlatGame platGame){
        if(platGameMapper.selectCount(platGame)>0){
            return true;
        }
        return false;
    }


    public void savePlatGame(PlatGame platGame){
        platGameMapper.insertSelective(platGame);
    }


}
