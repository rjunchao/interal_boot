package cn.rdp.integral.cust.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.integral.domain.CustomerVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月21日 下午4:15:15
*   desc: 
*/
@Mapper
public interface CustomerMapper {

	List<CustomerVO> findCustomer(Map<String, Object> params);
	
	int count(Map<String, Object> params);
	
	CustomerVO get(Map<String, Object> params);
	
	int deleteCustomer(Map<String, Object> params);
	
	int updateCustomer(CustomerVO vo);
	
	int updateCustomerIntegral(CustomerVO vo);
	
	int addCustomer(CustomerVO vo);
	
}
