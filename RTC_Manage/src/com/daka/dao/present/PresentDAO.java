package com.daka.dao.present;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PresentVO;

import java.util.List;
import java.util.Map;

public interface PresentDAO {

    List<PageData> queryPresentlistPage(Page page);

    Map<String,Object> queryPersentById(Integer id);

    void toUpdate(PresentVO presentVO); //修改状态

    void savePresent(PresentVO presentVO);

    Integer queryStateByCustomerId(Integer customerId); //根据用户id查询是否领取过奖励
}
