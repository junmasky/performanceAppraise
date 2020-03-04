package com.util.string;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.util.log.Log;

/**
 * 字符串处理类
 * 
 * @author chengf
 *
 */
public class StringUtil {

	private static Log log = new Log(StringUtil.class);

	// 该类不能外部实例化
	private StringUtil() {

	}

	/**
	 * 全球唯一ID
	 * 
	 * @return
	 */
	public static synchronized String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 处理null值为空
	 * 
	 * @String 字符串
	 * @return String
	 */
	public static String showNull(String str) {
		if (str == null || "null".equalsIgnoreCase(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 处理null值为空
	 * 
	 * @String 字符串
	 * @return String
	 */
	public static String showNull(Object str) {
		if (str == null || "null".equalsIgnoreCase(str.toString())) {
			return "";
		} else {
			return str.toString();
		}
	}

	/**
	 * 
	 * 获取Clob字段的值
	 * 
	 */
	public static String getClobValue(Clob clob) throws SQLException {
		if (clob != null) {
			String result = clob.getSubString(1L, (int) clob.length());
			return result;
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 获取blob字段的值
	 * 
	 */
	public static String getBlobValue(byte[] blob, String charCode) {
		String value = "";

		if (blob != null) {
			try {
				value = new String(blob, charCode);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}

		return value;
	}

	/**
	 * 
	 * 获取blob字段的值
	 * 
	 */
	public static String getBlobValue(byte[] blob) {
		String value = "";

		if (blob != null) {
			try {
				value = new String(blob);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}

		return value;
	}
	
	/**
	 * String类型的时间转成Timestamp类型
	 * @param time
	 * @return
	 */
	public static Timestamp getTimestamp(String time) {

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(time);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ts;
	}
}
