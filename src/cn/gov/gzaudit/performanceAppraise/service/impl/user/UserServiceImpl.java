package cn.gov.gzaudit.performanceAppraise.service.impl.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.framework.sys.UserSession;
import com.util.encrypt.MD5;
import com.util.log.Log;
import com.util.mybatispage.PageUtil;
import com.util.string.AjaxResultFmt;
import com.util.string.StringUtil;

import cn.gov.gzaudit.performanceAppraise.dao.user.gzaudit.UserMapper;
import cn.gov.gzaudit.performanceAppraise.pojo.response.ResponseData;
import cn.gov.gzaudit.performanceAppraise.pojo.user.gzaudit.User;
import cn.gov.gzaudit.performanceAppraise.service.user.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	private static Log log = new Log(UserServiceImpl.class);
	@Resource
	private UserMapper userMapper;
	
	
	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return this.userMapper.selectByPrimaryKey(userId);
	}
	
	@Override
	public User login(String userId,String password) {
		// TODO Auto-generated method stub
		return this.userMapper.login(userId,password);
	}
	

}
