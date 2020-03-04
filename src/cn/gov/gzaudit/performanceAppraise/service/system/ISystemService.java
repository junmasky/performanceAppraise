package cn.gov.gzaudit.performanceAppraise.service.system;

import java.util.List;
import java.util.Map;


import com.framework.sys.UserSession;


public interface ISystemService {
	
	public UserSession login(String loginId,String password);
	
	public List<Map<String, Object>> getRoleByUserid(String userId);
	
	public List<Map<String, Object>> getParentMenuByUserId(String userId);
	
	public UserSession getUserSession(String userId);
}
