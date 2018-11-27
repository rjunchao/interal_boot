package cn.rdp.system.service;

/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年10月27日 下午7:50:00
*   desc: 
*/

public interface IPrimaryKeyService {

	/**
	 * 生成主键
	 * @param params
	 * @return
	 */
	int generatePrimaryKey(String table_name);
	
}
