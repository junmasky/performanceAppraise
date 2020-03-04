package cn.gov.gzaudit.performanceAppraise.pojo.response;

import java.util.HashMap;
import java.util.Map;


public class ResponseData {

	private String message;
	private int code;
	private Map<String, Object> data = new HashMap<String, Object>();
	
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}
	public Map<String, Object> getData() {
		return data;
	}
	
	public ResponseData putDataValue(String key, Object value){
		data.put(key, value);
		return this;
	}
	
	public ResponseData() {
		// TODO Auto-generated constructor stub
	}
	
	private ResponseData(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public static ResponseData ok(){
		return new ResponseData(200,"ok");
	}
	
	public static ResponseData notFound(){
		return new ResponseData(404,"Not Found");
	}
	
	public static ResponseData badRequest(){
		return new ResponseData(400,"Bad Request");
	}
	
	public static ResponseData forbidden(){
		return new ResponseData(403,"Forbidden");
	}
	
	public static ResponseData unauthorized(){
		return new ResponseData(401,"unauthorized");
	}
	
	public static ResponseData serverInternalError(){
		return new ResponseData(500,"Server Internal Error");
	}
	
	public static ResponseData customerError(){
		return new ResponseData(1001,"customer Error");
	}
}
