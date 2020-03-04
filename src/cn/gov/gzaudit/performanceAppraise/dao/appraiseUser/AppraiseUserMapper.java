package cn.gov.gzaudit.performanceAppraise.dao.appraiseUser;

import java.util.List;
import java.util.Map;

import com.util.mybatispage.PageUtil;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUser;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUserKey;

public interface AppraiseUserMapper {
    int deleteByPrimaryKey(AppraiseUserKey key);

    int insert(AppraiseUser record);

    int insertSelective(AppraiseUser record);

    AppraiseUser selectByPrimaryKey(AppraiseUserKey key);

    int updateByPrimaryKeySelective(AppraiseUser record);

    int updateByPrimaryKey(AppraiseUser record);
    
    List<AppraiseUser> getAppraiseUserByExampleId(String exampleId);
    
    public List<Map<String, Object>> searchAppraiseUserGridData(PageUtil spu);
}