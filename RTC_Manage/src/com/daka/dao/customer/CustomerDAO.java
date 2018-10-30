package com.daka.dao.customer;

import com.daka.entry.CustomerVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.dto.CustomerDTO;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

	List<PageData> queryCustomerlistPage(Page page); //分页查询

	List<CustomerVO> findByCustomer(CustomerVO vo);//根据属性查询用户

	CustomerVO queryCustomerById(@Param("id")Integer id); //根据id查找用户

	void insertCustomer(CustomerVO customer);//注册

	void updateByCustomer(CustomerVO customer);//修改用户信息
    
	void updateState1(Integer id); //根据id修改状态码，即是否封号

	void updateState2(Integer id); //根据id修改状态码，即是否解封

	void deleteById(Integer id); //删除用户信息

	void toUpdate(CustomerVO customerVO); //后台修改用户信息

	CustomerDTO selectMemberCenter(@Param("id") Integer id); //会员中心

	List<CustomerVO> queryExtension(Integer id); //查询直属推广

	List<CustomerVO> queryTeam(@Param("ids") String ids); //查询推广团队

	String queryChild(Integer id); //查询子级

    void updateCodeById(CustomerVO customerVO);

	List<CustomerDTO> selectTeam(Integer id); //查询团队

	void updateShoppingCoin(CustomerVO customerVO);//修改商城币 退款

	String queryParent(Integer id); //查询父级

	void updateTeamAchievement(@Param("parentIds") String parentIds,
							   @Param("account") BigDecimal account) throws SQLException; //更新团队业绩

	void updateDividendsWallet(@Param("id") Integer id,
							   @Param("account") BigDecimal account) throws SQLException; //第一代返利分红钱包

	void updatewaitingividendDsWallet(@Param("id") Integer id,
									  @Param("account") BigDecimal account) throws SQLException; //第二、三代返利待分红

	CustomerDTO selectGeneration(Integer id); //查询三代

	void updateShoppingCoinToPay(CustomerVO customerVO);//修改商城币 支付

	CustomerVO findByCode(@Param("extensionCode")String extensionCode);//根据推广码查询用户信息

	void saveWalletTask(@Param("rebate") BigDecimal rebate,
						@Param("proportion") BigDecimal proportion) throws SQLException; //分红定时任务

	CustomerVO findByPhone(@Param("phone") String phone);

	CustomerVO selectParentCustomerVo(Integer id);

	void updateParentDividendsWallet(CustomerVO parentVo);
	
	void updateDirParentCount(String id);
	
	void updateAllParentCount(@Param("ids") String ids);
	
	CustomerVO findByExtension(@Param("extensionCode")String extensionCode);//根据激活码查找上级
	
	void activationCustomer(@Param("id")int id);//后台激活用户
	
	CustomerDTO cumulativeRecord(@Param("id")int id);//查询用户累计充值金额、提现金额
}
