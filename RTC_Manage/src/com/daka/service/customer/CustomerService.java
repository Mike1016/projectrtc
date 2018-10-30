package com.daka.service.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.constants.SystemConstant;
import com.daka.dao.bonus.BonusLogDAO;
import com.daka.dao.customer.CustomerDAO;
import com.daka.dao.rebate.RebateLogDAO;
import com.daka.dao.recharge.RechargeLogDAO;
import com.daka.dao.shopping.ShoppingCoinLogDAO;
import com.daka.entry.CustomerVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RebateLogVO;
import com.daka.entry.RechargeLogVO;
import com.daka.entry.dto.CustomerDTO;
import com.daka.enums.SystemEnum;
import com.daka.queue.QueueUtil;
import com.daka.service.dictionaries.DictionariesService;
import com.daka.util.DateUtils;
import com.daka.util.MD5;
import com.daka.util.session.RandomNickName;

@Service
public class CustomerService
{

	@Autowired
	private CustomerDAO customerDao;
	@Autowired
	private RechargeLogDAO rechargeLogDAO;
	@Autowired
	private BonusLogDAO bonusLogDAO;

	@Autowired
	private ShoppingCoinLogDAO shoppingCoinLogDAO;
	@Autowired
	private RebateLogDAO rebateLogDAO;

	/**
	 * 分页模糊查询
	 *
	 * @param page
	 * @return
	 */
	public List<PageData> queryCustomerlistPage(Page page)
	{
		return customerDao.queryCustomerlistPage(page);
	}

	/**
	 * @param phone
	 * @return
	 */
	public CustomerVO findByPhone(String phone)
	{// 跟据电话号码查询
		CustomerVO vo = customerDao.findByPhone(phone);
		return vo == null ? null : vo;
	}

	public CustomerVO queryCustomerById(Integer id)
	{
		return customerDao.queryCustomerById(id);
	}

