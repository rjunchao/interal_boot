package cn.rdp.integral.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rdp.common.utils.PageUtils;
import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;
import cn.rdp.integral.handle.mapper.IntegralHanleMapper;
import cn.rdp.integral.report.service.IntegralReportService;
import cn.rdp.integral.utils.IntegralUtils;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:38:31
*   desc: 
*/
@Service
public class IntegralReportImpl implements IntegralReportService{
	
	@Autowired
	private IntegralHanleMapper mapper;

	@Override
	public PageUtils findVipIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<VipIntegralDetailVO>(), 0);
		}
		List<VipIntegralDetailVO> vos = mapper.findVipIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(VipIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	@Override
	public PageUtils findAddIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<AddIntegralDetailVO>(), 0);
		}
		List<AddIntegralDetailVO> vos = mapper.findAddIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(AddIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	@Override
	public PageUtils findSubIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<SubIntegralDetailVO>(), 0);
		}
		List<SubIntegralDetailVO> vos = mapper.findSubIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(SubIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	

}
