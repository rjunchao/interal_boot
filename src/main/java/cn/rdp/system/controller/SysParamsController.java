package cn.rdp.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.annotation.Log;
import cn.rdp.common.controller.BaseController;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.Query;
import cn.rdp.system.domain.SysParamDO;
import cn.rdp.system.service.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:43:19
*   desc: 
*/

@RequestMapping("/system/param")
@Controller
@Api(value="系统参数配置", tags="SysParamsController")
public class SysParamsController extends BaseController {
	
	private String prefix="/system/param" ;
	
	@Autowired
	SysParamService paramService;
	
	@RequestMapping("")
	public String index(ModelAndView mv) {
		return prefix + "/param";
	}
	
	@Log("查询所有参数")
	@RequestMapping("list")
	@ResponseBody
	@ApiOperation(value="查询所有系统参数", notes="查询所有系统参数")
	@RequiresPermissions("sys:param")
	PageUtils findSysParam(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<SysParamDO> dos = paramService.findSysParam(query);
		int total = paramService.count(query);
		PageUtils pageUtil = new PageUtils(dos, total);
		return pageUtil;
	}
	
	
	
	@GetMapping("add")
	@RequiresPermissions("sys:param:add")
	public String toAddPage(ModelAndView mv) {
		return prefix + "/add";
	}
	
	@RequiresPermissions("sys:param:add")
	@Log("添加参数")
	@ApiOperation(value="添加参数", notes="添加参数")
	@PostMapping("save")
	@ResponseBody
	public MsgResponse add(SysParamDO param) {
		
		try {
			
			validateParam(param);
			param.setCreater(getUsername());
			int count = paramService.save(param);
			if(count > 0) {
				return new MsgResponse(true, "添加参数成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "添加参数失败, " + e.getMessage());
		}
		return new MsgResponse(false, "添加参数失败");
	}
	
	@RequiresPermissions("sys:param:edit")
	@Log("修改参数")
	@ApiOperation(value="修改参数", notes="修改参数")
	@PostMapping("updateSave")
	@ResponseBody
	public MsgResponse updateSave(SysParamDO param) {
		try {
//			validateParam(param);//不校验编码
			param.setModifier(getUsername());
			int count = paramService.update(param);
			if(count > 0) {
				return new MsgResponse(true, "修改参数成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "修改参数失败, " + e.getMessage());
		}
		return new MsgResponse(false, "修改参数失败");
	}
	
	
	private void validateParam(SysParamDO param) {
		Map<String, Object> queryParmMap = new HashMap<>();
		//
		queryParmMap.put("paramCode", param.getParamCode());
		SysParamDO sp = paramService.get(queryParmMap);
		if(sp != null) {
			throw new RuntimeException("重复的参数编码");
		}
		
	}

	@Log("根据id删除参数")
	@ApiOperation(value="根据id删除参数", notes="根据id删除参数")
	@PostMapping("remove")
	@ResponseBody
	@RequiresPermissions("sys:param:remove")
	public MsgResponse remove(int id) {
		MsgResponse msg = new MsgResponse();
		try {
			int count = paramService.delete(id);
			if(count > 0) {
				msg.setFlag(true);
				msg.setMessage("删除成功");
			}else {
				msg.setFlag(false);
				msg.setMessage("删除失败");
			}
		} catch(Exception e) {
			e.printStackTrace();
			msg.setFlag(false);
			msg.setMessage("删除失败, " + e.getMessage());
		}
		return msg;
	}
	
	
	@GetMapping("edit/{id}")
	@RequiresPermissions("sys:param:edit")
	public String toEditPage(@PathVariable("id") int id, Model model) {
		Map<String, Object> queryParmMap = new HashMap<>();
		queryParmMap.put("paramId", id);
		SysParamDO sp = paramService.get(queryParmMap);
		model.addAttribute("sp", sp);
		return prefix + "/edit";
	}
	
	
	@Log("批量删除系统参数")
	@ApiOperation(value="批量删除系统参数", notes="批量删除系统参数")
	@PostMapping("batchRemove")
	@ResponseBody
	@RequiresPermissions("sys:param:batchRemove")
	public MsgResponse batchRemove(@RequestParam("ids[]") List<Integer> ids) {
		MsgResponse msg = new MsgResponse();
		try {
			int count = paramService.batchRemove(ids);
			if(count > 0) {
				msg.setFlag(true);
				msg.setMessage("批量删除成功");
			}else {
				msg.setFlag(false);
				msg.setMessage("批量删除失败");
			}
		} catch(Exception e) {
			e.printStackTrace();
			msg.setFlag(false);
			msg.setMessage("批量删除失败, " + e.getMessage());
		}
		return msg;
	}
	
	
}
