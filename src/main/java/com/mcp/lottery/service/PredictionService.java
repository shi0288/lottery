package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcp.lottery.mapper.PredictionMapper;
import com.mcp.lottery.model.Prediction;
import com.mcp.lottery.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    @Autowired
    private PredictionMapper predictionMapper;


    public void saveOrUpdate(Prediction prediction) {
        if (prediction.getId() == null) {
            predictionMapper.insertSelective(prediction);
        } else {
            predictionMapper.updateByPrimaryKeySelective(prediction);
        }
    }

    public Prediction get(Prediction prediction) {
        return predictionMapper.selectOne(prediction);
    }


    public PageInfo<Prediction> getAll(Pager pager, Map<String, Object> map) {
        Prediction prediction = new Prediction();
        prediction.setGame(String.valueOf(map.get("type")));
        PageInfo pageInfo = PageHelper.startPage(pager.getPage(), pager.getLimit(), "id desc").doSelectPageInfo(() -> predictionMapper.select(prediction));
        return pageInfo;
    }


}
