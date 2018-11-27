package cn.rdp.integral.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月23日 下午2:58:15
*   desc: 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class VipIntegralDetailVO extends IntegralDetailBaseVO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String giveYear;//赠送年
	private String giveType;//赠送类型
	private double giveIntegral;//类型积分
	
	@Override
	public String getPkName() {
		return "pk_integral_detail";
	}

	@Override
	public String getTableName() {
		return "gd_vip_integral_detail";
	}

	public VipIntegralDetailVO() {
		super();
	}
	

}
