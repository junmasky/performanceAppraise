package cn.gov.gzaudit.performanceAppraise.dao.appraiseMould;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseMould.AppraiseMould;

public interface AppraiseMouldMapper {
    int deleteByPrimaryKey(String appraiseMouldId);

    int insert(AppraiseMould record);

    int insertSelective(AppraiseMould record);

    AppraiseMould selectByPrimaryKey(String appraiseMouldId);

    int updateByPrimaryKeySelective(AppraiseMould record);

    int updateByPrimaryKey(AppraiseMould record);
}