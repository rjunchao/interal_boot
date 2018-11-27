package cn.rdp.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.system.domain.SysParamDO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:44:29
*   desc: 
*/
@Mapper
public interface SysParamMapper {

	int save(SysParamDO sp);
	
	int delete(int paramId);
	
	int update(SysParamDO sp);

	int batchRemove(List<Integer> ids);
	
	int count(Map<String, Object> queryParmMap);
	
	List<SysParamDO> findSysParam(Map<String, Object> queryParmMap);
	
	SysParamDO get(Map<String, Object> queryParmMap);
}