	public void insertCustomer(CustomerVO customer)
	{// 注册
		CustomerVO cus = customerDao.findByExtension(customer.getExtensionCode());
		if(cus!=null){
			customer.setAgentId(cus.getId());// 父id
		}
		customer.setExtensionCode(customer.getPhone());
		customer.setRegesterTime(DateUtils.getCurrentTimeYMDHMS());
		customer.setSecurityPassword(MD5.md5(SystemConstant.CUSTOMER_SECURITYPASSWORD));
		customer.setLevel(SystemConstant.CUSTOMER_LEVEL);
		customer.setNickName(RandomNickName.getRandomNickName());// 随机昵称
		// customer.setState(0);
		customerDao.insertCustomer(customer);
		try
		{
			QueueUtil.queue.put(cus.getId());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public CustomerVO checklog(CustomerVO vo)
	{
		return customerDao.findByCustomer(vo).stream().findFirst().orElse(null);
	}

	public void updateByCustomer(CustomerVO customer)
	{// 修改用户资料
		customerDao.updateByCustomer(customer);
	}

	/**
	 * 封号
	 *
	 * @param id
	 */
	public void updateState1(Integer id)
	{
		customerDao.updateState1(id);
	}

	/**
	 * 解封
	 *
	 * @param id
	 */
	public void updateState2(Integer id)
	{
		customerDao.updateState2(id);
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	public void deleteById(Integer id)
	{
		customerDao.deleteById(id);
	}

	public void toUpdate(CustomerVO customerVO)
	{
		customerDao.toUpdate(customerVO);
	}

	public List<CustomerVO> queryExtension(Integer id)
	{
		return customerDao.queryExtension(id);
	}

	public String queryChild(Integer id)
	{
		String ids = customerDao.queryChild(id);
		ids = ids.substring(1, ids.length());
		return ids;
	}

	public List<CustomerVO> queryTeam(Integer id)
	{
		String ids = this.queryChild(id);
		List<CustomerVO> tList = customerDao.queryTeam(ids);
		return tList;
	}

	/**
	 * 会员中心
	 * 
	 * @param id
	 * @return
	 */
	public CustomerDTO selectMemberCenter(int id)
	{
		// String child = queryChild(id);
		return customerDao.selectMemberCenter(id);
	}

	public void updateCodeById(CustomerVO customerVO)
	{
		customerVO.setWaitingDividendsWallet(customerVO.getWaitingDividendsWallet().add(new BigDecimal(288)));
		customerDao.updateCodeById(customerVO);
	}

	// 结果集: AND (id IN(1,2,3,4) OR id IN(5,6,7,8))
	public String getChilds(String child)
	{
		StringBuffer childSQL = new StringBuffer();
		if (!child.isEmpty())
		{
			childSQL.append(" AND (id IN(");
			String[] childs = child.split(",");
			for (int i = 0; i < childs.length; i++)
			{
				if (i > 0 && i % 100 == 0)
				{
					childSQL.append(") OR id IN(" + childs[i] + ",");
				} else
				{
					childSQL.append(childs[i] + ",");
				}
			}
			childSQL.append(")) ");
		}
		return childSQL.toString().replaceAll(",\\)", "\\)");
	}

	/**
	 * 查询团队
	 * 
	 * @param id
	 * @return
	 */
	public List<CustomerDTO> selectTeam(Integer id)
	{
		return customerDao.selectTeam(id);
	}

	/**
	 * 团队详情
	 * 
	 * @param id
	 * @return
	 */
	public CustomerDTO getTeamStructure(Integer id)
	{
		List<CustomerDTO> list = selectTeam(id);
		List<CustomerDTO> level1 = new ArrayList<>();
		List<CustomerDTO> level2 = new ArrayList<>();
		List<CustomerDTO> level3 = new ArrayList<>();
		for (CustomerDTO customerDTO : list)
		{
			level1.add(customerDTO);
			for (CustomerDTO customerDTO1 : customerDTO.getChilds())
			{
				level2.add(customerDTO1);
				for (CustomerDTO customerDTO2 : customerDTO1.getChilds())
				{
					level3.add(customerDTO2);
					customerDTO2.setChilds(null);
				}
				customerDTO1.setChilds(null);
			}
			customerDTO.setChilds(null);
		}
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLevel1(level1);
		customerDTO.setLevel2(level2);
		customerDTO.setLevel3(level3);
		return customerDTO;
	}

	// 修改商城币
	public void updateShoppingCoin(CustomerVO customerVO)
	{
		customerDao.updateShoppingCoin(customerVO);
	}

	/**
	 * 三代返利、团队业绩
	 *
	 * @param rechargeLogVO
	 *            实体
	 */
	public void saveOrUpdateAchievement(RechargeLogVO rechargeLogVO) throws Exception
	{
		CustomerVO customerVO = customerDao.queryCustomerById(rechargeLogVO.getCustomerId()); // 用户
		String ids = customerDao.queryParent(rechargeLogVO.getCustomerId()); // 有效团队ID
		ids = ids.substring(1, ids.length());
		ids = getChilds(ids);
		BigDecimal account = null;
		CustomerDTO customerDTO = customerDao.selectGeneration(rechargeLogVO.getCustomerId()); // 三代用户
		// 第一代返利分红 第二、三代返利待分红 直推几人反几代
		if (customerDTO.getId() != null)
		{
			CustomerVO saveCustomerVO = new CustomerVO();
			saveCustomerVO.setId(customerVO.getId());
			saveCustomerVO.setWaitingDividendsWallet(
					customerVO.getWaitingDividendsWallet().add(doAccountLog(1001, rechargeLogVO)));
			customerDao.updateByCustomer(saveCustomerVO);
		}
		if (customerDTO.getID1() != null)
		{
			account = saveRebateLog(customerDTO.getId(), customerDTO.getID1(), rechargeLogVO.getAccount(), 1, 1201);
			customerDao.updateDividendsWallet(customerDTO.getID1(), account);
		}
		if (customerDTO.getID2() != null)
		{
			account = saveRebateLog(customerDTO.getId(), customerDTO.getID1(), rechargeLogVO.getAccount(), 2, 1202);
			customerDao.updatewaitingividendDsWallet(customerDTO.getID2(), account);
		}
		if (customerDTO.getID3() != null)
		{
			account = saveRebateLog(customerDTO.getId(), customerDTO.getID1(), rechargeLogVO.getAccount(), 3, 1203);
			customerDao.updatewaitingividendDsWallet(customerDTO.getID3(), account);
		}
		rechargeLogVO.setAccount(
				rechargeLogVO.getAccount().setScale(SystemConstant.ROUND_HALF_UP_NUM, BigDecimal.ROUND_HALF_UP));
		// 团队业绩
		customerDao.updateTeamAchievement(ids, rechargeLogVO.getAccount());
	}

	// 三代返利日志
	private BigDecimal saveRebateLog(Integer customerId, Integer agentId, BigDecimal account, Integer type,
			Integer param)
	{
		BigDecimal rebate = new BigDecimal(DictionariesService.dictionaries.get(param));
		RebateLogVO rebateLogVO = new RebateLogVO();
		rebateLogVO.setCustomerId(customerId);
		rebateLogVO.setAccount(
				account.multiply(rebate).setScale(SystemConstant.ROUND_HALF_UP_NUM, BigDecimal.ROUND_HALF_UP));
		rebateLogVO.setType(type);
		rebateLogVO.setAgentId(agentId);
		rebateLogVO.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		rebateLogDAO.insertRebate(rebateLogVO);// 三级返利日志
		return rebateLogVO.getAccount();
	}

	// 计算金额并记录日志
	private BigDecimal doAccountLog(Integer param, RechargeLogVO rechargeLogVO)
	{
		RechargeLogVO saveRechargeLogVO = new RechargeLogVO(); // 日志对象
		BeanUtils.copyProperties(rechargeLogVO, saveRechargeLogVO);
		BigDecimal rebate = new BigDecimal(DictionariesService.dictionaries.get(param));
		saveRechargeLogVO.setAccount(rechargeLogVO.getAccount().multiply(rebate)
				.setScale(SystemConstant.ROUND_HALF_UP_NUM, BigDecimal.ROUND_HALF_UP));
		rechargeLogDAO.insert(saveRechargeLogVO); // 日志
		return saveRechargeLogVO.getAccount();
	}

	/**
	 * 每天分红
	 * 
	 * @throws Exception
	 */
	public void saveWalletTask() throws Exception
	{
		BigDecimal rebate = new BigDecimal(DictionariesService.dictionaries.get(1101));
		BigDecimal proportion = new BigDecimal(DictionariesService.dictionaries.get(1302));
		shoppingCoinLogDAO.saveShopCoinLog(rebate, proportion, SystemEnum.DIVIDED_TO_SHOPPING_COIN.getValue()); // 分红到商城币日志
		proportion = new BigDecimal(DictionariesService.dictionaries.get(1301));

		rechargeLogDAO.saveWalletTask(SystemEnum.EVERY_TO_BONUS.getCode(), rebate, proportion,
				SystemEnum.EVERY_TO_BONUS.getValue(), DateUtils.getCurrentTimeYMDHMS()); // 待分红日志
		bonusLogDAO.saveWalletTask(SystemEnum.BONUS_TO_DIVIDED.getCode(), rebate, proportion,
				SystemEnum.BONUS_TO_DIVIDED.getValue(), DateUtils.getCurrentTimeYMDHMS()); // 分红日志

		customerDao.saveWalletTask(rebate, proportion); // 待分红到分红
	}

	/**
	 * 修改商城币 支付
	 *
	 * @param customerVO
	 */
	public void updateShoppingCoinToPay(CustomerVO customerVO)
	{
		customerDao.updateShoppingCoinToPay(customerVO);
	}

	public CustomerVO selectParentCustomerVo(Integer id)
	{
		return customerDao.selectParentCustomerVo(id);
	}

	public void updateDividendsWallet(CustomerVO parentVo)
	{
		customerDao.updateParentDividendsWallet(parentVo);
	}
	
	/**后台根据id激活用户
	 * @param customerId
	 */
	public void updateActivation(int customerId){
		customerDao.activationCustomer(customerId);
	}
	
}
