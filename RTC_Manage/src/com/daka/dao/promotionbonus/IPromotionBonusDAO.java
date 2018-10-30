package com.daka.dao.promotionbonus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PromotionBonusVO;

public interface IPromotionBonusDAO
{

	void insert(PromotionBonusVO vo) throws Exception;// 添加推广奖金记录

	void update(PromotionBonusVO vo) throws Exception;// 激活推广奖金

	List<PromotionBonusVO> findByCustomerId(@Param("customerId") int customerId) throws Exception;// 查询用户的推广奖金记录

	PromotionBonusVO findByChild(@Param("childCustomerId") int childCustomerId) throws Exception;// 根据被推广的id查询推广奖金信息

	PromotionBonusVO findById(@Param("id") int id);// 根据id查询推广奖金信息

	List<PageData> querydatalistPage(Page page);// 分页查询

	// /**
	// * 根据用户ID获取推广奖金数据
	// *
	// * @param customerId
	// * @return
	// * @throws Exception
	// */
	// List<PromotionBonusVO> findByCustomerId(int customerId) throws
	// Exception;;

	/**
	 * 根据用户批量更新推广奖金数据
	 * 
	 * @param customerId
	 */
	void updateUnfreezeInfoByCustomerId(Integer customerId) throws Exception;;

	/**
	 * 根据ID更新推广奖金数据
	 * 
	 * @param vo
	 */
	void updateUnfreezeInfoById(PromotionBonusVO vo) throws Exception;;

	/**
	 * 根据ID获取推广奖金数据
	 * 
	 * @param id
	 * @return
	 */
	PromotionBonusVO queryById(String id) throws Exception;;

}
