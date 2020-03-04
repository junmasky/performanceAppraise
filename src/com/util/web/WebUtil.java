package com.util.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

import com.util.string.StringUtil;

/**
 * 
 * 页面操作工具类
 * 
 */
public class WebUtil {
	
	/**
	 * 
	 * 替换HTML标签
	 */
	public static String replaceHTML(String html){
		return html.replaceAll("<\\/?[^>]*>","");
	}	
	/**
	 * 
	 * 获取请求所有参数及参数值
	 */
	public static Map<String,String> requestParams(HttpServletRequest request){
		Map<String,String> requestParams=new HashMap<String,String>();
		try{
			Map  map=request.getParameterMap();
			Set keySet=map.keySet();
			for(Object obj:keySet){
				if(map.get(obj).getClass().isArray()){
					Object[] param=(Object[]) map.get(obj);
					requestParams.put(obj.toString(), StringUtil.showNull(param[0]));	
				}else{
					requestParams.put(obj.toString(), StringUtil.showNull(map.get(obj)));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return requestParams;
	}
	
	/**
	 * 响应输出流
	 * @param response
	 * @return
	 */
	public static PrintWriter getPrintWriter(HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * 输出Html 不默认缓存
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void writeHtml(HttpServletResponse response,Object obj ) {
		writeHtml(response,String.valueOf(obj),false);
	}
	
	/**
	 * 输出Html 
	 * 
	 * @param str
	 * @param response
	 * @param isCache 是否缓存
	 */
	public static void writeHtml(HttpServletResponse response,Object obj,boolean isCache) {
		Assert.notNull(response, "response must not be null");
		response.setContentType("text/html;charset=utf-8");
			
		if(isCache){
			setResponseCache(response);
		}
		
		PrintWriter out = getPrintWriter(response);
		out.write(String.valueOf(obj));
		out.flush();
		out.close();
	}
	
	/**
	 * 设置缓存
	 * @param response
	 * @param isCache
	 */
	private static void setResponseCache(HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 输出Json
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response, Object obj,  boolean isCache) {
		Assert.notNull(response, "response must not be null");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		
		if(isCache){
			setResponseCache(response);
		}

		PrintWriter out = getPrintWriter(response);
		out.write(String.valueOf(obj));
		out.flush();
		out.close();
	}
	
	/**
	 * 输出Json 默认不缓存
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response, Object obj) {
		writeJson(response, String.valueOf(obj), false);
	}
	
	/**
	 * 输出Json
	 * 
	 */	
	public static void writeObj2Json(HttpServletResponse response,Object obj) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONArray jsonArray = JSONArray.fromObject(obj);  
		getPrintWriter(response).write(jsonArray.toString());
		
	}
	/**
	 * 输出Json
	 * 
	 */	
	public static void writeJsonObject(HttpServletResponse response,Object obj) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		PrintWriter out = getPrintWriter(response);
		out.write(String.valueOf(JSONObject.fromObject(obj)));
		out.flush();
		out.close();
		
	}
	
	/**
	 * 根据name获得session属性
	 * @param request
	 * @param name
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		HttpSession session = request.getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}
	
	/**
	 * 设置session属性
	 * @param request
	 * @param name
	 * @return
	 */
	public static void setSessionAttribute(HttpServletRequest request, String name,Object value) {
		Assert.notNull(request, "Request must not be null");
		HttpSession session = request.getSession(false);
		session.setAttribute(name, value);
	}
	
	/**
	 * 处理页面请求参数
	 * 
	 * @param str
	 * @throws IOException
	 */		
	public static <T> T handleRequestParams(Class<T> entityClass,HttpServletRequest request){
		try {
			T entity = entityClass.newInstance();
			//把请求中的参数取出
			Map allParams = request.getParameterMap();
			Set entries = allParams.entrySet();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String)entry.getKey();
				String[] value = (String[])entry.getValue();
				if(value != null){
					if(value.length == 1){
						BeanUtils.copyProperty(entity, name, value[0]);
					}else{
						BeanUtils.copyProperty(entity, name, value);
					}
				}
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void downloadFile(HttpServletResponse response, String absolutePath) {
		File file = new File(absolutePath);			
		String filename = file.getName();

		
		try {
			// 以流的形式下载文件。
			InputStream fis;
			fis = new BufferedInputStream(new FileInputStream(absolutePath));
			
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"ISO8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
