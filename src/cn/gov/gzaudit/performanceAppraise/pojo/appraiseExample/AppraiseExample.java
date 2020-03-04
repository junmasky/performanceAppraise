package cn.gov.gzaudit.performanceAppraise.pojo.appraiseExample;

import java.util.Date;

public class AppraiseExample {
    private String exampleId;

    private String appraiseMouldId;

    private String exampleTitle;

    private String updateUser;

    private Date updateTime;

    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId == null ? null : exampleId.trim();
    }

    public String getAppraiseMouldId() {
        return appraiseMouldId;
    }

    public void setAppraiseMouldId(String appraiseMouldId) {
        this.appraiseMouldId = appraiseMouldId == null ? null : appraiseMouldId.trim();
    }

    public String getExampleTitle() {
        return exampleTitle;
    }

    public void setExampleTitle(String exampleTitle) {
        this.exampleTitle = exampleTitle == null ? null : exampleTitle.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}