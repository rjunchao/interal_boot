package cn.rdp.integral.report.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rdp.common.annotation.Log;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.Query;
import cn.rdp.integral.report.service.IntegralReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:38:45
*   desc: 积分明细查询
*/
@Slf4j
@Api(value="积分查询", tags="IntegralReportController")
@Controller
@RequestMapping("/integral/report")
public class IntegralReportController {
	private static final String PREFIX = "/integral/report/";
	
	@Autowired
	private IntegralReportService service;
	
	/**
	 * 页面跳转
	 * @param type
	 * @return
	 */
	@Log("页面跳转")
	@GetMapping("index/{type}")
	public String toIntegralDetailPage(@PathVariable(name = "type", required=true) int type) {
		switch (type) {
		case 1:
			return PREFIX + "addIntegralDetail";
		case 2:
			return PREFIX + "subIntegralDetail";
		case 3:
			return PREFIX + "vipIntegralDetail";
		}
		return PREFIX;
	}
	
	
	@ResponseBody
	@ApiOperation(value="添加客户信息", notes="添加客户信息")
	@PostMapping("/add/list/{hiddenFlag}")
	public PageUtils queryAddIntegralDetail(@RequestParam Map<String, Object> params, 
			@PathVariable(name = "hiddenFlag", required=true) int hiddenFlag) {
		log.info("查询添加积分明细");
		Query query = new Query(params);
		query.put("isFuzzyQuery", hiddenFlag);
		query.put("table_name", "gd_add_integral_detail");
		return service.findAddIntegralDetail(query);
	}
	
	
	@ResponseBody
	@ApiOperation(value="添加客户信息", notes="添加客户信息")
	@PostMapping("/sub/list/{hiddenFlag}")
	public PageUtils querySubIntegralDetail(@RequestParam Map<String, Object> params, 
			@PathVariable(name = "hiddenFlag", required=true) int hiddenFlag) {
		log.info("查询积分兑换明细");
		Query query = new Query(params);
		query.put("isFuzzyQuery", hiddenFlag);
		query.put("table_name", "gd_sub_integral_detail");
		return service.findSubIntegralDetail(query);
	}
	
	@ResponseBody
	@ApiOperation(value="添加客户信息", notes="添加客户信息")
	@PostMapping("/vip/list/{hiddenFlag}")
	public PageUtils queryVipIntegralDetail(@RequestParam Map<String, Object> params, 
			@PathVariable(name = "hiddenFlag", required=true) int hiddenFlag) {
		log.info("查询vip积分明细");
		Query query = new Query(params);
		query.put("isFuzzyQuery", hiddenFlag);
		query.put("table_name", "gd_vip_integral_detail");
		return service.findVipIntegralDetail(query);
	}

	
}
