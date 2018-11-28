package cn.rdp.common.domain;

import org.apache.poi.ss.usermodel.Workbook;

import lombok.Data;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月28日 上午10:25:04
*   desc: 
*/
@Data
public class ExportFile {

	private String fileName;
	private String title;
	private Workbook wb;
	private String  sufix;
}
