package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.mcp.lottery.mapper.TermMapper;
import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.cons.Cons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TermService {

    @Autowired
    private TermMapper termMapper;


    public Term getOpenTerm(String game) {
        Term query = new Term();
        query.setStatus(1);
        query.setGame(game);
        List<Term> list = termMapper.select(query);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public Term get(Term term){
        return termMapper.selectOne(term);
    }

    public boolean update(Term term){
        if(termMapper.updateByPrimaryKeySelective(term)==1){
            return true;
        }
        return false;

    }


    public void resetTerm(String game,long time) {
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        Term query = new Term();
        query.setGame(game);
        query.setStatus(0);
        List<Term> list = termMapper.select(query);
        if (list.size() == 1) {
            Term update = list.get(0);
            if (update.getOpenAt().getTime() - new Date().getTime() <= time) {
                Term updateDB=new Term();
                updateDB.setId(update.getId());
                updateDB.setStatus(1);
                termMapper.updateByPrimaryKeySelective(updateDB);
            }
        }
        PageHelper.startPage(1, 1);
        PageHelper.orderBy("id asc");
        query.setStatus(1);
        list = termMapper.select(query);
        if (list.size() == 1) {
            Term update = list.get(0);
            if (update.getCloseAt().getTime() - new Date().getTime() <= time) {
                Term updateDB=new Term();
                updateDB.setId(update.getId());
                updateDB.setStatus(2);
                termMapper.updateByPrimaryKeySelective(updateDB);
            }
        }
    }



}
