package com.framework.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.util.jwt.JWTUtils;

public class TokenInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		JWTUtils jwtUtils = new JWTUtils();
		String jwt = request.getHeader("Authorization");
		Cookie cookies[] = request.getCookies();
		if(cookies != null){
			for(int i = 0;i < cookies.length;i++){
				if(cookies[i].getName().equals("token")){
					if("".equals(jwt) || jwt == null){
						jwt = cookies[i].getValue();
					}
					break;
				}
			}
		}
		System.out.println(jwt);
		return true;
	}

}
