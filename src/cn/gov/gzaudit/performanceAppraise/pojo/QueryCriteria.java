package cn.gov.gzaudit.performanceAppraise.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *查询条件类
 * @author u
 *
 */
public class QueryCriteria {
	
	private String queryText;	//列表查询搜索框文本
    private String treeNode;	//列表左边树节点
    
    @JsonIgnore
    public String getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode;
	}

	@JsonIgnore
    public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
}
