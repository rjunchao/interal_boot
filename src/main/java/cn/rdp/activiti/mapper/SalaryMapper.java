package cn.rdp.activiti.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.activiti.domain.SalaryDO;

import java.util.List;
import java.util.Map;

/**
 * 审批流程测试表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-11-25 13:28:58
 */
@Mapper
public interface SalaryMapper {

	SalaryDO get(String id);
	
	List<SalaryDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SalaryDO salary);
	
	int update(SalaryDO salary);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
