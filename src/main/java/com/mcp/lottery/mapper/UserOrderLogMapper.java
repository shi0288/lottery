package com.mcp.lottery.mapper;

import com.mcp.lottery.model.UserOrderLog;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserOrderLogMapper extends BaseMapper<UserOrderLog>{

    List<UserOrderLog> getAll(Map<String,Object> param);


    List<Map> getAllByDays(Map<String,Object> param);


}
