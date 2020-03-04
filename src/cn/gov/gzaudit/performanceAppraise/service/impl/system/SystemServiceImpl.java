package cn.gov.gzaudit.performanceAppraise.service.impl.system;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.sys.UserSession;
import com.util.encrypt.MD5;
import com.util.mybatispage.PageUtil;

import cn.gov.gzaudit.performanceAppraise.pojo.user.gzaudit.User;
//import cn.gov.gzaudit.performanceAppraise.service.menu.IMenuService;
//import cn.gov.gzaudit.performanceAppraise.service.role.IRoleService;
import cn.gov.gzaudit.performanceAppraise.service.system.ISystemService;
import cn.gov.gzaudit.performanceAppraise.service.user.IUserService;

@Service("systemService")
public class SystemServiceImpl implements ISystemService {
	
	@Resource  
    private IUserService userService;
//	@Resource  
//    private IMenuService menuService;
//	@Resource  
//    private IRoleService roleService;
	@Override
	public UserSession login(String loginId, String password) {
		// TODO Auto-generated method stub
		ObjectMapper om = new ObjectMapper();
		User user = userService.login(loginId, password);
		UserSession userSession = new UserSession();
		if(user != null){
			try {
//				List<Map<String, Object>> userRole = roleService.getRoleByUserid(userId);
				userSession.setUserID(user.getUserId());
				userSession.setUserName(user.getUserName());
//				if(userRole.get(0) != null){
//					userSession.setUserRole(userRole.get(0).get("ROLEIDS").toString());
//				}
//				String firstMenu = om.writeValueAsString(menuService.getParentMenuByUserId(user.getUserid()));
//				userSession.setFirstMenu(firstMenu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return userSession;
		}else{
			return null;
		}
	}
	
	@Override
	public UserSession getUserSession(String userId) {
		// TODO Auto-generated method stub
		ObjectMapper om = new ObjectMapper();
		User user = userService.getUserById(userId);
		UserSession userSession = new UserSession();
		if(user != null){
			try {
//				List<Map<String, Object>> userRole = roleService.getRoleByUserid(userId);
				userSession.setUserID(user.getUserId());
				userSession.setUserName(user.getUserName());
//				if(userRole.get(0) != null){
//					userSession.setUserRole(userRole.get(0).get("ROLEIDS").toString());
//				}
//				String firstMenu = om.writeValueAsString(menuService.getParentMenuByUserId(user.getUserid()));
//				userSession.setFirstMenu(firstMenu);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return userSession;
		}else{
			return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getRoleByUserid(String userId) {
		// TODO Auto-generated method stub
//		return roleService.getRoleByUserid(userId);
		return null;
	}
	@Override
	public List<Map<String, Object>> getParentMenuByUserId(String userId) {
		// TODO Auto-generated method stub
//		return menuService.getParentMenuByUserId(userId);
		return null;
	}


	
	

}
