package com.daka.api.customer;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daka.api.base.BaseProvider;
import com.daka.api.base.Result;
import com.daka.constants.SystemConstant;
import com.daka.entry.CustomerVO;
import com.daka.entry.RechargeLogVO;
import com.daka.entry.dto.CustomerDTO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.queue.QueueUtil;
import com.daka.service.activation.ActivationCodeService;
import com.daka.service.customer.CustomerService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.util.DateUtil;
import com.daka.util.ExpiryMap;
import com.daka.util.MD5;
import com.daka.util.SMSUtil;
import com.daka.util.session.IdCardComplex;

@RequestMapping("/app/customer")
@RestController
public class CustomerProvider extends BaseProvider
{

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ActivationCodeService activationCodeService;

	@Autowired
	private RechargeLogService rechargeLogService;

	public static ExpiryMap<String, String> current_users = new ExpiryMap<>(1000 * 60 * 30);

	@RequestMapping("/AuthCode")
	public Result AuthCode(HttpServletRequest request, String phone)
	{// 获取验证码
		try
		{
			if (phone.isEmpty())
				return Result.newFailure("手机号不正确");
			String message = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
			SMSUtil.sendSms(message, phone);
			SMSMap.put(phone, message);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("获取失败");
		}
		return Result.newSuccess("获取成功");
	}

	@RequestMapping("/Regster")
	// 注册
	public Result Regster(HttpServletRequest request, CustomerVO vo, String authCode)
	{
		try
		{
			if (vo.getPhone().isEmpty())
				return Result.newFailure("手机号不正确");
			CustomerVO cus = customerService.findByPhone(vo.getPhone());
			if (cus != null)
			{
				return Result.newFailure("用户已注册");
			}
			if (vo.getPhone().equals(vo.getExtensionCode()))
				return Result.newFailure("请勿扫描自己的推荐码二维码注册");
			if (!authCode.equals(SMSMap.get(vo.getPhone())))
				return Result.newFailure("验证码不正确");
			vo.setPassword(MD5.md5(String.valueOf(vo.getPassword())));
			customerService.insertCustomer(vo);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("注册失败");
		}
		return Result.newSuccess("注册成功");
	}

	@RequestMapping("/retrievePass")
	public Result retrievePass(HttpServletRequest requsest, CustomerVO vo, String authCode)
	{
		try
		{
			if (vo.getPhone().isEmpty())
				return Result.newFailure("手机号不正确");
			if (!authCode.equals(SMSMap.get(vo.getPhone())))
				return Result.newFailure("验证码不正确");
			CustomerVO cus = customerService.findByPhone(vo.getPhone());
			if (cus == null)
				return Result.newFailure("手机号不存在");
			vo.setPassword(MD5.md5(String.valueOf(vo.getPassword())));
			vo.setId(cus.getId());
			customerService.updateByCustomer(vo);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("找回失败，请重试");
		}
		return Result.newSuccess("找回成功");
	}

	@RequestMapping("/checkLog")
	// 登录
	public Result checkLog(HttpServletRequest request, CustomerVO vo)
	{// vo
		// phone;password
		vo.setPassword(MD5.md5(vo.getPassword()));
		CustomerVO cus = customerService.checklog(vo);
		try
		{
			if (cus == null)
				return Result.newFailure("登录信息不正确");
			if (cus.getState() == 2)
				return Result.newFailure("该账号已被封号,不能登录");
			final String sessionId = SessionContext.createSession(request, SystemConstant.APP_USER, cus);
			current_users.put(vo.getPhone(), vo.getPhone());
			return Result.newSuccess(new HashMap<String, String>()
			{
				{
					put("sessionId", sessionId);
				}
			}, "登录成功");
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("登录失败");
		}
	}

