package cn.rdp.integral.domain;

import cn.rdp.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月15日 上午10:47:14
*   desc: 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class IntegralConfigVO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pkGdIntegralConfig;//主键
	private String integralTypeName;//类型的名称
	private long integral;
	private String integralType;//1：添加积分，2：减少积分, 3:vip赠送，4：资金来源
	private double integralCoefficient;//积分系数，积分
	private String integralYear;//年份，送积分的
	@Override
	public String getPkName() {
		return "pk_gd_integral_config";
	}
	@Override
	public String getTableName() {
		return "gd_integral_config";
	}
}
