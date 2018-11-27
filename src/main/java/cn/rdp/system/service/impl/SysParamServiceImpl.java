package cn.rdp.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rdp.system.domain.SysParamDO;
import cn.rdp.system.mapper.SysParamMapper;
import cn.rdp.system.service.IPrimaryKeyService;
import cn.rdp.system.service.SysParamService;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:54:41
*   desc: 
*/
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SysParamServiceImpl implements SysParamService{

	private static Map<String, SysParamDO> params = new HashMap<>();
	
	@Autowired
	SysParamMapper mapper;
	
	@Autowired
	IPrimaryKeyService service;
	
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int save(SysParamDO sp) {
		sp.setParamId(service.generatePrimaryKey("sys_params"));
		params.put(sp.getParamCode(), sp);
		return mapper.save(sp);
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int delete(int paramId) {
		params.clear();
		return mapper.delete(paramId);
	}
	

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int update(SysParamDO sp) {
		params.put(sp.getParamCode(), sp);
		return mapper.update(sp);
	}

//	@Cacheable(cacheNames= {"sysParams"})
	@Override
	public List<SysParamDO> findSysParam(Map<String, Object> queryParmMap) {
		return mapper.findSysParam(queryParmMap);
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int batchRemove(List<Integer> ids) {
		params.clear();
		return mapper.batchRemove(ids);
	}

	@Override
	public SysParamDO get(Map<String, Object> queryParmMap) {
		SysParamDO sp = mapper.get(queryParmMap);
		return sp;
	}

	@Override
	public int count(Map<String, Object> queryParmMap) {
		return mapper.count(queryParmMap);
	}
	
	@Override
	public SysParamDO getParamByCode(String paramCode) {
		SysParamDO param = params.get(paramCode);
		if(param == null) {
			Map<String, Object> paramMap = new HashMap<>();
			//不在缓存中
			//查询返回并放入缓存
			paramMap.put("paramCode", paramCode);
			SysParamDO sp = mapper.get(paramMap);
			params.put(paramCode, sp);
			return sp;
		}
		return param;
	}
	
	

}
