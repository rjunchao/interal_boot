package cn.rdp.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.rdp.blog.domain.ContentDO;

/**
 * 文章内容
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 16:17:48
 */
@Mapper
public interface ContentMapper {

	ContentDO get(Long cid);
	
	List<ContentDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ContentDO content);
	
	int update(ContentDO content);
	
	int remove(Long cid);
	
	int batchRemove(Long[] cids);
}
