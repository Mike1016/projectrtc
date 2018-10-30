package com.daka.controller.apppicture;

import com.daka.api.base.Result;
import com.daka.constants.SystemConstant;
import com.daka.entry.AppPictureVO;
import com.daka.service.apppicture.AppPictureService;
import com.daka.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author: mike
 * @Description:
 * @Date: Created in 17:34 2018/10/16
 * @Modified By:
 */
@Controller
@RequestMapping("/apppicture")
public class AppPictureController {
    @Autowired
    private AppPictureService appPictureService;


    /**
     * 跳转到list.jsp
     *
     * @param request
     * @return
     */
    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("apppicture/list");
        return mv;
    }

    /**
     * 跳转到list.jsp
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAdd")
    public Object toAdd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("apppicture/add");
        return mv;
    }

    /**
     * 添加图片信息
     *
     * @date: 18:08 2018/10/16
     * @param: [appPictureVO]
     * @return: com.daka.api.base.Result
     */
    @RequestMapping("/addAppPicture")
    @ResponseBody
    public Result addAppPicture(AppPictureVO appPictureVO) {
        try {
            appPictureService.insertAppPicture(appPictureVO);
            return Result.newSuccess("添加成功", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.newSuccess("添加失败", 0);

    }

    /**
     * 删除图片信息
     *
     * @date: 18:04 2018/10/16
     * @param: [id]
     * @return: com.daka.api.base.Result
     */
    @RequestMapping("/removeAppPicture")
    @ResponseBody
    public Result removeAppPicture(int id) {
        try {
            appPictureService.deleteAppPictureById(id);
            return Result.newSuccess("删除成功", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.newSuccess("删除失败", 0);
    }

    /**
     * 修改图片信息
     *
     * @date: 17:53 2018/10/16
     * @param: []
     * @return: com.daka.api.base.Result
     */
    @RequestMapping("/modifyAppPicture")
    @ResponseBody
    public Result modifyAppPicture(AppPictureVO appPictureVO) {
        try {
            appPictureService.updateAppPictureById(appPictureVO);
            return Result.newSuccess("修改成功", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.newSuccess("修改失败", 0);
    }

    /**
     * 根据id查询图片信息
     *
     * @date: 18:56 2018/10/16
     * @param: [request]
     * @return: java.lang.Object
     */
    @RequestMapping("/queryAppPictureById")
    public Object queryAppPictureById(HttpServletRequest request) {
        String id = request.getParameter("id");
        ModelAndView mv = new ModelAndView("apppicture/review");
        Result result = null;
        try {
            result = appPictureService.queryAppPictureById(Integer.valueOf(id));
            mv.addObject("result", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 分页查询图片信息
     *
     * @date: 17:48 2018/10/16
     * @param: [request]
     * @return: java.lang.Object
     */
    @RequestMapping("/findAppPicturelistPage")
    @ResponseBody
    public Object findAppPicturelistPage(HttpServletRequest request) {

        try {
            return appPictureService.findAlllistpage(request);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.newFailure("系统错误，请联系管理员", 0);
        }
    }


    /**
     * 图片上传
     *
     * @param: [uploadFile, session]
     * @return: java.lang.String
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(MultipartFile file, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        // 获取图片的扩展名
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        String time = String.valueOf("apppicture" + System.currentTimeMillis());
        String newFileName = time + "." + extensionName;
        try {
            if (file != null) {
                String location = SystemConstant.LOCAL_PATH + "apppicture" + File.separator;
                File filePath = new File(location);
                if (!filePath.exists() && !filePath.isDirectory()) {
                    filePath.mkdirs();
                }
                Files.write(Paths.get(location + newFileName), file.getBytes());
            }
            String src = "/app/images" + File.separator + "apppicture" + File.separator + newFileName;
            //上传成功返回信息
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("message", "上传成功");
            result.put("src", src);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.newSuccess("上传失败", 0);
        }
    }

    /**
     * 查询全部图片信息
     *
     * @date: 15:08 2018/10/17
     * @param: []
     * @return: java.lang.Object
     */
    @RequestMapping("/findAllAppPicture")
    @ResponseBody
    public Object findAllAppPicture() {
        List<AppPictureVO> appPictureVOList = appPictureService.queryAllAppPicture();
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("data", appPictureVOList);
        return result;
    }

    /**
     * 查询不同类型图片的路径
     *
     * @date: 15:12 2018/10/17
     * @param: [type]
     * @return: java.lang.Object
     */
    @RequestMapping("/findImagePathByType")
    @ResponseBody
    public Object findImagePathByType(Integer type) {
        List<String> stringList = appPictureService.queryImagePathByType(type);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("data", stringList);
        return result;
    }

}
