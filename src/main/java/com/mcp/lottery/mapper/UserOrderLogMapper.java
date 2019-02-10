package com.mcp.lottery.mapper;

import com.mcp.lottery.model.UserOrderLog;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOrderLogMapper extends BaseMapper<UserOrderLog>{

    List<UserOrderLog> getAll(@Param("uid")Long uid);

}
