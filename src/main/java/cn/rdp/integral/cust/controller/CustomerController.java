package cn.rdp.integral.cust.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import cn.rdp.common.domain.ExportFile;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.ResponseUtil;
import cn.rdp.integral.cust.service.CustomerService;
import cn.rdp.integral.domain.CustomerVO;
import cn.rdp.integral.utils.IntegralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月21日 下午4:14:50
*   desc: 客户管理
*/
@Slf4j
@Api(value="客户管理", tags="CustomerController")
@Controller
@RequestMapping("/integral/customer")
public class CustomerController extends BaseController{

	private static final String CUST_PREFIX = "/integral/customer/";
		
	@Autowired
	private CustomerService custService;
	
	/**
	 * 首页
	 * @return
	 */
	@GetMapping()
	public String index() {
		return CUST_PREFIX + "customerList_easyui";
//		return CUST_PREFIX + "customerList";
	}
	
	
	@ResponseBody
	@RequiresPermissions("integral:customer:query")
	@PostMapping("/list/{hiddenFlag}")
//	@ApiOperation(value="客户信息查询", notes="客户信息查询")
	public PageUtils customerPage(@PathVariable(name="hiddenFlag", required=false) int hiddenFlag, 
			@RequestParam Map<String, Object> params) {
		log.info("客户信息查询");
		params.put("isFuzzyQuery", hiddenFlag);
		return custService.findCustomer(params);
	}
	
	@RequiresPermissions("integral:customer:add")
	@GetMapping("add")
	@ApiOperation(value="添加客户信息", notes="添加客户信息")
	public String toAddPage() {
		return CUST_PREFIX + "addCustomer";
	}
	
	@ResponseBody
	@RequiresPermissions("integral:customer:add")
	@PostMapping("addSave")
	public MsgResponse addSave(CustomerVO vo) {
		return custService.addCustomer(vo);
	}
	
	@RequiresPermissions("integral:customer:edit")
	@GetMapping("edit/{id}/{hiddenFlag}")
	@ApiOperation(value="修改客户信息", notes="修改客户信息")
	public String toEditPage(@PathVariable(name = "id", required=true)int id, 
			Model model, @PathVariable(name = "hiddenFlag", required=true)int hiddenFlag) {
		Map<String, Object> params = new HashMap<>();
		params.put("pkCustomerInfo", id);
		CustomerVO cust = custService.get(params);
		if(1 == hiddenFlag) {
			//身份证需要处理
			cust.setCustomerIdcard(IntegralUtils.idcardToX(cust.getCustomerIdcard()));
		}
		model.addAttribute("cust", cust);
		model.addAttribute("hiddenFlag", hiddenFlag);
		return CUST_PREFIX + "editCustomer";
	}
	
	@ResponseBody
	@RequiresPermissions("integral:customer:edit")
	@PostMapping("editSave/{hiddenFlag}")
	public MsgResponse editSave(CustomerVO vo, @PathVariable(name = "hiddenFlag", required=true)int hiddenFlag) {
		return custService.updateCustomer(vo, hiddenFlag);
	}


	
	
	@ResponseBody
	@RequiresPermissions("integral:customer:remove")
	@PostMapping("delCust/{id}")
	public MsgResponse delCust( @PathVariable(name = "id", required=true)int id) {
		Map<String, Object> params = new HashMap<>();
		params.put("pkCustomerInfo", id);
		return custService.deleteCustomer(params);
	}
	
	@Log("导出客户信息")
	@GetMapping("export/{hiddenFlag}")
	public @ResponseBody String export(@RequestParam Map<String, Object> params, HttpServletResponse resp
			,@PathVariable(name = "hiddenFlag", required=true)int hiddenFlag) {
		ExportFile ef = custService.exportCustomer(params, hiddenFlag);
		try {
			ef.setSufix(".xlsx");
			ResponseUtil.download(resp, ef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
}
