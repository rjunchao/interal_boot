package cn.rdp.common.utils;

import java.util.Base64;
import java.util.Base64.Decoder;

public class Base64Utils {
	
	/**
	 * base 64 加密
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(str.getBytes());
		return new String(decode);
	}
}
