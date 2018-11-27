package cn.rdp.system.domain;

import lombok.Data;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年10月27日 下午7:40:28
*   desc: 
*/
@Data
public class PrimaryKeyVO {
	private int id;
	private String tableName;//
	private int initValue;//
	private int stepValue;//
	private String primaryName;//主键名称
	
}
