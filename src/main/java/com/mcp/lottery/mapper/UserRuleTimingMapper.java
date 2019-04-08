package com.mcp.lottery.mapper;

import com.mcp.lottery.model.UserRuleTiming;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRuleTimingMapper extends BaseMapper<UserRuleTiming> {

    List<UserRuleTiming> getList(@Param("uid")Long uid,@Param("game")String game);

    int updateBottomwin(@Param("uid")Long uid,@Param("game")String game);

}
