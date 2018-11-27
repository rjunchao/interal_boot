package cn.rdp.integral.config.controller;

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

import cn.rdp.common.MsgResponse;
import cn.rdp.common.annotation.Log;
import cn.rdp.common.controller.BaseController;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.integral.config.service.IntegralConfigService;
import cn.rdp.integral.domain.ConfigComboxVO;
import cn.rdp.integral.domain.IntegralConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:34:31
*   desc: 积分基础数据配置
*/
@Api(value="积分配置", tags="IntegralConfigController")
@Controller
@RequestMapping("/integral/config")
public class IntegralConfigController extends BaseController{

	private static String CONFIG_PREFIX = "/integral/config/";
	
	@Autowired
	private IntegralConfigService service;
	
	@GetMapping("index/{integralType}")
	public String index(@PathVariable("integralType") int type) {
		//1：添加积分，2：减少积分, 3:vip赠送，4：资金来源
		switch (type) {
			case 1:
				return CONFIG_PREFIX + "add/addIntegral";
			case 2:
				return CONFIG_PREFIX + "sub/integralConfigSub";
			case 3:
				return CONFIG_PREFIX + "vip/integralConfigVip";
			case 4:
				return CONFIG_PREFIX + "source/integralConfigSc";
		}
		throw new RuntimeException("请输入类型");
	}
	
	
	@Log("查询添加配置")
	@ResponseBody
	@GetMapping("list")
	@RequiresPermissions("sys:config")
	@ApiOperation(value="查询添加积分配置", notes="查询添加积分配置")
	public PageUtils findIntegralConfig(@RequestParam Map<String, Object> params) {
		return service.findIntegralConfig(params);
	}
	/**
	 * 页面转发
	 * @param type
	 * @return
	 */
	@RequiresPermissions("sys:config:add")
	@GetMapping("add/{integralType}")
	public String addToPage(@PathVariable("integralType") int type) {
		switch (type) {
			case 1:
				return CONFIG_PREFIX + "add/addIntegralConfigAdd";
			case 2:
				return CONFIG_PREFIX + "sub/integralConfigSubAdd";
			case 3:
				return CONFIG_PREFIX + "vip/integralConfigVipAdd";
			case 4:
				return CONFIG_PREFIX + "source/integralConfigScAdd";
		}
		throw new RuntimeException("请输入类型");
	}
	/**
	 * 页面转发
	 * @param type
	 * @return
	 */
	@RequiresPermissions("sys:config:edit")
	@GetMapping("edit/{integralType}/{id}")
	public String editToPage(@PathVariable("integralType") int type, @PathVariable("id") int id, Model model) {
		IntegralConfigVO vo = service.get(id, type);
		model.addAttribute("config", vo);
		switch (type) {
			case 1:
				return CONFIG_PREFIX + "add/addIntegralConfigEdit";
			case 2:
				return CONFIG_PREFIX + "sub/integralConfigSubEdit";
			case 3:
				return CONFIG_PREFIX + "vip/integralConfigVipEdit";
			case 4:
				return CONFIG_PREFIX + "source/integralConfigScEdit";
		}
		throw new RuntimeException("请输入类型");
	}
	
	@GetMapping("find/{type}")
	@Log("积分配置添加")
	@ResponseBody
	@ApiOperation(value="积分配置查询", notes="积分配置查询")
	public List<ConfigComboxVO> findInegralConfigByType(@PathVariable("type")int type) {
		Map<String, Object> params = new HashMap<>();
		params.put("integralType", type);
		return service.findConfigToPage(params);
	}
	
	@PostMapping("addSave")
	@Log("积分配置添加")
	@ResponseBody
	@RequiresPermissions("sys:config:add")
	@ApiOperation(value="积分配置添加", notes="积分配置添加")
	public MsgResponse addSave(IntegralConfigVO vo) {
		return service.addIntegralConfig(vo);
	}
	
	@PostMapping("updateSave")
	@Log("积分配置修改")
	@ResponseBody
	@RequiresPermissions("sys:config:edit")
	@ApiOperation(value="积分配置修改", notes="积分配置修改")
	public MsgResponse updateSave(IntegralConfigVO vo) {
		return service.updateIntegralConfig(vo);
	}
	
	@PostMapping("batchRemove")
	@Log("积分配置批量删除")
	@ResponseBody
	@RequiresPermissions("sys:config:batchRemove")
	@ApiOperation(value="积分配置批量删除", notes="积分配置批量删除")
	public MsgResponse batchRemove(@RequestParam("ids[]") List<Integer> ids, int type) {
		return service.batchDeleteIntegralConfig(ids, type);
	}
	
	
}
