package cn.gov.gzaudit.performanceAppraise.pojo.appraiseScore;

public class AppraiseScoreKey {
    private String appraiseContentId;

    private String exampleId;

    private String userId;

    public String getAppraiseContentId() {
        return appraiseContentId;
    }

    public void setAppraiseContentId(String appraiseContentId) {
        this.appraiseContentId = appraiseContentId == null ? null : appraiseContentId.trim();
    }

    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId == null ? null : exampleId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}