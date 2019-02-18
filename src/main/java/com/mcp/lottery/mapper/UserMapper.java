package com.mcp.lottery.mapper;


import com.mcp.lottery.model.User;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    int addBalance(@Param("id")Long id,@Param("balance")Double balance);

    List<User> getAll();

    int openTouzhu(@Param("uid")Long uid);

    int closeTouzhu(@Param("uid")Long uid);

    int updateInitMoney(@Param("uid")Long uid,@Param("initMoney")Double initMoney);


}
