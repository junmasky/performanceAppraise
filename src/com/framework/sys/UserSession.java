package com.framework.sys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserSession {
	
	private String sessionID;
	private String userId;//用户ID
	private String userName;//用户名称
	private String userIp;//用户IP
	private String userRole;	//用户角色
	private String loginDate;//登录时间
	
	private String phone;//联系电话
	private String firstMenu;//一级菜单


	public UserSession(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String reTime = format.format(date);
		this.loginDate = reTime;
	}
	
	public String getFirstMenu() {
		return firstMenu;
	}
	
	
	public void setFirstMenu(String firstMenu) {
		this.firstMenu = firstMenu;
	}
	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	public String getLoginDate() {
		return loginDate;
	}


	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}


	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserID(String userID) {
		this.userId = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserIp() {
		return userIp;
	}


	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	


	public void setUserId(String userId) {
		this.userId = userId;
	}



	
}
