package cn.gov.gzaudit.performanceAppraise.dao.user.gzaudit;

import org.apache.ibatis.annotations.Param;

import cn.gov.gzaudit.performanceAppraise.pojo.user.gzaudit.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User login(@Param("loginId")String loginId,@Param("password")String password);
}