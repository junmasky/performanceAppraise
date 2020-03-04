package com.util.db;

import java.net.ProxySelector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;




import com.util.log.Log;

public class DBConnect {
	
	private Log log=new Log(DBConnect.class);
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 获得数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnect() throws Exception {

		Connection conn = null;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)webApplicationContext.getBean("sqlSessionFactory");
		try {
//			conn = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
			conn = sqlSessionFactory.openSession().getConnection();
		} catch (Exception ex) {
			log.error("从连接池获取连接失败,将从本地获取连接！",ex);
			
		}

		return conn;
	}
	
	
}
