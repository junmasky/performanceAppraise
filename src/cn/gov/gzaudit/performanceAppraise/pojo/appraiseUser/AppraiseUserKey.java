package cn.gov.gzaudit.performanceAppraise.pojo.appraiseUser;

public class AppraiseUserKey {
    private String userId;

    private String exampleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId == null ? null : exampleId.trim();
    }
}