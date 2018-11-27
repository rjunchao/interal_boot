package cn.rdp.integral.utils;

import cn.rdp.common.utils.StringUtils;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月21日 下午5:21:17
*   desc: 
*/
public class IntegralUtils {

	/**
	 * 身份证只显示年月日
	 * @param idcard
	 * @return
	 */
	public static String idcardToX(String idcard){
		if(StringUtils.isNotEmpty(idcard) && idcard.length() >= 18) {
			String tempidcard = idcard.substring(6, 14);
			return "**"+ tempidcard+ "**";
		}else {
			return "**********";
		}
		
	}
}
