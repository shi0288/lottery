package com.mcp.lottery.mapper;


import com.mcp.lottery.model.Term;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TermMapper extends BaseMapper<Term>{

    List<Term> getNoneWinNumber(@Param("game")String game,@Param("day")String day);

}
