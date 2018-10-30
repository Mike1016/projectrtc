package com.daka.service.dictionaries;

import com.daka.dao.dictionaries.DictionariesDAO;
import com.daka.entry.DictionariesVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictionariesService {
	public static Map<Integer, String> dictionaries = new HashMap<>();

    @Autowired
    private DictionariesDAO dictionariesDAO;

    public void init() {
		List<DictionariesVO> list = dictionariesDAO.queryAll();
		for (DictionariesVO vo : list) {
			dictionaries.put(vo.getType(), vo.getParameter());
		}
    }

    public List<PageData> queryDictionarieslistPage(Page page) {
        return dictionariesDAO.queryDictionarieslistPage(page);
    }

    public Map<String,Object> queryById(Integer id) {
        return dictionariesDAO.queryById(id);
    }

    public void toUpdate(Integer id,String parameter) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("parameter",parameter);
        dictionariesDAO.toUpdate(map);
        dictionaries.clear();
        init();
    }
}
