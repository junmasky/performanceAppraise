package cn.gov.gzaudit.performanceAppraise.pojo.appraiseMould;

import java.util.Date;

public class AppraiseMould {
    private String appraiseMouldId;

    private String appraiseMouldTitle;

    private String remarks;

    private String updateUser;

    private Date updateTime;

    public String getAppraiseMouldId() {
        return appraiseMouldId;
    }

    public void setAppraiseMouldId(String appraiseMouldId) {
        this.appraiseMouldId = appraiseMouldId == null ? null : appraiseMouldId.trim();
    }

    public String getAppraiseMouldTitle() {
        return appraiseMouldTitle;
    }

    public void setAppraiseMouldTitle(String appraiseMouldTitle) {
        this.appraiseMouldTitle = appraiseMouldTitle == null ? null : appraiseMouldTitle.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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