package cn.rdp.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.rdp.system.domain.SysParamDO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:54:23
*   desc: 
*/
@Service
public interface SysParamService {

	int save(SysParamDO sp);
	
	int delete(int paramId);
	
	int update(SysParamDO sp);
	
	SysParamDO getParamByCode(String paramCode);
	
	int count(Map<String, Object> queryParmMap);
	
	List<SysParamDO> findSysParam(Map<String, Object> queryParmMap);
	
	int batchRemove(List<Integer> ids);
	
	SysParamDO get(Map<String, Object> queryParmMap);
}
