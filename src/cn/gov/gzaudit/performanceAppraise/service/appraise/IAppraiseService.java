package cn.gov.gzaudit.performanceAppraise.service.appraise;

import java.util.List;
import java.util.Map;

import cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore.AppraiseScore;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUser;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUserKey;
import cn.gov.gzaudit.performanceAppraise.pojo.response.ResponseData;

import com.util.mybatispage.PageUtil;



public interface IAppraiseService {
	public List<Map<String,Object>> getAppraising(PageUtil spu);
	
	public ResponseData saveAppraiseScore(AppraiseScore appraiseScore);
	
	public ResponseData getScoreStatistics(AppraiseScore appraiseScore);
	
	public List<Map<String,Object>> searchAppraiseUserGridData(PageUtil spu);
	
	public ResponseData saveAppraiseUser(AppraiseUser bean);
	
	AppraiseUser getAppraiseUserByPrimaryKey(AppraiseUserKey key);
	
	public List<Map<String,Object>> searchWeightsStatisticsGridData(PageUtil spu);
	
	public List<Map<String,Object>> searchStatisticsDetailsGridJson(PageUtil spu);
}
