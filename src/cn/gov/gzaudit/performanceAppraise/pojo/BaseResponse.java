package cn.gov.gzaudit.performanceAppraise.pojo;

import com.util.object.EmptyValidator;

import java.util.Map;

public class BaseResponse {
    private int code;//0:成功 1:未登录 -1:失败
    private String desc;//状态描述
    private Object data;//业务数据
    private Map<String,Object> other;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        if (!EmptyValidator.isNotEmpty(this.desc)){
            this.desc = "操作成功";
        }
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }
}
