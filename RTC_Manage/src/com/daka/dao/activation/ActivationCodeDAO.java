package com.daka.dao.activation;

import com.daka.entry.ActivationCodeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivationCodeDAO {

    List<PageData> queryActivationCodelistPage(Page page);

    void insertBatch(List<ActivationCodeVO> list);

    List<String> queryCode();//查询所有的激活码

    void deleteCode(Integer id); //根据id删除激活码

    void updateState(String code);

    String queryCodeState(@Param("code") String code);
}
