package cn.rdp.integral.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.rdp.integral.domain.IntegralConfigVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:33:23
*   desc: 
*/
@Mapper
public interface IntegralConfigMapper {


	List<IntegralConfigVO> find(Map<String, Object> params);
	
	List<IntegralConfigVO> findIntegralConfig(Map<String, Object> params);
	
	int count(Map<String, Object> params);
	
	int deleteIntegralConfig(int id);
	
	IntegralConfigVO get(@Param("id")int id, @Param("type") int type);
	
	int batchDeleteIntegralConfig(@Param("ids")List<Integer> ids, @Param("type")int type);
	
	int updateIntegralConfig(IntegralConfigVO vo);
	
	int addIntegralConfig(IntegralConfigVO vo);
	
	
}
