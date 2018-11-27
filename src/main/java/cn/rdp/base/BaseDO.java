package cn.rdp.base;

import java.io.Serializable;

import lombok.Data;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:47:08
*   desc: 
*/
@Data
public abstract class BaseDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String creater;//创建人
	private String createTime;//创建时间
	private String modifier;//修改人
	private String modifiedTime;//修改时间
	private String dr;//删除标志
	private String ts;//时间戳
	private long createOrg;//创建机构
	//预留字段
	private String def1;//
	private String def2;// 
	private String def3;// 
	private String def4;// 
	private String def5;// 
	private String def6;// 
	private String def7;// 
	private String def8;// 
	private String def9;// 
	private String def10;//
	
	private String deptName;//部门名称
	private String createName;//创建人名称
	
	public abstract String getPkName();
	public abstract String getTableName();
	
}
