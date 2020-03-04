package com.framework.autoHitSelect;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.util.string.StringUtil;

public abstract class AutoHitSelect {
	public abstract List getList(HttpServletRequest request,HttpServletResponse response);
	
	public String getJsonData(HttpServletRequest request,
			HttpServletResponse response){
		
		String checkmode = StringUtil.showNull(request.getParameter("checkmode"));
		String dataType = StringUtil.showNull(request.getAttribute("dataType"));
		
		String jsonStr="";
		List list=new ArrayList();

		if(!checkmode.equals("")){
			list=this.getCheckList(request, response);
			List<Map<String, Object>> list1=new ArrayList<Map<String, Object>>();					
			for(int i=0;i<list.size();i++){
				Map<String,Object> fields=new LinkedHashMap<String, Object>();	
				SelectOption selectOption=(SelectOption)list.get(i);
				fields.put("id", selectOption.getId());
				fields.put("text", selectOption.getText());
				fields.put("value", selectOption.getValue());
				fields.put("leaf", selectOption.isLeaf());
				list1.add(fields);
			}
			jsonStr=JSONArray.fromObject(list1).toString();
		}else{
			if(dataType.equalsIgnoreCase("tree")){
				list=this.getList(request, response);
				jsonStr= JSONArray.fromObject(list).toString();
			}else if(dataType.equalsIgnoreCase("AutoData")){
				list=this.getList(request, response);
				jsonStr= JSONArray.fromObject(list).toString();
			}else{
				list=this.getList(request, response);
				int totalProperty = list.size();
				//jsonStr = "{totalProperty:" + totalProperty + ",data:";
				String resultStr = JSONArray.fromObject(list).toString();
				jsonStr += resultStr; //+ "}";					
			}
		}
		
		return jsonStr;		
	};
	
	public abstract List getCheckList(HttpServletRequest request,HttpServletResponse response);
	
}