	@RequestMapping("/LogOut")
	// 退出登录
	public Result LogOut(HttpServletRequest request, String sessionId)
	{
		try
		{
			HttpSession session = SessionContext.getSession(sessionId);
			if (session != null)
			{
				session.removeAttribute(SystemConstant.APP_USER);
				SessionContext.DelSession(session);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			return Result.newSuccess("退出成功");
		}
	}

	@RequestMapping("/updatePassword")
	public Result updatePassword(HttpServletRequest request, String sessionId, String authCode, String password)
	{
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (vo == null)
				return Result.newFailure("请重新登录", 5000);
			vo = customerService.checklog(vo);
			if (!authCode.equals(SMSMap.get(vo.getPhone())))
				return Result.newFailure("验证码不正确");
			vo.setPassword(MD5.md5(password));
			customerService.updateByCustomer(vo);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess("修改成功");
	}

	@RequestMapping("/updatePersonalImage")
	// 修改头像
	public Result updatePersonalImage(HttpServletRequest request, String sessionId, MultipartFile file) throws Exception
	{
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (vo == null)
				return Result.newFailure("请重新登录", 5000);
			if (file != null)
			{
				String location = SystemConstant.LOCAL_PATH + vo.getId() + File.separator;
				Path path = Paths.get(location);
				if (!Files.exists(path))
					Files.createDirectory(path);
				Files.write(Paths.get(location + file.getOriginalFilename()), file.getBytes());
			}
			CustomerVO cus = new CustomerVO();
			cus.setId(vo.getId());
			cus.setHeadImg(file.getOriginalFilename());
			customerService.updateByCustomer(cus);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess("修改成功");
	}

	/**
	 * 会员中心
	 */
	@RequestMapping("/memberCenter")
	public Result memberCenter(HttpServletRequest request, String sessionId)
	{
		CustomerDTO customerDTO = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			customerDTO = customerService.selectMemberCenter(vo.getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(customerDTO, "成功");
	}

	@RequestMapping("/updateSecurityPass")
	public Result updateSecurityPass(HttpServletRequest request, String sessionId, String authCode, String securityPass)
	{
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (vo == null)
				return Result.newFailure("请重新登录", 5000);
			if (securityPass.isEmpty())
				return Result.newFailure("安全密码不可为空", 5000);
			vo = customerService.checklog(vo);
			if (!authCode.equals(SMSMap.get(vo.getPhone())))
				return Result.newFailure("验证码不正确");
			vo.setSecurityPassword(MD5.md5(securityPass));
			customerService.updateByCustomer(vo);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess("修改成功");
	}

	@RequestMapping("/PerfectingPersonal") // 完善/修改个人资料
	public Result PerfectingPersonal(HttpServletRequest request, String sessionId, CustomerVO vo)
	{// vo:alipay,city,birthday,identityCard,nickName
		try
		{
			CustomerVO cus = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (cus == null)
				return Result.newFailure("请重新登录", 5000);
			boolean f = IdCardComplex.is18ByteIdCardComplex(vo.getIdentityCard());// 身份证是否合法
			if (!f)
			{
				return Result.newFailure("身份证不合法，请重新输入");
			}
			vo.setId(cus.getId());
			customerService.updateByCustomer(vo);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess("操作成功");
	}

	@RequestMapping("/AccessPersonal") // 获取用户信息
	public Result AccessPersonal(HttpServletRequest request, String sessionId)
	{
		try
		{
			CustomerVO cus = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (cus == null)
				return Result.newFailure("请重新登录", 5000);
			CustomerVO customer = customerService.checklog(cus);
			return Result.newSuccess(customer, "成功");
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
	}

	/**
	 * 使用激活码激活用户
	 * 
	 * @param request
	 * @param customerVO
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/updateCode")
	public Result updateCode(HttpServletRequest request, CustomerVO customerVO, String sessionId)
	{
		try
		{
			CustomerVO cus = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (cus == null)
				return Result.newFailure("请重新登录", 5000);
			if (activationCodeService.queryCodeState(customerVO.getCode()) == null)
				return Result.newFailure("激活码失效", 0);
			CustomerVO vo = customerService.queryCustomerById(cus.getId());
			if (vo.getState() != 0)
				return Result.newFailure("你已经激活过了", 0);
			if (vo.getCode() != null && vo.getCode() != "")
				return Result.newFailure("你已经激活过了", 0);
			customerVO.setId(vo.getId());
			customerVO.setWaitingDividendsWallet(vo.getWaitingDividendsWallet());
			if (customerVO.getCode() == null)
				return Result.newFailure("激活码不可为空", 0);
			activationCodeService.updateState(customerVO.getCode());
			customerService.updateCodeById(customerVO);
			QueueUtil.queue.put(vo.getId());
			RechargeLogVO rechargeLogVO = new RechargeLogVO();
			rechargeLogVO.setCustomerId(vo.getId());
			rechargeLogVO.setAccount(new BigDecimal(288));
			rechargeLogVO.setType(3);
			rechargeLogVO.setRemark("体验金到待分红钱包！");
			rechargeLogVO.setCreateTime(DateUtil.getTime());
			rechargeLogService.insert(rechargeLogVO);
			return Result.newSuccess("操作成功", 1);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("激活码不正确", 0);
		}

	}

	/**
	 * 团队详情
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/teamDetail")
	public Result teamDetail(HttpServletRequest request, String sessionId)
	{
		CustomerDTO customerDTO = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			customerDTO = customerService.getTeamStructure(vo.getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newSuccess("请联系管理员");
		}
		return Result.newSuccess(customerDTO, "成功");
	}

}
