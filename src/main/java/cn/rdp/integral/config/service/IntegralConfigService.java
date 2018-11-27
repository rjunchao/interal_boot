package cn.rdp.integral.config.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.integral.domain.ConfigComboxVO;
import cn.rdp.integral.domain.IntegralConfigVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:33:46
*   desc: 
*/
@Service
public interface IntegralConfigService {
	
	List<ConfigComboxVO> findConfigToPage(Map<String, Object> params);

	List<IntegralConfigVO> find(Map<String, Object> params);
	
	PageUtils findIntegralConfig(Map<String, Object> params);
	
	IntegralConfigVO get(int pk, int type);
	
	MsgResponse deleteIntegralConfig(int id);
	
	MsgResponse batchDeleteIntegralConfig(List<Integer> ids, int type);
	
	MsgResponse updateIntegralConfig(IntegralConfigVO vo);
	
	MsgResponse addIntegralConfig(IntegralConfigVO vo);
}
