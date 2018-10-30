package com.daka.service.activation;

import com.daka.dao.activation.ActivationCodeDAO;
import com.daka.entry.ActivationCodeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.util.CodeUtil;
import com.daka.util.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivationCodeService {

    @Autowired
    private ActivationCodeDAO activationCodeDAO;

    public List<PageData> queryActivationCodelistPage(Page page) {
        return activationCodeDAO.queryActivationCodelistPage(page);
    }

    public void insertBatchActivationCode(int num) {
        List<ActivationCodeVO> vos = new ArrayList<ActivationCodeVO>();
        for(int i=0;i<num;i++) {
            ActivationCodeVO vo = new ActivationCodeVO();
            vo.setCode(CodeUtil.getRandomChar(8));
            vo.setState(0);
            vo.setCreateTime(DateUtil.getTime());
            vos.add(vo);
        }

        activationCodeDAO.insertBatch(vos);
    }

    public boolean queryCode() {
        List<String> cList = activationCodeDAO.queryCode();
        return CodeUtil.activationCodes.addAll(cList);
    }

    public void deleteCode(Integer id) {
        activationCodeDAO.deleteCode(id);
    }

    public void updateState(String code) {
        activationCodeDAO.updateState(code);
    }

    public String queryCodeState(@Param("code") String code){
        return activationCodeDAO.queryCodeState(code);
    }
}
