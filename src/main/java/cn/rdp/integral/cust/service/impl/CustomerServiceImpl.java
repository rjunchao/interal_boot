package cn.rdp.integral.cust.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rdp.common.MsgResponse;
import cn.rdp.common.domain.ExportFile;
import cn.rdp.common.utils.DateUtils;
import cn.rdp.common.utils.ExcelUtils;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.common.utils.Query;
import cn.rdp.common.utils.ShiroUtils;
import cn.rdp.common.utils.StringUtils;
import cn.rdp.integral.cust.mapper.CustomerMapper;
import cn.rdp.integral.cust.service.CustomerService;
import cn.rdp.integral.domain.CustomerVO;
import cn.rdp.integral.utils.IntegralUtils;
import cn.rdp.system.domain.UserDO;
import cn.rdp.system.service.impl.PrimaryKeyServiceImpl;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月21日 下午4:15:51
*   desc: 
*/
@Service
@Transactional(readOnly = false,rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerMapper mapper;
	
	@Autowired
	private PrimaryKeyServiceImpl keyService;
	
//	@Cacheable(cacheNames = {"custList"}, cacheManager="doCacheManager")
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	@Override
	public PageUtils findCustomer(Map<String, Object> params) {
		Query query = new Query(params);
		int total = mapper.count(query);
		if(total <= 0) {
			return new PageUtils(new ArrayList<CustomerVO>(), 0);
		}
		List<CustomerVO> vos = mapper.findCustomer(query);
		if(1 == Integer.parseInt(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(CustomerVO vo : vos){
//					vo.setRealIdcard(vo.getCustomerIdcard());
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}



//	@CachePut(key = "#e.pkCustomerInfo")
	@Override
	public MsgResponse updateCustomer(CustomerVO vo, int hiddenFlag) {
		try {
			//判断身份证是否修改了
			Map<String, Object> params = new HashMap<>();
			params.put("pkCustomerInfo", vo.getPkCustomerInfo());
			CustomerVO cust = mapper.get(params);
			if(1 == hiddenFlag) {
				String id = IntegralUtils.idcardToX(cust.getCustomerIdcard());
				if(id.equals(vo.getCustomerIdcard())) {
					//身份证没有修改,还原身份证去校验，修改了，就用修改后的身份证去校验
					vo.setCustomerIdcard(vo.getRealIdcard());
				}
			}
			//校验身份证、手机号不能重复
			MsgResponse msg = validateCustomer(vo, 2);
			if(msg != null) {
				return msg;
			}
			
			vo.setModifier(ShiroUtils.getUser().getUsername());
			int count = mapper.updateCustomer(vo);
			if(count > 0) {
				return new MsgResponse(true, "客户修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "客户修改失败， " + e.getMessage());
		}
		return new MsgResponse(false, "客户修改失败");
	}

//	@CachePut
	@Override
	public MsgResponse addCustomer(CustomerVO vo) {
		try {
			//校验身份证、手机号不能重复
			MsgResponse msg = validateCustomer(vo, 1);
			if(msg != null) {
				return msg;
			}
			UserDO user = ShiroUtils.getUser();
			vo.setPkCustomerInfo(keyService.generatePrimaryKey("gd_customer_info"));
			vo.setCreater(user.getUsername());
			vo.setCreateOrg(user.getDeptId());
			int count = mapper.addCustomer(vo);
			if(count > 0) {
				return new MsgResponse(true, "客户添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "客户添加失败， " + e.getMessage());
		}
		return new MsgResponse(false, "客户添加失败");
	}

//	@Cacheable(cacheNames = {"cust"}, cacheManager="doCacheManager")
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	@Override
	public CustomerVO get(Map<String, Object> params) {
		return mapper.get(params);
	}

//	@CacheEvict(key = "#e.pkCustomerInfo")
	@Override
	public MsgResponse deleteCustomer(Map<String, Object> params) {
		if(params.isEmpty()) {
			throw new RuntimeException("请选择条件进行删除");
		}
		try {
			int count = mapper.deleteCustomer(params );
			if(count > 0) {
				return new MsgResponse(true, "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MsgResponse(false, "删除失败， " + e.getMessage());
		}
		return new MsgResponse(false, "删除失败");
	}
	
	/**
	 * 校验
	 * @param vo
	 * @param i 1:添加，2
	 */
	private MsgResponse validateCustomer(CustomerVO vo, int type) {
		String customer_idcard = vo.getCustomerIdcard();
		if(StringUtils.isEmpty(customer_idcard)){
			return new MsgResponse("身份证号为空，请填写！", false);
		}
		if(!customer_idcard.startsWith("**") && !customer_idcard.endsWith("**")){
			if(customer_idcard.length() != 18){
				return new MsgResponse("身份证号填写错误，请重新填写！", false);
			}
		}
		if(StringUtils.isNotEmpty(vo.getCustomerPhone()) && vo.getCustomerPhone().trim().length() != 11){
			return new MsgResponse("手机号填写错误，请重新填写！", false);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("customerIdcard", customer_idcard);
		/*if(2 == type){
			params.put("pkCustomerInfo", vo.getPkCustomerInfo());
		}*/
		CustomerVO cust = mapper.get(params);
		if(cust != null && StringUtils.isNotEmpty(cust.getCustomerIdcard())) {
			if(cust.getPkCustomerInfo() == (vo.getPkCustomerInfo())) {
				//是自己
				return null;
			}
			return new MsgResponse("身份证号重复，请重新填写！", false);
		}
		return null;
	}


	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int updateCustomerIntegral(CustomerVO vo) {
		return mapper.updateCustomerIntegral(vo);
	}



	@Override
	public ExportFile exportCustomer(Map<String, Object> params, int hiddenFlag) {
		params.put("isFuzzyQuery", hiddenFlag);
		List<CustomerVO> customers = mapper.findCustomer(params);
		if(customers == null ){
			customers = new ArrayList<>();
		}
		if(1 == Integer.parseInt(params.get("isFuzzyQuery").toString())) {
			if(customers != null && customers.size() > 0) {
				for(CustomerVO vo : customers){
//					vo.setRealIdcard(vo.getCustomerIdcard());
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		//导出
		
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("客户信息表");
		Row row0 = sheet.createRow(0);
		Cell cell = row0.createCell(0);
		//第一行
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		
		cell.setCellValue("客户积分明细表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		cell.setCellStyle(style);
		setCellWidth(sheet);
		String[] titles = {"客户名称", "客户身份证号", "电话", 
					"当前可用积分", "VIP", "录入人", "录入机构" , "录入时间", "备注"};
		//创建表头
		bulidTitleWorkbook(wb, titles);
		Row row = sheet.createRow(1);
//		ExcelUtils.batchCreateCell(row, titles);
		if(customers.size() >= 0){
			String[] contents = null;
			CustomerVO vo = null;
			for(int i = 0, len = customers.size(); i < len; i++){
				vo = customers.get(i);
				contents = new String[]{vo.getCustomerName(), vo.getCustomerIdcard(), vo.getCustomerPhone(),
						vo.getNowUsableIntegral() + "", vo.getDef1(), vo.getCreateName(),
						vo.getOrgname(), vo.getTs(), vo.getRemark() };
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		ExportFile ef = new ExportFile();
		ef.setSufix(".xlsx");
		ef.setFileName("客户信息" + DateUtils.format("yyyyMMddHHmmss"));
		ef.setWb(wb);
		return ef;
	}

	public void bulidTitleWorkbook(Workbook wb, String[] titles) {
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.createRow(1);
		CellStyle style = wb.createCellStyle();
		style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);//前景色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12);//字体
		style.setFont(font);
		row.setHeightInPoints(16);
		ExcelUtils.batchCreateCell(row, titles, style);
	}
	
	private void setCellWidth(Sheet sheet) {
		for(int i = 0; i < 9; i++){
			sheet.setColumnWidth(i, 50*100);
		}
		
	}
}
