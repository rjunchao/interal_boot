package cn.rdp.integral.cust.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.integral.domain.CustomerVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月21日 下午4:16:01
*   desc: 
*/
@Service
public interface CustomerService {

	PageUtils findCustomer(Map<String, Object> params);
	
	
	CustomerVO get(Map<String, Object> params);
	
	MsgResponse deleteCustomer(Map<String, Object> params);
	
	MsgResponse updateCustomer(CustomerVO vo, int hiddenFlag);
	
	MsgResponse addCustomer(CustomerVO vo);
	
	int updateCustomerIntegral(CustomerVO vo);
}
