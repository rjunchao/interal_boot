package cn.rdp.integral.report.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rdp.common.annotation.Log;
import cn.rdp.common.domain.ExportFile;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.Query;
import cn.rdp.common.utils.ResponseUtil;
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
	@RequiresPermissions("integral:detail:query")
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
	/**
	 * 页面跳转
	 * @param type
	 * @return
	 */
	@Log("积分明细下载")
	@ResponseBody
	@RequiresPermissions("integral:detail:export")
	@GetMapping("export/{hiddenFlag}/{type}")
	public void toIntegralDetailPage(@RequestParam Map<String, Object> params, 
			@PathVariable(name = "hiddenFlag", required=true)int hiddenFlag,
			@PathVariable(name = "type", required=true) int type, HttpServletResponse resp) {
		ExportFile ef = service.exportIntegralDetail(params, type, hiddenFlag);
		try {
			ef.setSufix(".xlsx");
			ResponseUtil.download(resp, ef);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@ResponseBody
	@RequiresPermissions("integral:detail:query")
	@ApiOperation(value="查询添加积分明细", notes="查询添加积分明细")
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
	@RequiresPermissions("integral:detail:query")
	@ApiOperation(value="查询积分兑换明细", notes="查询积分兑换明细")
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
	@ApiOperation(value="vip积分赠送明细", notes="vip积分赠送明细")
	@PostMapping("/vip/list/{hiddenFlag}")
	@RequiresPermissions("integral:detail:query")
	public PageUtils queryVipIntegralDetail(@RequestParam Map<String, Object> params, 
			@PathVariable(name = "hiddenFlag", required=true) int hiddenFlag) {
		log.info("查询vip积分明细");
		Query query = new Query(params);
		query.put("isFuzzyQuery", hiddenFlag);
		query.put("table_name", "gd_vip_integral_detail");
		return service.findVipIntegralDetail(query);
	}

	
}
