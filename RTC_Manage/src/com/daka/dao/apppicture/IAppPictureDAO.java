package com.daka.dao.apppicture;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.daka.entry.AppPictureVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;

public interface IAppPictureDAO {
	
	void insert(AppPictureVO vo);//新增图片
	
	AppPictureVO findById(@Param("id")int id);//根据id查询图片
	
	List<PageData> findAlllistPage(Page page);//分页查询
	
	void update(AppPictureVO vo);//修改图片
	
	void delete(@Param("id")int id);//删除
	
	List<AppPictureVO> findAll();//查询所有图片
	List<String> findImagePath(@Param("type") Integer type);
}
