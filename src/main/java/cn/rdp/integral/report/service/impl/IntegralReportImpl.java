package cn.rdp.integral.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import cn.rdp.common.domain.ExportFile;
import cn.rdp.common.utils.DateUtils;
import cn.rdp.common.utils.ExcelUtils;
import cn.rdp.common.utils.PageUtils;
import cn.rdp.integral.domain.AddIntegralDetailVO;
import cn.rdp.integral.domain.SubIntegralDetailVO;
import cn.rdp.integral.domain.VipIntegralDetailVO;
import cn.rdp.integral.handle.mapper.IntegralHanleMapper;
import cn.rdp.integral.report.service.IntegralReportService;
import cn.rdp.integral.utils.IntegralUtils;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:38:31
*   desc: 
*/
@Service
public class IntegralReportImpl implements IntegralReportService{
	
	@Autowired
	private IntegralHanleMapper mapper;

	@Override
	public PageUtils findVipIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<VipIntegralDetailVO>(), 0);
		}
		List<VipIntegralDetailVO> vos = mapper.findVipIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(VipIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	@Override
	public PageUtils findAddIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<AddIntegralDetailVO>(), 0);
		}
		List<AddIntegralDetailVO> vos = mapper.findAddIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(AddIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	@Override
	public PageUtils findSubIntegralDetail(Map<String, Object> params) {
		int total = mapper.count(params);
		if(total <= 0) {
			return new PageUtils(new ArrayList<SubIntegralDetailVO>(), 0);
		}
		List<SubIntegralDetailVO> vos = mapper.findSubIntegralDetailPage(params);
		if("1".equals(params.get("isFuzzyQuery").toString())) {
			if(vos != null && vos.size() > 0) {
				for(SubIntegralDetailVO vo : vos){
					vo.setCustomerIdcard(IntegralUtils.idcardToX(vo.getCustomerIdcard()));
				}
			}
		}
		PageUtils page = new PageUtils(vos, total);
		return page;
	}

	/**
	 * 导出报表明细
	 * 
	 */
	@Override
	public ExportFile exportIntegralDetail(Map<String, Object> params, int type, int hiddenFlag) {
		ExportFile ef = new ExportFile();
		Workbook wb = null;
		//
		String fileName = "";
		
		if(1 == type){
			fileName = "积分添加明细";
			wb = bulidExportAddIntegralDetail(params, fileName, hiddenFlag);
		}else if(2 == type){
			fileName= "积分兑换明细";
			wb = bulidExportSubIntegralDetail(params, fileName, hiddenFlag);
		}else if(3 == type){
			fileName= "积分赠送明细";
			wb = bulidExportVipIntegralDetail(params, fileName, hiddenFlag);
		}
		ef.setWb(wb);
		ef.setFileName(fileName + DateUtils.format("yyyyMMddHHmmss"));
		return ef;
	}
	
	public Workbook bulidBaseWorkbook(String fileName) {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(fileName);
		Row row = sheet.createRow(0);
		row.setHeightInPoints(18);
		Cell cell = row.createCell(0);
		//第一行
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)14);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);
		cell.setCellValue(fileName);
		
		cell.setCellStyle(style);
		return wb;
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

	private Workbook bulidExportVipIntegralDetail(Map<String, Object> params, String fileName, int hiddenFlag) {
		Workbook wb = bulidBaseWorkbook(fileName);
		Sheet sheet = wb.getSheetAt(0);
		//合并表头
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		//設置列寬
		setCellWidth(sheet, 3);
		String[] titles = {"客户名称", "客户身份证号", "积分", "vip赠送积分", 
				"vip积分赠送类型", "vip积分赠送年", "备注", "录入时间", "录入人", "录入机构"};
		//标题
		bulidTitleWorkbook(wb, titles);
		//查询数据
		List<VipIntegralDetailVO> vos = mapper.findVipIntegralDetailPage(params);
		if(vos != null && vos.size() >= 0){
			String[] contents = null;
			VipIntegralDetailVO vo = null;
			Row row = null;
			if(1 == hiddenFlag) {
				if(vos != null && vos.size() > 0) {
					for(VipIntegralDetailVO v : vos){
						v.setCustomerIdcard(IntegralUtils.idcardToX(v.getCustomerIdcard()));
					}
				}
			}
			for(int i = 0, len = vos.size(); i < len; i++){
				vo = vos.get(i);
				String customer_name = vo.getCustomerName();
				String idcard = vo.getCustomerIdcard();
				double integral = vo.getCustomerIntegral();
				String giveIntegral = vo.getGiveIntegral() + "";//计算系数（添加）、积分单位（赠送和兑换）
				String giveType = vo.getGiveType();//积分操作详情类型
				String giveYear = vo.getGiveYear();//积分赠送年份
				String empname = vo.getCreateName();
				String orgname = vo.getDeptName();
				String ts = vo.getTs();
				contents = new String[]{customer_name, idcard, integral+"", 
						giveType, giveIntegral, giveYear,vo.getRemark(),
						ts, empname, orgname};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		return wb;
	}

	private Workbook bulidExportSubIntegralDetail(Map<String, Object> params, String fileName, int hiddenFlag) {
		Workbook wb = bulidBaseWorkbook(fileName);
		Sheet sheet = wb.getSheetAt(0);
		//合并表头
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		//設置列寬
		setCellWidth(sheet, 2);
		String[] titles = {"年份","序号","客户名称", "客户身份证号", "积分", "兑换类型积分", 
				"兑换数量", "兑换商品", "备注", "录入时间", "录入人", "录入机构"};
		//标题
		bulidTitleWorkbook(wb, titles);
		//查询数据
		List<SubIntegralDetailVO> vos = mapper.findSubIntegralDetailPage(params);
		if(vos != null && vos.size() >= 0){
			String[] contents = null;
			SubIntegralDetailVO vo = null;
			Row row = null;
			if(1 == hiddenFlag) {
				if(vos != null && vos.size() > 0) {
					for(SubIntegralDetailVO v : vos){
						v.setCustomerIdcard(IntegralUtils.idcardToX(v.getCustomerIdcard()));
					}
				}
			}
			for(int i = 0, len = vos.size(); i < len; i++){
				vo = vos.get(i);
				String customer_name = vo.getCustomerName();
				String idcard = vo.getCustomerIdcard();
				double integral = vo.getCustomerIntegral();
				String empname = vo.getCreateName();
				String orgname = vo.getDeptName();
				String ts = vo.getTs();
				String subYear = vo.getSubYear();
				int subNum = vo.getSubNum();
				int subTypeIntegral = vo.getSubTypeIntegral();
				int subTotal = vo.getSubTotal();
				String subProd = vo.getSubProd();
				contents = new String[]{
						subYear, subNum+"",
						customer_name, idcard, integral+"", 
						subTypeIntegral+"", subTotal+"", subProd,vo.getRemark(),
						ts, empname, orgname};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		return wb;
	}

	private Workbook bulidExportAddIntegralDetail(Map<String, Object> params, String fileName, int hiddenFlag) {
		Workbook wb = bulidBaseWorkbook(fileName);
		Sheet sheet = wb.getSheetAt(0);
		//合并表头
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 23));
		//設置列寬
		setCellWidth(sheet, 1);
		String[] titles = {"客户名称", "客户身份证号", "积分", "账号", "存单号","存单金额",
				"营销人1", "营销人1金额", 
				"营销人2", "营销人2金额", 
				"营销人3", "营销人3金额", 
				"资金来源1", "资金来源金额1",
				"资金来源2", "资金来源金额2",
				"资金来源3", "资金来源金额3",
				"添加积分类型", "积分计算系数", "录入时间", "录入人", "录入机构", "备注"};
		//标题
		bulidTitleWorkbook(wb, titles);
		//查询数据
		List<AddIntegralDetailVO> vos = mapper.findAddIntegralDetailPage(params);
		if(vos != null && vos.size() >= 0){
			String[] contents = null;
			AddIntegralDetailVO vo = null;
			Row row = null;
			if(1 == hiddenFlag) {
				if(vos != null && vos.size() > 0) {
					for(AddIntegralDetailVO v : vos){
						v.setCustomerIdcard(IntegralUtils.idcardToX(v.getCustomerIdcard()));
					}
				}
			}
			for(int i = 0, len = vos.size(); i < len; i++){
				vo = vos.get(i);
				String customer_name = vo.getCustomerName();
				String idcard = vo.getCustomerIdcard();
				double integral = vo.getCustomerIntegral();
				String empname = vo.getCreateName();
				String orgname = vo.getDeptName();
				String ts = vo.getTs();
				String account = vo.getCustomerAccount();//账号
				double depositReceiptAmt = vo.getDepositReceiptAmt();//存单金额
				String depositReceiptNum = vo.getDepositReceiptNum();//存单号
				contents = new String[]{customer_name, idcard, integral+"", account, depositReceiptNum, depositReceiptAmt + "",
						vo.getMarketingPeople1(), vo.getMarketingPeopleAmt1() + "", 
						vo.getMarketingPeople2(), vo.getMarketingPeopleAmt2() + "", 
						vo.getMarketingPeople3(), vo.getMarketingPeopleAmt3() + "", 
						vo.getCapitalSource1(), vo.getCapitalSourceAmt1() + "",
						vo.getCapitalSource2(), vo.getCapitalSourceAmt2() + "",
						vo.getCapitalSource3(), vo.getCapitalSourceAmt3() + "",
						vo.getConfigTypeName(), vo.getIntegralCalcNum(), ts, empname, orgname, vo.getRemark()};
				row = sheet.createRow((i+2));
				ExcelUtils.batchCreateCell(row, contents);
			}
		}
		return wb;
	}
	
	private void setCellWidth(Sheet sheet, int type) {
		sheet.setColumnWidth(1, 60*100);
		if(1 == type){
			for(int i = 1; i < 23; i++){
				sheet.setColumnWidth(i, 53*100);
			}
		}if(2 == type){
			for(int i = 2; i < 11; i++){
				sheet.setColumnWidth(i, 35*100);
			}
			sheet.setColumnWidth(1, 35*100);
			sheet.setColumnWidth(3, 45*130);
			sheet.setColumnWidth(8, 55*100);
			sheet.setColumnWidth(10, 75*100);
		}else{
			for(int i = 2; i < 9; i++){
				sheet.setColumnWidth(i, 35*100);
			}
			sheet.setColumnWidth(6, 55*100);
			sheet.setColumnWidth(8, 55*100);
		}
		
	}

	

}
