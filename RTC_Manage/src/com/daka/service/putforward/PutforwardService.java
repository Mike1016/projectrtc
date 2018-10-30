package com.daka.service.putforward;

import com.daka.dao.putforward.PutforwardLogDAO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PutforwardLogVO;
import com.daka.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PutforwardService {

    @Autowired
    private PutforwardLogDAO putforwardLogDAO;

    public List<PageData> queryPutforwardlistPage(Page page) {
        return putforwardLogDAO.queryPutforwardlistPage(page);
    }

    public Map<String,Object> queryById(Integer id) {
        return putforwardLogDAO.queryById(id);
    }

    public void updateState(PutforwardLogVO putforwardLogVO) {
        putforwardLogVO.setUpdateTime(DateUtil.getTime());
        putforwardLogDAO.updateState(putforwardLogVO);
    }
    
    public void insert(PutforwardLogVO vo){
    	putforwardLogDAO.insert(vo);
    }
    
    public PutforwardLogVO findById(Integer id){
    	return putforwardLogDAO.findById(id);
    }
    

}
