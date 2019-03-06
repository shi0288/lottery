package com.mcp.lottery.mapper;

import com.mcp.lottery.model.Plat;
import com.mcp.lottery.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlatMapper extends BaseMapper<Plat>{

    Plat get(@Param("id")Long id);

    List<Plat> getAll();

    List<Plat> getAllForTerminal(Map map);


}
