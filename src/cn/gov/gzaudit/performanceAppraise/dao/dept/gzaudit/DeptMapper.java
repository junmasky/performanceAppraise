package cn.gov.gzaudit.performanceAppraise.dao.dept.gzaudit;

import cn.gov.gzaudit.performanceAppraise.pojo.dept.gzaudit.Dept;

public interface DeptMapper {
    int deleteByPrimaryKey(String deptId);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(String deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKeyWithBLOBs(Dept record);

    int updateByPrimaryKey(Dept record);
}