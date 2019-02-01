package com.mcp.lottery.service;


import com.github.pagehelper.PageHelper;
import com.mcp.lottery.mapper.PredictionMapper;
import com.mcp.lottery.model.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Prediction> getAll() {
        PageHelper.orderBy("id desc");
        return predictionMapper.selectAll();
    }


}
