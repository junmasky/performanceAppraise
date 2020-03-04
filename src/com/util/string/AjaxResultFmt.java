package com.util.string;

import java.util.List;

import com.util.json.JsonUtil;

/**
 * ajax返回值格式化
 * 
 */
public class AjaxResultFmt {
	private static final String OKSTR="1";
	private static final String ERRORSTR="-1";
	public static final String ERR_AJAX="{id:'-1',text:'保存失败controller'}";   //调用controller失败时通用返回信息
	public static final String OK_AJAX="{id:'1',text:'操作成功'}";   //调用controller成功时通用返回信息
	
	public static String getFormat(String id,String text) {		
		return "{id:'"+id+"',text:'"+text+"'}";
	}
	
	public static String getFormatOK(String text) {		
		return getFormat(OKSTR,text);
	}
	
	public static String getFormatERROR(String text) {		
		return getFormat(ERRORSTR,text);
	}
	
	public static String getFormatBean(String id,String text, Object object) {
		String rslt="";
		if (object!=null) {
			if (object instanceof String) {
				rslt="{id:'"+id+"',text:'"+text+"',data:"+object+"}";
			} else {
				rslt="{id:'"+id+"',text:'"+text+"',data:"+JsonUtil.bean2json(object)+"}";
			}			
		} else {
			rslt="{id:'"+id+"',text:'"+text+"'}";
		}
		return rslt;
	}
	
	public static String getFormatBeanOK(String text, Object object) {
		
		return getFormatBean(OKSTR,text,object);
	}
	
	public static String getFormatBeanERROR(String text, Object object) {
		
		return getFormatBean(ERRORSTR,text,object);
	}
	
	public static String getFormatList(String id,String text, List<Object> lst) {
		String rslt="";
		if (lst!=null) {
			rslt="{id:'"+id+"',text:'"+text+"',data:"+JsonUtil.list2json(lst)+"}";
		} else {
			rslt="{id:'"+id+"',text:'"+text+"'}";
		}
		return rslt;
	}
	
	public static String getFormatListOK(String text, List<Object> lst) {
		return getFormatBean(OKSTR,text,lst);
	}
	
	public static String getFormatListERROR(String text, List<Object> lst) {
		return getFormatBean(ERRORSTR,text,lst);
	}
}
