package com.neaos.utils;

/**
 * Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一
 * Base64编码可用于在HTTP环境下传递较长的标识信息
 *
 * Base64要求把每三个8Bit的字节转换为四个6Bit的字节（3*8 = 4*6 = 24），
 * 然后把6Bit再添两位高位0，组成四个8Bit的字节，也就是说，转换后的字符串理论上将要比原来的长1/3
 */

public class Base64 {
	
    /**
     * data[]进行编码
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
      return java.util.Base64.getEncoder().encodeToString(data);
    }

   

    /**
     * Decodes the given Base64 encoded String to a new byte array. The byte
     * array holding the decoded data is returned.
     */

    public static byte[] decode(String s) {

    	return java.util.Base64.getDecoder().decode(s);
    }

    
}
