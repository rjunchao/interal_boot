package cn.rdp.system.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.system.domain.PrimaryKeyVO;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年10月27日 下午7:39:42
*   desc: 
*/
@Mapper
public interface IPrimaryKeyMapper {

	/**
	 * 查询表信息
	 * @param params
	 * @return
	 */
	PrimaryKeyVO findTableInfo(Map<String, Object> params);
	/**
	 * 查询表主键的最大值
	 * @param params
	 * @return
	 */
	int findTableMaxValue(Map<String, Object> params);
}
