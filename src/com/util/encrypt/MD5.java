package com.util.encrypt;

import java.security.MessageDigest;

/**
 *
 * <p>Title: 不可逆加密算法</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MD5 {
  public MD5() {
  }

  /**
   * 不可逆加密函数
   * @param s  ：待加密字符串,
   * @return  ：  加密后字符串，如果待加密字符串为null，则返回null
   */
  public final static String getMD5String(String s) {
    char hexDigits[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f'};
    try {
      byte[] strTemp = s.getBytes();
      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
      mdTemp.update(strTemp);
      byte[] md = mdTemp.digest();
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    }
    catch (Exception e) {
      return null;
    }
  }
}



