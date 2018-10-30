package com.daka.dao.rebate;

import com.daka.entry.RebateLogVO;
import com.daka.entry.dto.RebateLogDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RebateLogDAO {

    List<RebateLogDTO> selectRebateList(Integer id); //查询佣金钱包

    int insertRebate(RebateLogVO rebateLogVO); //新增三级返利日志

    List<RebateLogVO> findAllRebateByType(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("type") Integer type);
}
