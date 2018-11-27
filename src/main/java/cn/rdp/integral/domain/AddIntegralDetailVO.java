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
public class AddIntegralDetailVO extends IntegralDetailBaseVO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerAccount;//客户账号
	private String depositReceiptNum;//存单号
	private double depositReceiptAmt;//存单金额
	private String marketingPeople1;//营销人1
	private String marketingPeople2;//营销人2
	private String marketingPeople3;//营销人3
	private double marketingPeopleAmt1;//营销人1营销金额
	private double marketingPeopleAmt2;//营销人2营销金额
	private double marketingPeopleAmt3;//营销人3营销金额

	private String capitalSource1;//资金来源1
	private String capitalSource2;//资金来源2
	private String capitalSource3;//资金来源3
	private double capitalSourceAmt1;//资金来源1
	private double capitalSourceAmt2;//资金来源2
	private double capitalSourceAmt3;//资金来源3
	private String duration;//存续期
	
	private String accountNumber;//账号序号
	private String configTypeName;//添加积分类型
	private String integralCalcNum;//添加积分类型计算系数
	
	@Override
	public String getPkName() {
		return "pk_integral_detail";
	}

	@Override
	public String getTableName() {
		return "gd_add_integral_detail";
	}

	public AddIntegralDetailVO() {
		super();
	}

}
