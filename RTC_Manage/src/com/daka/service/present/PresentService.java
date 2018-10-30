package com.daka.service.present;

import com.daka.dao.present.PresentDAO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PresentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PresentService {

    @Autowired
    private PresentDAO presentDAO;

    public List<PageData> queryPresentlistPage(Page page) {
        return presentDAO.queryPresentlistPage(page);
    }

    public Map<String,Object> queryPersentById(Integer id) {
        return presentDAO.queryPersentById(id);
    }

    public void toUpdate(PresentVO presentVO) {
        presentDAO.toUpdate(presentVO);
    }

    public void savePresent(PresentVO presentVO) {
        presentDAO.savePresent(presentVO);
    }

    public Integer queryStateByCustomerId(Integer customerId) {
        return presentDAO.queryStateByCustomerId(customerId);
    }
}
