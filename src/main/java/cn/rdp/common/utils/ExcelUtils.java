package cn.rdp.common.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-7-13
 *@version 1.0.0
 *@desc
 */
public class ExcelUtils {

	/**
	 * 创建一个单元格，
	 */
	public static void createCellByStringValue(Row row, CellStyle style, int column, String value){
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}
	
	public static void batchCreateCell(Sheet sheet, String[] contents, int[] rowIndexs, CellStyle style){
		int len = rowIndexs.length;
		for(int i = 0; i < len; i++){
			Cell cell = sheet.createRow(rowIndexs[i]).createCell(0);
			cell.setCellValue(contents[i]);
			cell.setCellStyle(style);
		}
	}
	public static void batchCreateCell(Row row, String[] contents){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
		}
	}
	public static void batchCreateCell(Row row, String[] contents, CellStyle style){
		int len = contents.length;
		for(int i = 0; i < len; i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(contents[i]);
			cell.setCellStyle(style);
		}
	}
	
	/**
	 * 合并单元格
	 * @param sheet 
	 * @param ranges firstRow, lastRow, firstCol, lastCol
	 */
	public static void CellRangeAddress(Sheet sheet, int[] ranges){
		//firstRow, lastRow, firstCol, lastCol
		sheet.addMergedRegion(new CellRangeAddress(ranges[0], ranges[1], ranges[2], ranges[3]));
	}
	/**
	 * 合并单元格
	 * @param sheet
	 * @param ranges
	 */
	public static void setRowHeght(Row row, short rowHeight){
		row.setHeight(rowHeight);
	}
	
}
