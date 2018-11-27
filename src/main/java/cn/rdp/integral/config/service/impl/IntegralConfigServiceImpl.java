package cn.rdp.integral.config.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.Query;
import cn.rdp.common.utils.ShiroUtils;
import cn.rdp.integral.config.mapper.IntegralConfigMapper;
import cn.rdp.integral.config.service.IntegralConfigService;
import cn.rdp.integral.domain.ConfigComboxVO;
import cn.rdp.integral.domain.IntegralConfigVO;
import cn.rdp.system.service.impl.PrimaryKeyServiceImpl;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:34:08
*   desc: 
*/
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class IntegralConfigServiceImpl implements IntegralConfigService{
	
	@Autowired
	private IntegralConfigMapper mapper;
	
	@Autowired
	private PrimaryKeyServiceImpl pkService;

	@Override
	public PageUtils findIntegralConfig(Map<String, Object> params) {
		Query query = new Query(params);
		List<IntegralConfigVO> vos = mapper.findIntegralConfig(query);
		int total = mapper.count(query);
		PageUtils page = new PageUtils(vos, total);
		return page;
	}
	
	@Override
	public IntegralConfigVO get(int pk, int type) {
		return mapper.get(pk, type);
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public MsgResponse deleteIntegralConfig(int id) {
		//校验
		//保存
		try {
			int count = mapper.deleteIntegralConfig(id);
			if(count > 0) {
				return new MsgResponse(true, "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "删除失败, " + e.getMessage());
		}
		return new MsgResponse(false, "删除失败");
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public MsgResponse batchDeleteIntegralConfig(List<Integer> ids, int type) {
		//校验
		//保存
		try {
			int count = mapper.batchDeleteIntegralConfig(ids, type);
			if(count > 0) {
				return new MsgResponse(true, "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "删除失败, " + e.getMessage());
		}
		return new MsgResponse(false, "删除失败");
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public MsgResponse updateIntegralConfig(IntegralConfigVO vo) {
		//校验
		//保存
		try {
			vo.setModifier(ShiroUtils.getUser().getUsername());
			int count = mapper.updateIntegralConfig(vo);
			if(count > 0) {
				return new MsgResponse(true, "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "修改失败, " + e.getMessage());
		}
		return new MsgResponse(false, "修改失败");
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public MsgResponse addIntegralConfig(IntegralConfigVO vo) {
		//校验
		//保存
		try {
			vo.setCreater(ShiroUtils.getUser().getUsername());
			vo.setPkGdIntegralConfig(pkService.generatePrimaryKey(vo.getTableName()));
			int count = mapper.addIntegralConfig(vo);
			if(count > 0) {
				return new MsgResponse(true, "添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "添加失败, " + e.getMessage());
		}
		return new MsgResponse(false, "添加失败");
	}

	@Override
	public List<IntegralConfigVO> find(Map<String, Object> params) {
		return mapper.find(params);
	}

	@Override
	public List<ConfigComboxVO> findConfigToPage(Map<String, Object> params) {
		List<IntegralConfigVO> vos = mapper.find(params);
		if(vos != null && vos.size() > 0) {
			List<ConfigComboxVO> ccs = new ArrayList<>();
			ConfigComboxVO cc = null;
			String type = params.get("integralType").toString();
			if("1".equals(type)
					|| "2".equals(type)) {
				//积分添加
				for(IntegralConfigVO vo : vos) {
					cc = new ConfigComboxVO();
					//添加： id + name + 系数
					//兑换： id + name + 积分
					cc.setId(vo.getPkGdIntegralConfig()+"_" + vo.getIntegralTypeName()+"_" + vo.getIntegralCoefficient());
					cc.setText(vo.getIntegralTypeName());
					ccs.add(cc);
				}
			}
			if("3".equals(type)){
				for(IntegralConfigVO vo : vos) {
					cc = new ConfigComboxVO();
					cc.setId(vo.getPkGdIntegralConfig()+"");
					cc.setText(vo.getIntegralTypeName());
					ccs.add(cc);
				}
			}
			if("4".equals(type)) {
				//积分添加
				for(IntegralConfigVO vo : vos) {
					cc = new ConfigComboxVO();
					//id + name + 系数
					cc.setId(vo.getIntegralTypeName());
					cc.setText(vo.getIntegralTypeName());
					ccs.add(cc);
				}
			}
			
			return ccs;
		}
		return null;
	}

}
