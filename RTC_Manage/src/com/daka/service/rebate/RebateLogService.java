package com.daka.service.rebate;

import com.daka.dao.rebate.RebateLogDAO;
import com.daka.entry.RebateLogVO;
import com.daka.entry.dto.RebateLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RebateLogService {
    @Autowired
    RebateLogDAO rebateLogDAO;

    public List<RebateLogDTO> selectRebate(Integer id) {
        return rebateLogDAO.selectRebateList(id);
    }

    /**
     * 按时间段和类型查询返利数据
     * @auther: mike
     * @date: 11:44 2018/10/24
     * @param: [beginTime, endTime, type]
     * @return: java.util.Map<java.lang.Integer,java.math.BigDecimal>
     *
     */
    public List<RebateLogVO> queryAllRebateByType(String beginTime, String endTime, Integer type) {

        return rebateLogDAO.findAllRebateByType(beginTime, endTime, type);
    }
}
