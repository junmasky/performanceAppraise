package com.util.mybatispage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
 
public class PageUtil {
	
	private int rows;
	
	private int total;
	
	private int page;
    
	private String orderStr;
	
	private String sort;
	
	private String order;
	
	private Object queryObj;
	
	private Object data;
	
	@JsonIgnore
	public String getOrderStr() {
		orderStr = "";
		if (!org.springframework.util.StringUtils.isEmpty(order)) {
			String[] orders = order.split(",");
			String[] sorts = sort.split(",");
			for (int i = 0; i < sorts.length; i++) {
				orderStr += sorts[i] + " " + orders[i] + ", ";
			}
			orderStr = orderStr.endsWith(", ") ? orderStr.substring(0, orderStr.length() - 2) : orderStr;
		}
		return orderStr;
	}
	
	@JsonIgnore
	public int getPageSize() {
		return rows;
	}
	
	public Object getRows(){
		return data;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
 
	public int getTotal() {
		return total;
	}
 
	public void setTotal(int total) {
		this.total = total;
	}
 
	@JsonIgnore
	public int getPage() {
		return page;
	}
 
	public void setPage(int page) {
		this.page = page;
	}
 
	@JsonIgnore
	public String getSort() {
		return sort;
	}
 
	public void setSort(String sort) {
		this.sort = sort;
	}
 
	@JsonIgnore
	public String getOrder() {
		return order;
	}
 
	public void setOrder(String order) {
		this.order = order;
	}
 
	@JsonIgnore
	public Object getQueryObj() {
		return queryObj;
	}
 
	public void setQueryObj(Object queryObj) {
		this.queryObj = queryObj;
	}
 
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
 
	public void setData(Object data) {
		this.data = data;
	}
 
	@Override
	public String toString() {
		return "PageUtil [rows=" + rows + ", total=" + total + ", page=" + page
				+ ", orderStr=" + orderStr + ", sort=" + sort + ", order="
				+ order + ", queryObj=" + queryObj + ", data=" + data + "]";
	}
 
}