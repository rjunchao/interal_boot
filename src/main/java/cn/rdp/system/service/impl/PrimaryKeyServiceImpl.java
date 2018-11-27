package cn.rdp.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rdp.system.domain.PrimaryKeyVO;
import cn.rdp.system.mapper.IPrimaryKeyMapper;
import cn.rdp.system.service.IPrimaryKeyService;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年10月27日 下午7:50:33
*   desc: 主键生成服务
*/
@Service
public class PrimaryKeyServiceImpl implements IPrimaryKeyService{

	@Autowired
	IPrimaryKeyMapper mapper;
	
	public int generatePrimaryKey(String table_name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", table_name);
		PrimaryKeyVO vo = findTableInfo(params);
		params.put("primary_name", vo.getPrimaryName());
		//MAX(CONVERT(def8,DECIMAL)) 
		String sql = "SELECT MAX(CONVERT("+vo.getPrimaryName()+", DECIMAL)) FROM " + table_name;
		params.put("sql", sql);
		int maxValue = findTableMaxValue(params);
		return (maxValue + vo.getStepValue());
	}
	
	public PrimaryKeyVO findTableInfo(Map<String, Object> params) {
		return mapper.findTableInfo(params);
	}

	public int findTableMaxValue(Map<String, Object> params) {
		return mapper.findTableMaxValue(params);
	}
	

}
