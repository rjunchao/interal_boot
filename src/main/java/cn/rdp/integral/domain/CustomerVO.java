package cn.rdp.integral.domain;

import cn.rdp.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:57:00
*   desc: 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerVO extends BaseDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pkCustomerInfo;//主键
	private String customerName;//客户名称
	private String customerIdcard;//身份证，唯一
	private String customerPhone;//手机号码
	private String recommendPhone;//推荐人手机号
	private double nowUsableIntegral;//当前可用积分
	private String inputOrg;//建档机构，登录用户机构
	
	private String orgname;//机构名称
	private String realIdcard;
	private String remark;//备注
	
	@Override
	public String getPkName() {
		return "pk_customer_info";
	}
	@Override
	public String getTableName() {
		return "gd_customer_info";
	}
}
