package cn.gov.gzaudit.performanceAppraise.dao.appraiseScore;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.util.mybatispage.PageUtil;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore.AppraiseScore;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore.AppraiseScoreKey;

public interface AppraiseScoreMapper {
    int deleteByPrimaryKey(AppraiseScoreKey key);

    int insert(AppraiseScore record);

    int insertSelective(AppraiseScore record);

    AppraiseScore selectByPrimaryKey(AppraiseScoreKey key);

    int updateByPrimaryKeySelective(AppraiseScore record);

    int updateByPrimaryKey(AppraiseScore record);
    
    int insertAppraiseScoreBatch(String exampleId);
    
    public List<Map<String, Object>> getAppraiseScore(PageUtil spu);
    
    int updateByAppraiseItemsSelective(@Param("appraiseScore")AppraiseScore appraiseScore,@Param("items")String items);
    
    public List<Map<String, Object>> getScoreStatistics(AppraiseScore record);
    
    public List<Map<String, Object>> searchWeightsStatisticsGridData(PageUtil spu);
    
    public List<Map<String, Object>> searchStatisticsDetailsGridJson(PageUtil spu);
}