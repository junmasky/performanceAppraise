package cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore;

import java.util.Date;

public class AppraiseScore extends AppraiseScoreKey {
    private String appraiseItemsType;

    private Short appraiseScore;

    private String updateUser;

    private Date updateTime;

    private String statisticsSign;

    public String getAppraiseItemsType() {
        return appraiseItemsType;
    }

    public void setAppraiseItemsType(String appraiseItemsType) {
        this.appraiseItemsType = appraiseItemsType == null ? null : appraiseItemsType.trim();
    }

    public Short getAppraiseScore() {
        return appraiseScore;
    }

    public void setAppraiseScore(Short appraiseScore) {
        this.appraiseScore = appraiseScore;
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
}