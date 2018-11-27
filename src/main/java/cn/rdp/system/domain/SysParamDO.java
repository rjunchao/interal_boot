package cn.rdp.system.domain;

import cn.rdp.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 上午9:45:15
*   desc: 
*/
@Data
@EqualsAndHashCode(callSuper=false)
public class SysParamDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int paramId;//主键
    private String paramCode;//参数编码
    private String paramValue;//参数值
    private String paramDesc;//参数描述
	@Override
	public String getPkName() {
		return "param_id";
	}
	@Override
	public String getTableName() {
		return "sys_params";
	}
}
