package cn.rdp.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* auther:rjc
* emali:rjunchao@126.com
* date:2018年8月26日
* version:1.0.0
* desc:
*/
public class PropertiesUtils {
	private static Properties prop = null;
	private static InputStream is = null;
	static {
		try {
		
			is = new FileInputStream("rdp.properties");
			prop = new Properties();
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(String key){
	//	InputStream is = PropertiesUtils.class.getResourceAsStream("rdp.properties");
		return prop.getProperty(key);
	}
}
