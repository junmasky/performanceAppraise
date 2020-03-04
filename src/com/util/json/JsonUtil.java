package com.util.json;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.util.string.StringUtil;
/**
 * 
 * JSON工具类
 * 
 * @author Administrator
 *
 */
public class JsonUtil {
	
	private static Log log = LogFactory.getLog(JsonUtil.class);  
	/**
	 * 
	 * Object转Json
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2json(Object obj) {  
         StringBuilder json = new StringBuilder();  
         if (obj == null) {  
           json.append("\"\"");  
         } else if (obj instanceof String || obj instanceof Integer || obj instanceof Float  
             || obj instanceof Boolean || obj instanceof Short || obj instanceof Double  
             || obj instanceof Long || obj instanceof BigDecimal || obj instanceof BigInteger  
             || obj instanceof Byte) {  
           json.append("\"").append(string2json(obj.toString())).append("\"");  
         } else if (obj instanceof Object[]) {  
           json.append(array2json((Object[]) obj));  
         } else if (obj instanceof List) {  
           json.append(list2json((List<?>) obj));  
         } else if (obj instanceof Map) {  
           json.append(map2json((Map<?, ?>) obj));  
         } else if (obj instanceof Set) {  
           json.append(set2json((Set<?>) obj));  
         } else {  
           json.append(bean2json(obj));  
         }  
         return json.toString();  
	}
	/**
	 * bean转Json
	 * @param bean
	 * @return
	 */
	public static String bean2json(Object bean) {  
         StringBuilder json = new StringBuilder();  
         json.append("{");  
         PropertyDescriptor[] props = null;  
         try {  
           props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();  
         } catch (Exception e) {
        	 log.error("e.getMessage():"+e.getMessage(),e);
        	 e.printStackTrace();
         }  
         if (props != null) {  
           for (int i = 0; i < props.length; i++) {  
             try {  
               String name = object2json(props[i].getName());  
               String value = object2json(props[i].getReadMethod().invoke(bean));  
               json.append(name);  
               json.append(":");  
               json.append(value);  
               json.append(",");  
             } catch (Exception e) {
            	 log.error(e.getMessage(), e);
             }  
           }  
           json.setCharAt(json.length() - 1, '}');  
         } else {  
           json.append("}");  
         }  
         return json.toString();  
	}
	/**
	 * 
	 * list转Json
	 * 
	 * @param list
	 * @return
	 */
	public static String list2json(List<?> list) {  
	     StringBuilder json = new StringBuilder();  
	     json.append("[");  
	     if (list != null && list.size() > 0) {  
	       for (Object obj : list) {  
	         json.append(object2json(obj));  
	         json.append(",");  
	       }  
	       json.setCharAt(json.length() - 1, ']');  
	     } else {  
	       json.append("]");  
	     }  
	     return json.toString();  
	}
	/**
	 * 
	 * array转Json
	 * 
	 * @param array
	 * @return
	 */
	public static String array2json(Object[] array) {  
         StringBuilder json = new StringBuilder();  
         json.append("[");  
         if (array != null && array.length > 0) {  
           for (Object obj : array) {  
             json.append(object2json(obj));  
             json.append(",");  
           }  
           json.setCharAt(json.length() - 1, ']');  
         } else {  
           json.append("]");  
         }  
         return json.toString();  
	}
	/**
	 * map转Json
	 * @param map
	 * @return
	 */
	public static String map2json(Map<?, ?> map) {  
	     StringBuilder json = new StringBuilder();  
	     json.append("{");  
	     if (map != null && map.size() > 0) {  
	       for (Object key : map.keySet()) {  
	         json.append(object2json(key));  
	         json.append(":");  
	         json.append(object2json(map.get(key)));  
	         json.append(",");  
	       }  
	       json.setCharAt(json.length() - 1, '}');  
	     } else {  
	       json.append("}");  
	     }  
	     return json.toString();  
	}
	/**
	 * set转Json
	 * @param set
	 * @return
	 */
	public static String set2json(Set<?> set) {  
	     StringBuilder json = new StringBuilder();  
	     json.append("[");  
	     if (set != null && set.size() > 0) {  
	       for (Object obj : set) {  
	         json.append(object2json(obj));  
	         json.append(",");  
	       }  
	       json.setCharAt(json.length() - 1, ']');  
	     } else {  
	       json.append("]");  
	     }  
	     return json.toString();  
	}
	/**
	 * String转Json
	 * @param s
	 * @return
	 */
	public static String string2json(String s) {  
	     if (s == null)  
	       return "";  
	     StringBuilder sb = new StringBuilder();  
	     for (int i = 0; i < s.length(); i++) {  
	       char ch = s.charAt(i);  
	       switch (ch) {  
	       case '"':  
	         sb.append("\\\"");  
	         break;  
	       case '\\':  
	         sb.append("\\\\");  
	         break;  
	       case '\b':  
	         sb.append("\\b");  
	         break;  
	       case '\f':  
	         sb.append("\\f");  
	         break;  
	       case '\n':  
	         sb.append("\\n");  
	         break;  
	       case '\r':  
	         sb.append("\\r");  
	         break;  
	       case '\t':  
	         sb.append("\\t");  
	         break;  
	       case '/':  
	         sb.append("\\/");  
	         break;  
	       default:  
	         if (ch >= '\u0000' && ch <= '\u001F') {  
	           String ss = Integer.toHexString(ch);  
	           sb.append("\\u");  
	           for (int k = 0; k < 4 - ss.length(); k++) {  
	             sb.append('0');  
	           }  
	           sb.append(ss.toUpperCase());  
	         } else {  
	           sb.append(ch);  
	         }  
	       }  
	     }  
	     return sb.toString();  
	}
	/**
	 * JSON字符串解释成List
	 * @param s
	 * @return
	 */	
	public static List<Map<String,String>> JSONToList(String jsonStr) throws Exception{
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		JSONObject jb=null;
		if(jsonStr.startsWith("[")){
			jb = JSONObject.fromObject("{\"results\":"+jsonStr+"}");
		}else{
			if(jsonStr.indexOf("\"results\":")>0){
				jb = JSONObject.fromObject(jsonStr);
			}else{
				jb = JSONObject.fromObject("{\"results\":["+jsonStr+"]}");
			}
		}
		
        JSONArray jsons = jb.getJSONArray("results");
        int jsonLength = jsons.size();
        for (int i = 0; i < jsonLength; i++) {
            JSONObject tempJson = JSONObject.fromObject(jsons.get(i));
            Iterator<String> iter = tempJson.keys();
            Map<String,String> map=new HashMap<String, String>();
            while(iter.hasNext()){
            	String key=iter.next();
            	map.put(key, StringUtil.showNull(tempJson.get(key)));  
            }
            list.add(map);
        }
        
		return list;
	}
	/**
	 * JSON字符串解释成List
	 * @param s
	 * @return
	 */	
	public static Map<String,String> JSONToMap(String jsonStr) throws Exception{
		
		Map<String,String> map=new HashMap<String, String>();
		JSONObject jb=null;
		if(jsonStr.startsWith("[")){
			jb = JSONObject.fromObject("{\"results\":"+jsonStr+"}");
		}else{
			if(jsonStr.indexOf("\"results\":")>0){
				jb = JSONObject.fromObject(jsonStr);
			}else{
				jb = JSONObject.fromObject("{\"results\":["+jsonStr+"]}");
			}
		}
		
        JSONArray jsons = jb.getJSONArray("results");
        int jsonLength = jsons.size();
        for (int i = 0; i < jsonLength; i++) {
            JSONObject tempJson = JSONObject.fromObject(jsons.get(i));
            Iterator<String> iter = tempJson.keys();
            while(iter.hasNext()){
            	String key=iter.next();
            	map.put(key, StringUtil.showNull(tempJson.get(key)));  
            }
        }
        
		return map;
	}	
	/**
	 * JSON字符串解释成Bean
	 * @param s
	 * @return
	 */	
	public static <T> T JSONToBean(String jsonStr,Class<T> entityClass) throws Exception{
		
		T object = null;
		
		JSONObject jb=null;
		if(jsonStr.startsWith("[")){
			jb = JSONObject.fromObject("{\"results\":"+jsonStr+"}");
		}else{
			if(jsonStr.indexOf("\"results\":")>0){
				jb = JSONObject.fromObject(jsonStr);
			}else{
				jb = JSONObject.fromObject("{\"results\":["+jsonStr+"]}");
			}
		}
		
        JSONArray jsons = jb.getJSONArray("results");
        int jsonLength = jsons.size();
        if(jsonLength>0){
        	JSONObject tempJson = JSONObject.fromObject(jsons.get(0));
        	object=(T) JSONObject.toBean(tempJson,entityClass);
        }

		return object;
	}
	
	/**
	 * JSON字符串解释成Bean
	 * @param s
	 * @return
	 */	
	public static <T> List<T> JSONToListBean(String jsonStr,Class<T> entityClass) throws Exception{
		
		List<T> objects =new ArrayList<T>();
		
		JSONObject jb=null;
		if(jsonStr.startsWith("[")){
			jb = JSONObject.fromObject("{\"results\":"+jsonStr+"}");
		}else{
			if(jsonStr.indexOf("\"results\":")>0){
				jb = JSONObject.fromObject(jsonStr);
			}else{
				jb = JSONObject.fromObject("{\"results\":["+jsonStr+"]}");
			}
		}
		
        JSONArray jsons = jb.getJSONArray("results");
        int jsonLength = jsons.size();
        for(int i=0;i<jsonLength;i++){
        	JSONObject tempJson = JSONObject.fromObject(jsons.get(i));
        	 T object=(T) JSONObject.toBean(tempJson,entityClass);
        	 objects.add(object);
        }

		return objects;
	}
}
