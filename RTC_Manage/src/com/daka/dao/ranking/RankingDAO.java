package com.daka.dao.ranking;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RankingVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RankingDAO {

    List<PageData> queryRankinglistPage(Page page); //查询排行榜

    void insertAccount(RankingVO rankingVO); //后台用来模拟数据

    void deleteById(Integer id);

    void updateRanking(RankingVO rankingVO); //根据id修改信息

    Map<String,Object> queryRankingById(Integer id);

    List<RankingVO> queryRanking();

    void deleteRanking(); //清空排名

	void saveRankingTask() throws SQLException; //生成排名

    String queryPhone(String phone);
}
