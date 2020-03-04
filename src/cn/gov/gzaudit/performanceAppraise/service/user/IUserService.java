package cn.gov.gzaudit.performanceAppraise.service.user;


import cn.gov.gzaudit.performanceAppraise.pojo.user.gzaudit.User;

public interface IUserService {
	public User getUserById(String id);
	
	public User login(String userId,String password);
	
}
