package cn.rdp.integral.handle.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.utils.ShiroUtils;
import cn.rdp.common.utils.StringUtils;
import cn.rdp.integral.config.service.IntegralConfigService;
import cn.rdp.integral.cust.service.CustomerService;
import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.CustomerVO;
import cn.rdp.integral.domain.IntegralConfigVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;
import cn.rdp.integral.handle.mapper.IntegralHanleMapper;
import cn.rdp.integral.handle.service.IntegralHanleService;
import cn.rdp.system.domain.UserDO;
import cn.rdp.system.service.impl.PrimaryKeyServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:36:55
*   desc: 
*/
@Slf4j
@Service
@Transactional(readOnly = false,rollbackFor = Exception.class)
public class IntegralHanleServiceImpl implements IntegralHanleService{

	@Autowired
	private PrimaryKeyServiceImpl keyService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private IntegralConfigService configService;
	
	@Autowired
	private IntegralHanleMapper mapper;
	
	/**
	 * vip赠送赠送明细
	 */
	@Override
	public MsgResponse addVipIntegralDetail(VipIntegralDetailVO vo) {
		try {
			//根据客户主键把客户信息查询出来
			Map<String, Object> params = new HashMap<>();
			params.put("pkCustomerInfo", vo.getCustomerIdcard());//到后台的身份证字段存放的是客户主键，业务员的有*号不能用
			CustomerVO cust = custService.get(params);
			vo.setCustomerIdcard(cust.getCustomerIdcard());
			MsgResponse msg = validateVip(vo);
			if(msg != null) {
				return msg;
			}
			//添加客户积分
			double integral = vo.getGiveIntegral();
			vo.setCustomerIntegral(integral);
			cust.setNowUsableIntegral(cust.getNowUsableIntegral()+integral);
			custService.updateCustomerIntegral(cust);
			//插入积分明细
			UserDO user = ShiroUtils.getUser();
			vo.setCreater(user.getUsername());
			vo.setCreateOrg(user.getDeptId());//创建机构
			vo.setPkIntegralDetail(keyService.generatePrimaryKey("gd_vip_integral_detail"));
			int count = mapper.addVipIntegralDetail(vo);
			if(count > 0) {
				return new MsgResponse("积分赠送成功", true);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.info("积分赠送失败，" + e.getMessage());
			return new MsgResponse("积分赠送失败，" + e.getMessage(), false);
		}
		return new MsgResponse("积分赠送失败" , false);
	}

	/*
	 * 校验
	 * 	校验这个条目今年是否已经送完
	 * 	
	 */
	private MsgResponse validateVip(VipIntegralDetailVO vo) {
		Map<String, Object> params = new HashMap<>();
		//查询这个条目是否已经送完了
		params.put("integralType", "3");
		int id = vo.getConfigId();
		IntegralConfigVO cvo = configService.get(id, 3);
		//获取年
		int year = LocalDate.now().getYear();
		//设置显示信息
		vo.setGiveType(cvo.getIntegralTypeName());//类型中文
		vo.setGiveYear(year+"");//年份
		vo.setGiveIntegral(cvo.getIntegralCoefficient());//积分
		
		//查询积分明细看今年的赠送次数
		params.put("nowYear", year);//积分
		params.put("customerIdCard", vo.getCustomerIdcard());//积分
		params.put("configId", id);//积分主键
		
		int count = mapper.queryVipIntegralByYear(params);
		int number = Integer.parseInt(cvo.getDef2());
		if(count >= number) {
			return new MsgResponse("积分赠送类型" + vo.getDef1() + "已经用完，请选择其他条目", false);
		}
		return null;
	}

	/**
	 * 添加积分明细
	 */
	@Override
	public MsgResponse addAddIntegralDetail(AddIntegralDetailVO vo) {
		try {
			//根据客户主键把客户信息查询出来
			Map<String, Object> params = new HashMap<>();
			params.put("pkCustomerInfo", vo.getCustomerIdcard());//到后台的身份证字段存放的是客户主键，业务员的有*号不能用
			CustomerVO cust = custService.get(params);
			vo.setCustomerIdcard(cust.getCustomerIdcard());
			MsgResponse msg = validateAdd(vo);
			if(msg != null) {
				return msg;
			}
			//增加客户积分
			cust.setNowUsableIntegral(cust.getNowUsableIntegral()+vo.getCustomerIntegral());
			custService.updateCustomerIntegral(cust);
			//插入积分明细
			UserDO user = ShiroUtils.getUser();
			vo.setCreater(user.getUsername());
			vo.setCreateOrg(user.getDeptId());
			vo.setPkIntegralDetail(keyService.generatePrimaryKey("gd_add_integral_detail"));
			int count = mapper.addAddIntegralDetail(vo);
			if(count > 0) {
				return new MsgResponse("积分添加成功", true);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.info("积分添加失败，" + e.getMessage());
			return new MsgResponse("积分添加失败，" + e.getMessage(), false);
		}
		return new MsgResponse("积分添加失败" , false);
	}

	/**
	 * 积分添加校验
	 * @param vo
	 * @return
	 */
	private MsgResponse validateAdd(AddIntegralDetailVO vo) {
		String num = vo.getCustomerAccount();//账号
		if(num != null && !"".equals(num.trim())){
			if(!(num.length() == 16 || num.length() == 19)){
				//对的
				return new MsgResponse("账号填写错误，账号必须为16或19位", false);
			}
		}else{
			return new MsgResponse("账号为空，请重新填写", false);
		}
		//校验账号和存单号、存续期
		String deposit_receipt_num = vo.getDepositReceiptNum();
		String accountNumber = vo.getAccountNumber();//账户序号
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("customerAccount", num);//账号
			params.put("accountNumber", accountNumber);//账户序号
			List<AddIntegralDetailVO> vos = mapper.findAddIntegralDetailPage(params);
			String duration = vo.getDuration();//是否存续期
			if(vos != null && vos.size() > 0){
				for(AddIntegralDetailVO o : vos){
					//存单号是否已经存在
					if(deposit_receipt_num.equals(o.getDepositReceiptNum())){
						if("N".equals(duration)){
							return new MsgResponse("添加积分失败，账号、账户序号、存单号已经存在且不在存续期", false);
						}
					}else{
						//存单号不相同
						if("N".equals(duration)){
							return new MsgResponse("添加积分失败，账号、账户序号已经存在且不在存续期", false);
						}
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new MsgResponse("添加积分失败，" + e.getMessage(), false);
		}
		return null;
	}

	/**
	 * 积分兑换明细
	 */
	@Override
	public MsgResponse addSubIntegralDetail(List<SubIntegralDetailVO> vos) {
		//校验 兑换的积分数是否足够
		if(vos != null && vos.size() > 0) {
			//查询得到客户信息
			Map<String, Object> params = new HashMap<>();
			params.put("pkCustomerInfo", vos.get(0).getCustomerIdcard());//到后台的身份证字段存放的是客户主键，业务员的有*号不能用
			CustomerVO cust = custService.get(params);
			String idcard = cust.getCustomerIdcard();
			UserDO user = ShiroUtils.getUser();
			double countIntegral = 0.0;
			String subYear = LocalDate.now().getYear()+"";
			int sn = querySerialNumber(cust);
			for(SubIntegralDetailVO vo:vos) {
				countIntegral = countIntegral + vo.getCustomerIntegral();
				vo.setPkIntegralDetail(keyService.generatePrimaryKey("gd_sub_integral_detail"));//主键
				vo.setCustomerIdcard(idcard);//身份证
				vo.setCreater(user.getUsername());
				vo.setCreateOrg(user.getDeptId());
				vo.setSubYear(subYear);
				vo.setSubNum(sn);
			}
			if(countIntegral > cust.getNowUsableIntegral()) {
				return new MsgResponse("兑换积分超过了可用积分，修改兑换项或兑换数量", false);
			}
			//积分减少
			cust.setNowUsableIntegral(cust.getNowUsableIntegral()-countIntegral);
			custService.updateCustomerIntegral(cust);
			//插入积分明细
			int count = 0;
			try {
				for(SubIntegralDetailVO vo:vos) {
					count += mapper.addSubIntegralDetail(vo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return new MsgResponse(false, "积分兑换失败, " + e.getMessage());
			}
			if(count == vos.size()) {
				return new MsgResponse(true, "积分兑换成功");
			}
			return new MsgResponse(false, "积分兑换部分成功");
			
		}
		
		//插入数据库
		/*vo.setCreater(user.getUsername());
		vo.setPkIntegralDetail(keyService.generatePrimaryKey("gd_sub_integral_detail"));
		*/
		return new MsgResponse(false, "请先添加兑换商品数据");
	}

	private int querySerialNumber(CustomerVO cust) {
		Map<String, Object> params = new HashMap<>();
		params.put("customerIdCard", cust.getCustomerIdcard());
		params.put("subYear", LocalDate.now().getYear());
		int sn = 0;
		try {
			String snStr = mapper.querySubNumber(params);
			if(StringUtils.isNotEmpty(snStr)) {
				sn = Integer.parseInt(snStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return (sn + 1);//序号加1
	}

}
