package cn.gov.gzaudit.performanceAppraise.dao.appraiseExample;

import java.util.List;
import java.util.Map;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseExample.AppraiseExample;

public interface AppraiseExampleMapper {
    int deleteByPrimaryKey(String exampleId);

    int insert(AppraiseExample record);

    int insertSelective(AppraiseExample record);

    AppraiseExample selectByPrimaryKey(String exampleId);

    int updateByPrimaryKeySelective(AppraiseExample record);

    int updateByPrimaryKey(AppraiseExample record);
    
    public List<Map<String, Object>> getAppraiseExampleById(String exampleId);
}