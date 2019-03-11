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

    int openTouzhu(@Param("id")Long id);

    int closeTouzhu(@Param("id")Long id);

    int updateInitMoney(@Param("id")Long id,@Param("initMoney")Double initMoney);

    int updateLimitLose(@Param("id")Long id,@Param("limitLoseMoney")Double initMoney);

    int updateLimitWin(@Param("id")Long id,@Param("limitWinMoney")Double initMoney);

    int openDividing(@Param("id")Long id);

    int closeDividing(@Param("id")Long id);

    int openTraceLose(@Param("id")Long id);

    int closeTraceLose(@Param("id")Long id);

    int updateLimitMaxWin(@Param("id")Long id,@Param("limitMaxWin")Double limitMaxWin);

    int updateLimitMinWin(@Param("id")Long id,@Param("limitMinWin")Double limitMinWin);

    int openBottomWin(@Param("id")Long id);

    int closeBottomWin(@Param("id")Long id);


}
