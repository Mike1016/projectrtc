package com.daka.service.fiveInvest;

import com.daka.dao.fiveInvest.IFiveTimesInvestDAO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiveTimesInvestService {
    @Autowired
    IFiveTimesInvestDAO fiveTimesInvestDAO;

    /**
     * 五倍复投 分页查询
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> queryFiveInvestlistPage(Page page) throws Exception{
        return fiveTimesInvestDAO.queryFiveInvestlistPage(page);
    }

    /**
     * 五倍复投日志 分页查询
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> queryFiveInvestLoglistPage(Page page) throws Exception{
        return fiveTimesInvestDAO.queryFiveInvestLoglistPage(page);
    }
}
