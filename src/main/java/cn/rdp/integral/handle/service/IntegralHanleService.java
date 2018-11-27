package cn.rdp.integral.handle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.rdp.common.MsgResponse;
import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:36:40
*   desc: 
*/
@Service
public interface IntegralHanleService {

	MsgResponse addVipIntegralDetail(VipIntegralDetailVO vo);
	
	MsgResponse addAddIntegralDetail(AddIntegralDetailVO vo);
	
	MsgResponse addSubIntegralDetail(List<SubIntegralDetailVO> vos);
}
