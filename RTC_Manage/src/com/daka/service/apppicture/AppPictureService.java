package com.daka.service.apppicture;

import com.daka.api.base.Result;
import com.daka.dao.apppicture.IAppPictureDAO;
import com.daka.entry.AppPictureVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: mike
 * @Description:
 * @Date: Created in 17:09 2018/10/16
 * @Modified By:
 */
@Service
public class AppPictureService {

    @Autowired
    private IAppPictureDAO AppPictureDAO;


    /**
     * 添加图片信息
     *
     * @date: 17:25 2018/10/16
     * @param: [appPictureVO]
     * @return: void
     */

    public void insertAppPicture(AppPictureVO appPictureVO) {
        AppPictureDAO.insert(appPictureVO);
    }

    /**
     * 删除图片信息
     *
     * @date: 17:28 2018/10/16
     * @param: [id]
     * @return: void
     */
    public void deleteAppPictureById(int id) {
        AppPictureDAO.delete(id);
    }


    /**
     * 修改图片信息
     *
     * @date: 17:26 2018/10/16
     * @param: [appPictureVO]
     * @return: void
     */
    public void updateAppPictureById(AppPictureVO appPictureVO) {
        AppPictureDAO.update(appPictureVO);
    }

    /**
     * 根据id查询图片信息
     *
     * @date: 18:23 2018/10/16
     * @param: [id]
     * @return: java.lang.Object
     */
    public Result queryAppPictureById(int id) {
        AppPictureVO vo = AppPictureDAO.findById(id);
        if (vo == null) {
            return Result.newFailure("选择错误，请重试", 0);
        }
        return Result.newSuccess(vo, 1);
    }

    /**
     * 分页查询，条件查询
     *
     * @date: 17:30 2018/10/16
     * @param: [request]
     * @return: java.util.List<com.daka.entry.AppPictureVO>
     */
    public Object findAlllistpage(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> apppictureList = AppPictureDAO.findAlllistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "");
        result.put("count", page.getTotalResult());
        result.put("data", apppictureList);
        return result;
    }

    /**
     * 查询全部图片信息
     *
     * @date: 14:43 2018/10/17
     * @param: []
     * @return: java.util.List<com.daka.entry.AppPictureVO>
     */
    public List<AppPictureVO> queryAllAppPicture() {
        return AppPictureDAO.findAll();
    }

    /**
     * 查询不同类型图片的路径
     *
     * @date: 14:54 2018/10/17
     * @param: [type]
     * @return: java.util.List<java.lang.String>
     */
    public List<String> queryImagePathByType(Integer type) {
        return AppPictureDAO.findImagePath(type);
    }

}
