package cn.gov.gzaudit.performanceAppraise.controller.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import cn.gov.gzaudit.performanceAppraise.service.system.ISystemService;
import cn.gov.gzaudit.performanceAppraise.pojo.response.ResponseData;
import cn.gov.gzaudit.performanceAppraise.pojo.user.gzaudit.User;

import com.util.encrypt.MD5;
import com.util.string.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.sys.UserSession;

@Controller
@RequestMapping(value = "/systemController")
public class SystemController {
	private static String ip;
	
	@Resource  
    private ISystemService systemService;
	
	
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response,Model model){
		String msg = StringUtil.showNull(request.getParameter("errMsg"));
		String errMsg = "";
		if(!"".equals(msg)){
			errMsg = "您已被强行退出，请重新登录";
		}
		model.addAttribute("errMsg", errMsg);
		return "login";
	}
	
	@RequestMapping(value = "/oaLogin")
	public String oaLogin(HttpServletRequest request,
			HttpServletResponse response,Model model){
		String userId = StringUtil.showNull(request.getParameter("userId"));
		String userPassword = StringUtil.showNull(request.getParameter("userPassword"));
		model.addAttribute("userId", userId);
		model.addAttribute("userPassword", userPassword);
		return "oaLogin";
	}
	
	/**
	 * 系统登录验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginValidate")
	@ResponseBody
	public ResponseData loginValidate(HttpServletRequest request,
			HttpServletResponse response){
//		response.setContentType("text/html; charset=UTF-8");
		
		String view = "login";
		String userId = StringUtil.showNull(request.getParameter("userId"));
		String userPassword = StringUtil.showNull(request.getParameter("userPassword"));
		ResponseData responseData = null;
		
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = StringUtil.showNull(request.getParameter("errMsg"));
		UserSession userSession = new UserSession();
		String errMsg = "";
		if(!"".equals(userId) ){
			try {
				userSession = systemService.login(userId,MD5.getMD5String(userPassword));
				if(userSession == null){
					errMsg = "用户不存在或密码错误";
					responseData = ResponseData.unauthorized();
				}else{
					responseData = ResponseData.ok();
					HttpSession hs = request.getSession();
					hs.setMaxInactiveInterval(10 * 3600);
					userSession.setSessionID(hs.getId());
					userSession.setUserIp(ip);
					hs.setAttribute("userSession", userSession);
					hs.setAttribute("userSessionID", userSession.getUserId());
					view = "index";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!"".equals(msg)){
			errMsg = "您已被强行退出，请重新登录";
		}
		responseData.putDataValue("errMsg", errMsg);
//		model.addAttribute("errMsg", errMsg);
		return responseData;
	}
	
	/**
	 * 系统注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exit")
	public String exit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		UserSession userSession = (UserSession) request.getSession()
				.getAttribute("userSession");


		request.getSession().invalidate();
		request.getSession().removeAttribute("userSession");
		response.sendRedirect("login");

		return null;
	}
	
	@RequestMapping(value = "/homePage")
	public String homePage(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws Exception {
			
		return "homePage";
	}
	
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws Exception {
			
		return "index";
	}
}
