package cn.rdp.oa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.oa.domain.NotifyDO;
import cn.rdp.oa.domain.NotifyDTO;

/**
 * 通知通告
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-05 17:11:16
 */
@Mapper
public interface NotifyMapper {

	NotifyDO get(Long id);

	List<NotifyDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NotifyDO notify);

	int update(NotifyDO notify);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<NotifyDO> listByIds(Long[] ids);

	int countDTO(Map<String, Object> map);

	List<NotifyDTO> listDTO(Map<String, Object> map);
}
