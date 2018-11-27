package cn.rdp.integral.handle.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.annotation.Log;
import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;
import cn.rdp.integral.handle.service.IntegralHanleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:37:13
*   desc: 积分处理
*   	积分添加
*   	积分兑换
*   	积分赠送
*/
@Api(value="积分明细数据插入", tags="IntegralHanleController")
@Controller
@RequestMapping("/integral/handle/")
public class IntegralHanleController {

	private static final String PREFIX = "/integral/handle/";
	
	@Autowired
	private IntegralHanleService service;
	
	/*@Autowired
	private IntegralConfigService configService;*/
	
	@Log("页面跳转")
	@ApiOperation(value="页面跳转", notes="页面跳转")
	@GetMapping("index/{type}/{id}")
	public String index(
			@PathVariable(name="type", required=true)int type, 
			@PathVariable(name="id", required=true)int id,
			Model model) {
		//客户主键
		model.addAttribute("pk", id);
		Map<String, Object> params = new HashMap<>();
		switch (type) {
			case 1:
				return PREFIX + "addHandle";
			case 2:
				return PREFIX + "subHandle";
			case 3:
				/**
				 * 查询vip类型
				 */
				params.put("integralType", 3);
				/*List<IntegralConfigVO> configs = configService.find(params);
				model.addAttribute("configs", configs);*/
				return PREFIX + "vipHandle";
		}
		throw new RuntimeException("请输入类型");
	}
	
	@ResponseBody
	@ApiOperation(value="vip 积分赠送处理", notes="vip 积分赠送处理")
	@PostMapping("vipSave")
	@RequiresPermissions("integral:customer:integral:vip")
	public MsgResponse addVipIntegralDetail(VipIntegralDetailVO vo) {
		return service.addVipIntegralDetail(vo);
	}

	@ResponseBody
	@ApiOperation(value="积分添加处理", notes="积分添加处理")
	@PostMapping("addSave")
	@RequiresPermissions("integral:customer:integral:add")
	public MsgResponse addAddIntegralDetail( AddIntegralDetailVO vo) {
		return service.addAddIntegralDetail(vo);
	}

	@ResponseBody
	@ApiOperation(value="积分兑换处理", notes="积分兑换处理")
	@PostMapping("subSave")
	@RequiresPermissions("integral:customer:integral:sub")
	public MsgResponse addSubIntegralDetail(@RequestBody List<SubIntegralDetailVO> vos) {
		return service.addSubIntegralDetail(vos);
	}
}
