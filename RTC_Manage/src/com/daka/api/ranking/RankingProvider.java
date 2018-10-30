package com.daka.api.ranking;

import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.RankingVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.ranking.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/app/ranking")
@RestController
public class RankingProvider {

    @Autowired
    private RankingService rankingService;

    @RequestMapping("/showRanking")
    public Result showRanking(HttpServletRequest request,String sessionId) {
        CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (vo == null) return Result.newSuccess("获取信息失败", 5000);
        List<RankingVO> data = rankingService.queryRanking();
        return Result.newSuccess(data,"成功", 1);
    }
}
