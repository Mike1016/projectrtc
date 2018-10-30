package com.daka.service.ranking;

import com.daka.dao.ranking.RankingDAO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RankingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RankingService {

    @Autowired
    private RankingDAO rankingDAO;

    public List<PageData> queryRankinglistPage(Page page) {
        return rankingDAO.queryRankinglistPage(page);
    }

    public void insertAccount(RankingVO rankingVO) {
        rankingVO.setState(1);
        rankingDAO.insertAccount(rankingVO);
    }

    public void deleteById(Integer id) {
        rankingDAO.deleteById(id);
    }

    public void updateRanking(RankingVO rankingVO) {
        rankingDAO.updateRanking(rankingVO);
    }

    public Map<String,Object> queryRankingById(Integer id) {
        return rankingDAO.queryRankingById(id);
    }

    public List<RankingVO> queryRanking() {
        return rankingDAO.queryRanking();
    }

	/**
	 * 每周排名
	 * @throws Exception
	 */
	public void saveRankingTask() throws Exception {
		rankingDAO.deleteRanking(); //清空排名
		rankingDAO.saveRankingTask();
	}

	public String queryPhone(String phone) {
	    return rankingDAO.queryPhone(phone);
    }
}
