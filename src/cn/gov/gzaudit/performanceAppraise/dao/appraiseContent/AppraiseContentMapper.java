package cn.gov.gzaudit.performanceAppraise.dao.appraiseContent;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseContent.AppraiseContent;

public interface AppraiseContentMapper {
    int deleteByPrimaryKey(String appraiseContentId);

    int insert(AppraiseContent record);

    int insertSelective(AppraiseContent record);

    AppraiseContent selectByPrimaryKey(String appraiseContentId);

    int updateByPrimaryKeySelective(AppraiseContent record);

    int updateByPrimaryKey(AppraiseContent record);
}