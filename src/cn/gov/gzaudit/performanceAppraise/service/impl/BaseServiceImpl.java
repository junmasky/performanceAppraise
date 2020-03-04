package cn.gov.gzaudit.performanceAppraise.service.impl;

import cn.gov.gzaudit.performanceAppraise.pojo.BaseResponse;
import com.util.log.Log;

public class BaseServiceImpl {
    protected static Log log = null;

    protected void logException(BaseResponse baseResponse,Exception e){
        String className = e.getStackTrace()[0].getClassName();
        String methodName = e.getStackTrace()[0].getMethodName();
        baseResponse.setDesc(className+" " + methodName + "方法抛异常:"+e.getMessage());
        if(log == null){
            log = new Log(this.getClass());
        }
        log.error(e.getMessage(),e);
    }
}
