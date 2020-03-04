package cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser;

import java.math.BigDecimal;
import java.util.Date;

public class AppraiseUser extends AppraiseUserKey {
    private String appraiseItemsType;

    private String appraiseType;

    private Short sortFlag;

    private String updateUser;

    private Date updateTime;

    private String statisticsSign;

    private BigDecimal weights;

    private Short appraiseStatus;

    public String getAppraiseItemsType() {
        return appraiseItemsType;
    }

    public void setAppraiseItemsType(String appraiseItemsType) {
        this.appraiseItemsType = appraiseItemsType == null ? null : appraiseItemsType.trim();
    }

    public String getAppraiseType() {
        return appraiseType;
    }

    public void setAppraiseType(String appraiseType) {
        this.appraiseType = appraiseType == null ? null : appraiseType.trim();
    }

    public Short getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(Short sortFlag) {
        this.sortFlag = sortFlag;
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

    public String getStatisticsSign() {
        return statisticsSign;
    }

    public void setStatisticsSign(String statisticsSign) {
        this.statisticsSign = statisticsSign == null ? null : statisticsSign.trim();
    }

    public BigDecimal getWeights() {
        return weights;
    }

    public void setWeights(BigDecimal weights) {
        this.weights = weights;
    }

    public Short getAppraiseStatus() {
        return appraiseStatus;
    }

    public void setAppraiseStatus(Short appraiseStatus) {
        this.appraiseStatus = appraiseStatus;
    }
}