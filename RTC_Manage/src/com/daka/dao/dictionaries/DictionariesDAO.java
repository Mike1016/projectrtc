package com.daka.dao.dictionaries;

import com.daka.entry.DictionariesVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;

import java.util.List;
import java.util.Map;

public interface DictionariesDAO {

    List<DictionariesVO> queryAll(); //查询所有信息

    List<PageData> queryDictionarieslistPage(Page page);

    Map<String,Object> queryById(Integer id); //根据id查询详细信息

    void toUpdate(Map<String,Object> map); //根据id修改参数
}
