package com.mcp.lottery.service;


import com.mcp.lottery.mapper.PlatMapper;
import com.mcp.lottery.model.Plat;
import java.util.List;
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

    public List<Plat> getAll(){
        return platMapper.getAll();
    }

    public boolean save(Plat plat){
        if(platMapper.insertSelective(plat)==1){
            return true;
        }
        return false;
    }








}
