package cn.rdp.common.service;

import org.springframework.stereotype.Service;

import cn.rdp.common.domain.LogDO;
import cn.rdp.common.domain.PageDO;
import cn.rdp.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
