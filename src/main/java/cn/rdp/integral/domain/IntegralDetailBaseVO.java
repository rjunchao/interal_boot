package cn.rdp.integral.domain;

import cn.rdp.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月23日 下午2:56:58
*   desc: 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class IntegralDetailBaseVO extends BaseDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int pkIntegralDetail;
	private int configId;//类型主键
	private String customerIdcard;//客户身份证号，根据客户得到
	private double customerIntegral;//客户积分
	
	private String inputDate;//录入时间
	private String optionOrg;//操作机构
	private String realIdcard;
	private String customerName;
	private String remark;//备注
	
}
