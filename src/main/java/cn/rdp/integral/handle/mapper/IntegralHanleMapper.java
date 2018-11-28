package cn.rdp.integral.handle.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:36:17
*   desc: 
*/
@Mapper
public interface IntegralHanleMapper {

	/**
	 * 查询当前用户在这一年是否已经送了积分，并且按次数统计
	 * @param cust
	 * @return
	 */
	int queryVipIntegralByYear(Map<String, Object> params);
	
	String querySubNumber(Map<String, Object> params);
	
	int addVipIntegralDetail(VipIntegralDetailVO vo);
	
	int addAddIntegralDetail(AddIntegralDetailVO vo);
	
	int addSubIntegralDetail(SubIntegralDetailVO vo);
	
	List<VipIntegralDetailVO> findVipIntegralDetailPage(Map<String, Object> params);
	
	List<AddIntegralDetailVO> findAddIntegralDetailPage(Map<String, Object> params);
	
	List<SubIntegralDetailVO> findSubIntegralDetailPage(Map<String, Object> params);
	
	int count(Map<String, Object> params);
}
