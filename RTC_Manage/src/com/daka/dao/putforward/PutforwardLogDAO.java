package com.daka.dao.putforward;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PutforwardLogVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PutforwardLogDAO {

    List<PageData> queryPutforwardlistPage(Page page); //提现记录

    Map<String,Object> queryById(Integer id); //通过id查询详情

    void updateState(PutforwardLogVO putforwardLogVO); //审核是否通过以及修改描述
    
    void insert(PutforwardLogVO vo);//新增提现申请
    
    PutforwardLogVO findById(@Param("id")Integer id);//查询提现记录
    
}
