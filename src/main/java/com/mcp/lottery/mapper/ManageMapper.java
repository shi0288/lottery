package com.mcp.lottery.mapper;

import com.mcp.lottery.model.Manage;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageMapper extends BaseMapper<Manage>{
    void updateUids(@Param("id")Integer id, @Param("uids")String uids);
}
