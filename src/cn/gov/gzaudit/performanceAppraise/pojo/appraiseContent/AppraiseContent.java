package cn.gov.gzaudit.performanceAppraise.pojo.appraiseContent;

import java.util.Date;

public class AppraiseContent {
    private String appraiseContentId;

    private String items;

    private String appraiseObject;

    private String content;

    private String requirement;

    private Short standardScore;

    private String deductPointsItems;

    private Short sortFlag;

    private String updateUser;

    private Date updateTime;

    public String getAppraiseContentId() {
        return appraiseContentId;
    }

    public void setAppraiseContentId(String appraiseContentId) {
        this.appraiseContentId = appraiseContentId == null ? null : appraiseContentId.trim();
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }

    public String getAppraiseObject() {
        return appraiseObject;
    }

    public void setAppraiseObject(String appraiseObject) {
        this.appraiseObject = appraiseObject == null ? null : appraiseObject.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    public Short getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(Short standardScore) {
        this.standardScore = standardScore;
    }

    public String getDeductPointsItems() {
        return deductPointsItems;
    }

    public void setDeductPointsItems(String deductPointsItems) {
        this.deductPointsItems = deductPointsItems == null ? null : deductPointsItems.trim();
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
}