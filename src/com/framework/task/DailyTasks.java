package com.framework.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

//import cn.gov.gzaudit.dailyManage.pojo.rtx.RtxChatRecord;
//import cn.gov.gzaudit.dailyManage.service.impl.rtx.RtxServiceImpl;
//import cn.gov.gzaudit.dailyManage.service.monthReport.IMonthReportService;
//import cn.gov.gzaudit.dailyManage.service.rtx.IRtxService;
//import cn.gov.gzaudit.dailyManage.service.workList.IWorkListService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.sys.UserSession;
//import com.framework.webSocket.MsgScoketHandle;

@Component
public class DailyTasks {
	
//	@Resource
//    private MsgScoketHandle msgScoketHandle;
//	@Resource
//    private IWorkListService workListService;
//	@Resource
//    private IMonthReportService monthReportService;
//	@Resource
//	private IRtxService rtxService;
//	
//	@Scheduled(cron="0 20-40/5 17 ? * MON-FRI")
//	public void dayReportRemind(){
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		int w = cal.get(Calendar.DAY_OF_WEEK);
//		//判断是检测日报还是检测日报和周报
//		Integer reportFlag = 1;
//		String msgStr = "注意填写日报";
//		//如果是周五，就提醒周报
//		if(w == 6){
//			msgStr += "和周报";
//			reportFlag = 2;
//		}
//		ObjectMapper om = new ObjectMapper();
//        Map<String, String> msgMap = new HashMap<String, String>();
//        msgMap.put("msgType", "1");
//        msgMap.put("msgContent", msgStr);
//        String user = monthReportService.checkReport(reportFlag);
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
//	
//	@Scheduled(cron="0 0/30 8-18 * * ?")
//	public void workListRemind(){
//		System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
//		ObjectMapper om = new ObjectMapper();
//        Map<String, String> msgMap = new HashMap<String, String>();
//        msgMap.put("msgType", "1");
//        msgMap.put("msgContent", "您有新的待办");
//        String user = workListService.checkWorkListByMin(30);
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
//	
//	@Scheduled(cron="0 59 23 * * ?")
//	public void updateRtxChatRecord(){
//		BufferedReader br = null;
//		try {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			String dateStr = df.format(new Date());
//			String rtxLogPath = PropertiesLoaderUtils.loadAllProperties("sysconfig.properties").getProperty("rtx.server.logPath");
//			String fileName = "IM_"+dateStr+".txt";
//			File rtxLogFile = new File(rtxLogPath+fileName);
//			if(rtxLogFile.exists()){
//				br = new BufferedReader(new FileReader(rtxLogFile));
//				String tempStr = null;
//				Map<String,String> map = new HashMap<String, String>();
//				while((tempStr = br.readLine()) != null){
//					if("---------------------------------------------------------------------------".equals(tempStr)){
//						System.out.println(map.get("ThreadID"));
//						RtxChatRecord rtxChatRecord = new RtxChatRecord();
//						rtxChatRecord.setThreadid(map.get("ThreadID"));
//						rtxChatRecord.setTime(dateStr+" "+map.get("Time").split(" ")[1]);
//						rtxChatRecord.setSender(map.get("Sender"));
//						rtxChatRecord.setReceivers(map.get("Receivers"));
//						rtxChatRecord.setMsg(map.get("Msg"));
//						rtxChatRecord.setFileName(fileName);
//						rtxService.addChatRecord(rtxChatRecord);
//						map = new HashMap<String, String>();
//					}else{
//						String str[] = tempStr.split("=");
//						map.put(str[0], str[1]);
//						
//					}
//					System.out.println(tempStr);
//				}
//				
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			if(br != null){
//				try {
//					br.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}

}
