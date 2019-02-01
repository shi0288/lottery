package com.mcp.lottery.mapper;


import com.mcp.lottery.model.User;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    int addBalance(@Param("id")Long id,@Param("balance")Double balance);

}
