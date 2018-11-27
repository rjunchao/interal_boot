package cn.rdp.integral.config.test;
/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月16日 下午1:41:22
*   desc: 
*/

import org.springframework.scheduling.annotation.Scheduled;

public class ConfigTest {

	@Scheduled(cron="0 1 3 * * ?")
	public void test() {
		
	}
}
