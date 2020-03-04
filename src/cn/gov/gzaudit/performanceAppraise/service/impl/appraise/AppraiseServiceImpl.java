package cn.gov.gzaudit.performanceAppraise.service.impl.appraise;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.util.log.Log;
import com.util.mybatispage.PageUtil;

import cn.gov.gzaudit.performanceAppraise.dao.appraiseContent.AppraiseContentMapper;
import cn.gov.gzaudit.performanceAppraise.dao.appraiseExample.AppraiseExampleMapper;
import cn.gov.gzaudit.performanceAppraise.dao.appraiseScore.AppraiseScoreMapper;
import cn.gov.gzaudit.performanceAppraise.dao.appraiseUser.AppraiseUserMapper;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseContent.AppraiseContent;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseExample.AppraiseExample;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore.AppraiseScore;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUser;
import cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser.AppraiseUserKey;
import cn.gov.gzaudit.performanceAppraise.pojo.response.ResponseData;
import cn.gov.gzaudit.performanceAppraise.service.appraise.IAppraiseService;

@Service("appraiseService")
public class AppraiseServiceImpl implements IAppraiseService {
	
	private static Log log = new Log(AppraiseServiceImpl.class);
	
	@Resource
	private AppraiseExampleMapper appraiseExampleMapper;
	@Resource
	private AppraiseUserMapper appraiseUserMapper;
	@Resource
	private AppraiseScoreMapper appraiseScoreMapper;
	@Resource
	private AppraiseContentMapper appraiseContentMapper;
	
	@Override
	public List<Map<String, Object>> getAppraising(PageUtil spu) {
		// TODO Auto-generated method stub
		
//		List<Map<String, Object>> appraiseExampleInfo = appraiseExampleMapper.getAppraiseExampleById(exampleId);
//		List<AppraiseUser> appraiseUserList = appraiseUserMapper.getAppraiseUserByExampleId(exampleId);
		List<Map<String, Object>> appraiseScoreList = appraiseScoreMapper.getAppraiseScore(spu);
		return appraiseScoreList;
	}

	@Override
	public ResponseData saveAppraiseScore(AppraiseScore appraiseScore) {
		// TODO Auto-generated method stub
		int n = -1;
		ResponseData responseData = new ResponseData();
		AppraiseUser appraiseUser = new AppraiseUser();
		//本人才能修改评分
		if(appraiseScore.getUserId().equals(appraiseScore.getUpdateUser())){
			
			appraiseUser.setExampleId(appraiseScore.getExampleId());
			appraiseUser.setUserId(appraiseScore.getUserId());
			appraiseUser = appraiseUserMapper.selectByPrimaryKey(appraiseUser);
			if(appraiseUser.getAppraiseItemsType().equals("考核项目")){
				AppraiseContent appraiseContent = appraiseContentMapper.selectByPrimaryKey(appraiseScore.getAppraiseContentId());
				n = appraiseScoreMapper.updateByAppraiseItemsSelective(appraiseScore,appraiseContent.getItems());
				
			}else if(appraiseUser.getAppraiseItemsType().equals("考核内容")){
				n = appraiseScoreMapper.updateByPrimaryKeySelective(appraiseScore);
			}
		}else{
			n = -2;
		}
		responseData.setCode(n);
		if (n >= 1){
			System.out.println("分数保存成功");
			responseData.setMessage("分数保存成功");
		}else if (n == -2){
			System.out.println("保存失败，没有权限修改评分");
			responseData.setMessage("保存失败，没有权限修改评分");
		}else{
			System.out.println("分数保存失败");
			responseData.setMessage("分数保存失败");
		}
		return responseData;
	}

	@Override
	public ResponseData getScoreStatistics(AppraiseScore appraiseScore) {
		// TODO Auto-generated method stub
		ResponseData responseData = new ResponseData();
		List<Map<String, Object>> scoreStatistics = appraiseScoreMapper.getScoreStatistics(appraiseScore);
		responseData.setCode(scoreStatistics.size());
		responseData.putDataValue("scoreStatistics", scoreStatistics);
		if (responseData.getCode() >= 1){
			System.out.println("获取统计数据成功");
			responseData.setMessage("获取统计数据成功");
		}else{
			System.out.println("获取统计数据失败");
			responseData.setMessage("获取统计数据失败");
		}
		return responseData;
	}

	@Override
	public List<Map<String, Object>> searchAppraiseUserGridData(PageUtil spu) {
		// TODO Auto-generated method stub
		return appraiseUserMapper.searchAppraiseUserGridData(spu);
	}

	@Override
	public ResponseData saveAppraiseUser(AppraiseUser bean) {
		// TODO Auto-generated method stub
		int n = -1;
		ResponseData responseData = new ResponseData();
		//本人才能提交考核
		if((bean.getUserId().equals(bean.getUpdateUser()) && bean.getAppraiseStatus() == 1) || bean.getAppraiseStatus() == 0){
			n = appraiseUserMapper.updateByPrimaryKeySelective(bean);
		}else{
			n = -2;
		}
		responseData.setCode(n);
		if (n >= 1){
			responseData.setMessage("操作成功");
		}else if (n == -2){
			responseData.setMessage("保存失败，没有权限提交考核");
		}else{
			responseData.setMessage("操作失败");
		}
		return responseData;
	}

	@Override
	public AppraiseUser getAppraiseUserByPrimaryKey(AppraiseUserKey key) {
		// TODO Auto-generated method stub
		return appraiseUserMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<Map<String, Object>> searchWeightsStatisticsGridData(
			PageUtil spu) {
		// TODO Auto-generated method stub
		return appraiseScoreMapper.searchWeightsStatisticsGridData(spu);
	}

	@Override
	public List<Map<String, Object>> searchStatisticsDetailsGridJson(
			PageUtil spu) {
		// TODO Auto-generated method stub
		return appraiseScoreMapper.searchStatisticsDetailsGridJson(spu);
	}
	
	
	
	

}
