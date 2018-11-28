package cn.rdp.integral.report.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.rdp.common.domain.ExportFile;
import cn.rdp.common.utils.PageUtils;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:38:17
*   desc: 
*/
@Service
public interface IntegralReportService {

	PageUtils findVipIntegralDetail(Map<String, Object> params);
	
	PageUtils findAddIntegralDetail(Map<String, Object> params);
	
	PageUtils findSubIntegralDetail(Map<String, Object> params);
	
	ExportFile exportIntegralDetail(Map<String, Object> params, int type, int hiddenFlag);
	
}
