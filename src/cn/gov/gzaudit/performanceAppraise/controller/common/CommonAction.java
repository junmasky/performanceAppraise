package cn.gov.gzaudit.performanceAppraise.controller.common;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.servlet.ModelAndView;

import com.util.db.DBConnect;
import com.util.db.DbUnits;
import com.util.db.DbUtil;
import com.util.log.Log;
import com.util.string.StringUtil;
import com.util.web.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.autoHitSelect.AutoHitSelect;
import com.framework.sys.UserSession;


@Controller
@RequestMapping(value = "/commonController")
public class CommonAction {
	private static Log log = new Log(CommonAction.class);
	
	@RequestMapping(value = "/getTreeJsonData2")
	public ModelAndView getTreeJsonData2(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String autoId= StringUtil.showNull(request.getParameter("autoId"));
		String refer = StringUtil.showNull(request.getParameter("refer"));
		String refer1 =StringUtil.showNull(request.getParameter("refer1"));
		String refer2 =StringUtil.showNull(request.getParameter("refer2"));	
		String checked =StringUtil.showNull(request.getParameter("checked"));//是否根据strchecksql选中节点
		String year =StringUtil.showNull(request.getParameter("year"));
		//添加一个根节点
		String sortroot = StringUtil.showNull(request.getParameter("sortroot"));
		//state状态用来判断树是否展开open为展开，closed为关闭
		String state = StringUtil.showNull(request.getParameter("state"));
		//当树的state状态为closed的时候，使用参数id
		String id = StringUtil.showNull(request.getParameter("id"));
		//是否添加attributes属性值;true为添加，false为不添加
		String attributes = StringUtil.showNull(request.getParameter("attributes"));
		//是否排序
		String sortflag = StringUtil.showNull(request.getParameter("sortflag"));
		String rootId = StringUtil.showNull(request.getParameter("rootId"));
		
		//判断使用哪个数据库，如果为空则默认使用gzsj的数据库
		String connType = StringUtil.showNull(request.getParameter("connType"));
		
		UserSession us = (UserSession) request.getSession().getAttribute("userSession");
		String strCurUserId = us.getUserId();
//		String curDepartmentid = (String) us.getDeptId();
//		String curUnitId = (String) us.getUnitId();
//		String curCtlUnitId = (String) us.getCtlUnitId();
		String curDepartmentid = "";
		String curUnitId = "";
		String curCtlUnitId = "";
		ObjectMapper om = new ObjectMapper();
		
		String sql="select strinitsql,strsql,strchecksql from MT_SYS_AUTOHINTSELECT where id = ? ";
		
		String json="";
		Object[] objs={autoId};
		Connection conn=null;
		Connection Dataconn=null;
		try {
			conn=new DBConnect().getConnect();
			if (autoId != null && !"".equals(autoId)) {
				
				if (autoId.toLowerCase().indexOf(".") > 0) {
					AutoHitSelect autoHitSelect = (AutoHitSelect) Class.forName(autoId).newInstance();
					String jsonStr = autoHitSelect.getJsonData(request, response);
					WebUtil.writeHtml(response, jsonStr);
				}else{
					Map<String, Object> map=new DbUnits(conn).getOneObject2Map(sql, objs);
					if(map!=null&&map.size()>0){
						
						List<Map<String,Object>>  zonglist = new ArrayList<Map<String,Object>>();
						Map<String,Object> rootmap = new HashMap<String,Object>();
						String strinitsql = StringUtil.showNull(map.get("strinitsql"));
//							strinitsql += " and d.dic_value = "+year;
						if(!"".equals(sortflag)){
							strinitsql += " order by "+sortflag;
						}
						
						Map<String, String> requestParams=new HashMap<String, String>();
						requestParams.put("refer", refer);
						requestParams.put("refer1", refer1);
 						requestParams.put("refer2", refer2);
						requestParams.put("checked", checked);
						requestParams.put("id", id);
						requestParams.put("state",state);
						requestParams.put("strCurUserId", strCurUserId);
						requestParams.put("curDepartmentid", curDepartmentid);
						requestParams.put("curUnitId", curUnitId);
						requestParams.put("curCtlUnitId", curCtlUnitId);
						requestParams.put("strinitsql",strinitsql);
						requestParams.put("attributes", attributes);
						requestParams.put("strsql",StringUtil.showNull(map.get("strsql")));
						requestParams.put("strchecksql",StringUtil.showNull(map.get("strchecksql")));
						List list = null;
						list = new DbUnits(conn).getTreeJson3(requestParams, "");
						if(!"".equals(sortroot)&&"".equals(id)){
							if(!"".equals(rootId)){
								rootmap.put("id",rootId);
							}else{
								rootmap.put("id","root");								
							}
							rootmap.put("text",sortroot);
							if(list.size()==0){
								rootmap.put("iconCls","icon-leaf");
							}
							rootmap.put("children",list);
							zonglist.add(rootmap);
//							json=JSONArray.fromObject(zonglist).toString();
							json = om.writeValueAsString(zonglist);
						}else{
//							json=JSONArray.fromObject(list).toString();
							json = om.writeValueAsString(list);
						}
						
						response.getWriter().print(json);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
		}
		
		return null;
	}
}
