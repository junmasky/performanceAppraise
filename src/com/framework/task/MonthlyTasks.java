package com.framework.task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

//import cn.gov.gzaudit.dailyManage.service.monthReport.IMonthReportService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.framework.webSocket.MsgScoketHandle;

@Component
public class MonthlyTasks {
	
//	@Resource
//    private MsgScoketHandle msgScoketHandle;
//	@Resource
//    private IMonthReportService monthReportService;
//	
//	@Scheduled(cron="0 20-40/5 17 28-31 * ?")
//	public void monthReportRemind(){
//		ObjectMapper om = new ObjectMapper();
//        Map<String, String> msgMap = new HashMap<String, String>();
//        msgMap.put("msgType", "1");
//        msgMap.put("msgContent", "注意填写月报");
//        String user = monthReportService.checkReport(3);
//        user = user==null ? "" : user;
//        String [] users = user.split(",");
//        TextMessage textMessage;
//		try {
//			textMessage = new TextMessage(om.writeValueAsString(msgMap));
//			msgScoketHandle.sendMessageToUser(users, textMessage);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
