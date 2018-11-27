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
public class SubIntegralDetailVO extends IntegralDetailBaseVO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subYear;//
  	private int subNum;//
  	private int subTypeIntegral;//
  	private int subTotal;//
  	private String subProd;//
  	private String remark;//
	
	@Override
	public String getPkName() {
		return "pk_integral_detail";
	}


	@Override
	public String getTableName() {
		return "gd_sub_integral_detail";
	}


	public SubIntegralDetailVO() {
		super();
	}

}
