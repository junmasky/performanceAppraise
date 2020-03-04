package cn.gov.gzaudit.performanceAppraise.controller.appraise;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;












import cn.gov.gzaudit.performanceAppraise.pojo.response.ResponseData;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore.AppraiseScore;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUser;
import cn.gov.gzaudit.performanceAppraise.service.appraise.IAppraiseService;

import com.framework.sys.UserSession;
import com.util.log.Log;
import com.util.mybatispage.PageUtil;
import com.util.string.StringUtil;


@Controller
@RequestMapping(value = "/appraiseController")
public class AppraiseController {
	private static Log log = new Log(AppraiseController.class);
	
	@Resource  
    private IAppraiseService service;
	
	private static final String LIST_VIEW = "appraise/list";	//考核的待办页面
	private static final String APPRAISING_VIEW = "appraise/appraising";	//绩效考核页面
	private static final String STATISTICS_VIEW = "appraise/statistics";	//考核的统计页面
	private static final String STATISTICS_DETAILS_VIEW = "appraise/statisticsDetails";	//考核的统计详细页面
      
    
    @RequestMapping("/list")  
    public String list(HttpServletRequest request,Model model){  
    	String userId = StringUtil.showNull(request.getParameter("userId"));
    	String appraiseStatus = StringUtil.showNull(request.getParameter("appraiseStatus"));
    	String exampleId = StringUtil.showNull(request.getParameter("exampleId"));
    	String appraiseType = StringUtil.showNull(request.getParameter("appraiseType"));
    	String readonly = StringUtil.showNull(request.getParameter("readonly"));
    	model.addAttribute("userId",userId);
    	model.addAttribute("appraiseStatus",appraiseStatus);
    	model.addAttribute("exampleId",exampleId);
    	model.addAttribute("appraiseType",appraiseType);
    	model.addAttribute("readonly",readonly);
        return LIST_VIEW;  
    }
    
    @RequestMapping("/statistics")  
    public String statistics(HttpServletRequest request,Model model){  
        return STATISTICS_VIEW;  
    }
    
    @RequestMapping("/statisticsDetails")  
    public String statisticsDetails(HttpServletRequest request,Model model){  
    	String exampleId = StringUtil.showNull(request.getParameter("exampleId"));
    	model.addAttribute("exampleId",exampleId);
        return STATISTICS_DETAILS_VIEW;  
    }
    
    @RequestMapping("/appraising")  
    public String appraising(HttpServletRequest request,Model model){
    	String exampleId = StringUtil.showNull(request.getParameter("exampleId"));
    	String userId = StringUtil.showNull(request.getParameter("userId"));
    	AppraiseUser appraiseUser = new AppraiseUser();
    	appraiseUser.setExampleId(exampleId);
    	appraiseUser.setUserId(userId);
    	appraiseUser = service.getAppraiseUserByPrimaryKey(appraiseUser);
    	model.addAttribute("exampleId",exampleId);
    	model.addAttribute("userId",userId);
    	model.addAttribute("appraiseStatus",appraiseUser.getAppraiseStatus());
        return APPRAISING_VIEW;  
    }
    
    /**
     * 根据实例id和用户id返回考核中的详细数据
     * @param appraiseScore
     * @param pu
     * @return
     */
    @RequestMapping(value="/getAppraising")
    public @ResponseBody PageUtil getAppraising(AppraiseScore appraiseScore, PageUtil pu) {
//    	if(!"".equals(StringUtil.showNull(dataList.getQueryText()))){
//    		dataList.setTreeNode(null);
//    	}
        pu.setQueryObj(appraiseScore);//设置查询条件
        List<Map<String,Object>> data = service.getAppraising(pu);
    	pu.setData(data);//设置反馈结果
    	return pu;
    }
    
    /**
     * 保存考核评分
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @RequestMapping(value = "/saveAppraiseScore")
	public @ResponseBody ResponseData saveAppraiseScore(HttpServletRequest request,
			HttpServletResponse response,AppraiseScore bean) {
		ResponseData responseData = new ResponseData();
		UserSession us = (UserSession) request.getSession().getAttribute("userSession");
		try {
			bean.setUpdateUser(us.getUserId());
			responseData = service.saveAppraiseScore(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			responseData.setCode(-1);
			responseData.setMessage(e.getMessage());
		}finally{
			
		}
		return responseData;

	}
    
    /**
     * 返回个人考核的统计数据
     * @param bean
     * @return
     */
    @RequestMapping(value = "/getScoreStatistics")
	public @ResponseBody ResponseData getScoreStatistics(AppraiseScore bean) {
		ResponseData responseData = new ResponseData();
		try {
			responseData = service.getScoreStatistics(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			responseData.setCode(-1);
			responseData.setMessage(e.getMessage());
		}finally{
			
		}
		return responseData;

	}
    
    /**
     * 返回考核中的list数据
     * @param appraiseUser
     * @param pu
     * @return
     */
    @RequestMapping(value="/getAppraiseUserGridJson")
    public @ResponseBody PageUtil getAppraiseUserGridJson(AppraiseUser appraiseUser, PageUtil pu) {
//    	if(!"".equals(StringUtil.showNull(dataList.getQueryText()))){
//    		dataList.setTreeNode(null);
//    	}
        pu.setQueryObj(appraiseUser);//设置查询条件
        List<Map<String,Object>> data = service.searchAppraiseUserGridData(pu);
    	pu.setData(data);//设置反馈结果
    	return pu;
    }
    
    @RequestMapping(value = "/saveAppraiseUser")
	public @ResponseBody ResponseData saveAppraiseUser(HttpServletRequest request,
			HttpServletResponse response,AppraiseUser bean) {
		ResponseData responseData = new ResponseData();
		UserSession us = (UserSession) request.getSession().getAttribute("userSession");
		try {
			bean.setUpdateUser(us.getUserId());
			responseData = service.saveAppraiseUser(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			responseData.setCode(-1);
			responseData.setMessage(e.getMessage());
		}finally{
			
		}
		return responseData;

	}
    
    @RequestMapping(value="/getWeightsStatisticsGridJson")
    public @ResponseBody PageUtil getWeightsStatisticsGridJson(AppraiseScore bean, PageUtil pu) {
        pu.setQueryObj(bean);//设置查询条件
        List<Map<String,Object>> data = service.searchWeightsStatisticsGridData(pu);
    	pu.setData(data);//设置反馈结果
    	return pu;
    }
    
    @RequestMapping(value="/getStatisticsDetailsGridJson")
    public @ResponseBody PageUtil getStatisticsDetailsGridJson(AppraiseScore bean, PageUtil pu) {
        pu.setQueryObj(bean);//设置查询条件
        List<Map<String,Object>> data = service.searchStatisticsDetailsGridJson(pu);
    	pu.setData(data);//设置反馈结果
    	return pu;
    }
}
