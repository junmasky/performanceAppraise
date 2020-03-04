package com.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.framework.sys.UserSession;

public class RequestFilter implements Filter {
	
	private static Log log = LogFactory.getLog(RequestFilter.class);
	
	private String LOGIN_URL;//登录URL
	private String[] IGNORE_URLS;//忽略的URL
	
	protected String encoding = null;//默认POST编码
	protected String encoding_wlc = null;//weblogic下的GET编码
	protected boolean ignore = true;
	
	protected String selectEncoding(ServletRequest request){
		return (this.encoding);
	}
	
	protected String selectEncoding_wlc(ServletRequest request){
		return (this.encoding_wlc);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.encoding = null;
		this.encoding_wlc = null;
		
		log.info("过滤器注销了");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		String encod = selectEncoding(request);
		String encod_wlc = selectEncoding_wlc(request);
		if(request.getContentType() == null){
			request.setCharacterEncoding(encod_wlc);
			response.setContentType("application/x-www-form-urlencoded;charset=" + encod_wlc);
			response.setCharacterEncoding(encod_wlc);
		}else{
			request.setCharacterEncoding(encod);
		}
		encod = null;
		encod_wlc = null;
		
		String uri = httpRequest.getServletPath();
		
		if(this.IGNORE_URLS != null){
			for(int i = 0; i < this.IGNORE_URLS.length; i++){
				if(uri.startsWith(IGNORE_URLS[i])){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
		if(userSession == null){
			String url = httpRequest.getContextPath()+this.LOGIN_URL;
			if(!"/jsp/index.jsp".equals(uri)){
				url = url +"?errMsg=already";
			}
			httpResponse.sendRedirect(url);
			return;
		}
//		//如果来源地址为空的则注销用户(防止直接在浏览器地址栏输入链接访问页面)
//		if(httpRequest.getHeader("Referer") == null && !uri.startsWith("/webSocketServer") && !uri.startsWith("/systemController/exit")){
//			httpResponse.sendRedirect(httpRequest.getContextPath()+"/systemController/exit");
//			return;
//		}
		
		MDC.put("userId", userSession.getUserId());
		MDC.put("userName", userSession.getUserName());
		MDC.put("userIp", userSession.getUserIp());
		
		chain.doFilter(request, response);
		
		return;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.LOGIN_URL = config.getInitParameter("loginUrl");
		
		String ignoreUrl = config.getInitParameter("ignoreUrl");
		
		if(ignoreUrl != null && !"".equals(ignoreUrl)){
			this.IGNORE_URLS = ignoreUrl.split(";");
		}
		
		log.info("过滤器启动了...");
		
		this.encoding = config.getInitParameter("encoding");
		this.encoding_wlc = config.getInitParameter("encoding_wlc");
		log.info("编码过滤初始化,默认POST请求编码:" + this.encoding
				+ ",WebLogic下GET请求编码:" + this.encoding_wlc);
	}

}
