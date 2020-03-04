package com.util.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.framework.sys.UserSession;

import com.util.log.Log;
import com.util.string.StringUtil;

/**
 * 
 * 数据库操作公用服务类
 * 使用QueryRunner
 * @author admin
 *
 */
public class DbUnits {
	private Log log = new Log(DbUnits.class) ;
	private QueryRunner queryRunner = new QueryRunner(true);
	
	private Connection conn;
    /**
     * 
     * 初始化对象，需要传入Connection对象
     * 本类所以方法均不关闭Connection对象，使用完毕请手动关闭
     * 
     * @param Connection
     * 
     */
    public DbUnits(Connection conn){
        this.conn = conn;
    }

    /**
     * 
     * 执行SQL语句,获取记录数
     * 
     * @param String  要执行的SQL语句
     * 
     * @return int
     * 
     */	
	public int getTotalCount(String sql) {
		int total = 0;
		String sqlForTotalCount = "select count(1) as totalCount from ( " + sql + ") a";
		try {
			Object[] result = queryRunner.query(conn, sqlForTotalCount, new ArrayHandler());
			BigDecimal a = (BigDecimal) result[0];
			total = a.intValue();
		} catch (Exception e) {
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
		}
		return total;
	}	
	/**
	 * 
	 * 查询表的记录总数
	 * 
	 * @param String
	 * 
	 * @return int
	 */
	public int getTotal(String table) {
		int total = 0;
		String sql = "SELECT COUNT(*) AS TOTAL FROM " + table;
		try {
			Object[] result = queryRunner.query(conn, sql, new ArrayHandler());
			BigDecimal a = (BigDecimal) result[0];
			total = a.intValue();
		} catch (Exception e) {
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
		}
		return total;
	}	    
	    
   /**
    * 
    * 返回Sql语句查询结果集的第一行数据
    * 
    * @param String    查询Sql语句
    * @param Object[]  查询参数，没有参数用null表示
    * 
    * @throws Exception
    */
    public Map<String, Object> getOneObject2Map(String sql,Object[] objs){ 	
    	Map<String, Object> map=new LinkedHashMap<String, Object>();

    	try{
  
    		MapHandler a=new MapHandler();
	    	if(objs==null||objs.length==0){
	    		map=queryRunner.query(conn, sql, a);
	    	}else{
	    		map=queryRunner.query(conn,sql, a, objs);
	    	}
    	}catch(Exception e){
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
    	}
    	return map;
    }
	    
    /**
     * 
     * 返回Sql语句查询结果集所有记录
     * 
     * @param String 查询Sql语句
     * @param Object[] 查询参数，没有参数用null表示
     * 
     * @return List集合 List集合中存放的是Map Map中 key存放列名 value存放列数据
     * 
     * @throws Exception
     */
    public List<Map<String, Object>> getAllObject2ListMap(String sql,Object[] objs){	    	
    	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();

    	try{
	    	MapListHandler a=new MapListHandler();
	    	if(objs==null||objs.length==0){
	    		list=(ArrayList<Map<String, Object>>) queryRunner.query(conn, sql, a);
	    	}else{
	    		list=(ArrayList<Map<String, Object>>) queryRunner.query(conn, sql, a ,objs);
	    	}
    	}catch(Exception e){
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
    	}
    	return list;
    	
    }

    /**
     * 
     * 返回Sql语句查询结果集的第一行数据
     * 
     * @param String     查询Sql语句
     * @param Object[]   查询参数，没有参数用null表示
     * 
     * @throws Exception
     * 
     */
     public Map<String, Object> getOneObject2MapUpper(String sql,Object[] objs){ 	
     	Map<String, Object> map=new LinkedHashMap<String, Object>();
     	PreparedStatement ps = null;
     	ResultSet rs=null;
     	ResultSetMetaData rms=null;
     	try{
 			ps = conn.prepareStatement(sql);
 			if(objs!=null){
 				for(int i=0; i < objs.length; i++) {
 					ps.setObject(i+1, objs[i]);
 				}
 			}

 			rs=ps.executeQuery();
 			rms=rs.getMetaData();
 			
 			if(rs.next()){
 				for(int i=1;i<=rms.getColumnCount();i++){
 					 map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 				}
 			}
     	}catch(Exception e){
     		log.error(e.getMessage(),e);
     		throw new RuntimeException(e);
     	}finally{
     		DbUtil.close(rs);
     		DbUtil.close(ps);
     	}
     	return map;
     }
     
     /**
      * 
      *返回 结果 将第一个值做为KEY将第二个值做为VALUE
      *
      * @param sql		查询Sql语句
      * @param objs		查询参数，没有参数用null表示
      * @return
      */
     public Map<String, String> getAllListMap(String sql,Object[] objs){	    	
      	Map<String, String> list = new LinkedHashMap<String, String>();
      	PreparedStatement ps = null;
      	ResultSet rs=null;
      	ResultSetMetaData rms=null;
  	    try{
  			ps = conn.prepareStatement(sql);
  			if(objs!=null){
  				for(int i=0; i < objs.length; i++) {
  					ps.setObject(i+1, objs[i]);
  				}
  			}

  			rs=ps.executeQuery();
  			rms=rs.getMetaData();

  			while(rs.next()){
  				list.put(rs.getString(rms.getColumnLabel(1)), rs.getString(rms.getColumnLabel(2)));
  				
  			}
  			
      	}catch(Exception e){
      		log.error(e.getMessage(),e);
      		throw new RuntimeException(e);
      	}finally{
      		DbUtil.close(rs);
      		DbUtil.close(ps);
      	}
      	
      	return list;
      	
      }
 	    
     /**
      * 
      * 返回Sql语句查询结果集所有记录
      * 
      * @param String   查询Sql语句
      * @param Object[] 查询参数，没有参数用null表示
      * 
      * @return List集合 List集合中存放的是Map Map中 key存放列名 value存放列数据
      * @throws Exception
      */
     public List<Map<String, Object>> getAllObject2ListMapUpper(String sql,Object[] objs){	    	
     	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
     	PreparedStatement ps = null;
     	ResultSet rs=null;
     	ResultSetMetaData rms=null;
 	    try{
 			ps = conn.prepareStatement(sql);
 			if(objs!=null){
 				for(int i=0; i < objs.length; i++) {
 					ps.setObject(i+1, objs[i]);
 				}
 			}

 			rs=ps.executeQuery();
 			rms=rs.getMetaData();

 			while(rs.next()){
 				Map<String, Object> map = new LinkedHashMap<String, Object>();
 				for(int i=1;i<=rms.getColumnCount();i++){
 					if(rms.getColumnLabel(i).equals("_id")||rms.getColumnLabel(i).equals("_parent")||rms.getColumnLabel(i).equals("_is_leaf")){
 	 					map.put(rms.getColumnLabel(i), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}else{
 	 					map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}				
 				}
 				list.add(map);
 			}
 			
     	}catch(Exception e){
     		log.error(e.getMessage(),e);
     		throw new RuntimeException(e);
     	}finally{
     		DbUtil.close(rs);
     		DbUtil.close(ps);
     	}
     	
     	return list;
     	
     }
     
     public List<Map<String, Object>> getAllObject3ListMapUpper(String sql,Object[] objs){	    	
      	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
      	PreparedStatement ps = null;
      	ResultSet rs=null;
      	ResultSetMetaData rms=null;
  	    try{
  			ps = conn.prepareStatement(sql);
  			if(objs!=null){
  				for(int i=0; i < objs.length; i++) {
  					ps.setObject(i+1, objs[i]);
  				}
  			}

  			rs=ps.executeQuery();
  			rms=rs.getMetaData();

  			while(rs.next()){
  				Map<String, Object> map = new LinkedHashMap<String, Object>();
  				for(int i=1;i<=rms.getColumnCount();i++){
  					if(rms.getColumnLabel(i).equals("_id")||rms.getColumnLabel(i).equals("_parent")||rms.getColumnLabel(i).equals("_is_leaf")){
  	 					map.put(rms.getColumnLabel(i), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
  					}else{
  	 					map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
  					}				
  				}
  				list.add(map);
  			}
  			
      	}catch(Exception e){
      		log.error(e.getMessage(),e);
      		throw new RuntimeException(e);
      	}finally{
      		DbUtil.close(rs);
      		DbUtil.close(ps);
      	}
      	
      	return list;
      	
      }
     
     /**
      * 
      * 返回Sql语句查询结果集所有记录
      * 
      * @param ResultSet 数据库记录集
      * 
      * @return List集合 List集合中存放的是Map Map中 key存放列名 value存放列数据
      * @throws Exception
      */
     public List<Map<String, Object>> getAllObject2ListMapUpper(ResultSet rs){	    	
     	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
     	PreparedStatement ps = null;
     	ResultSetMetaData rms=null;
 	    try{
 			rms=rs.getMetaData();
 			while(rs.next()){
 				Map<String, Object> map = new LinkedHashMap<String, Object>();
 				for(int i=1;i<=rms.getColumnCount();i++){
 					if(rms.getColumnLabel(i).equals("_id")||rms.getColumnLabel(i).equals("_parent")||rms.getColumnLabel(i).equals("_is_leaf")){
 	 					map.put(rms.getColumnLabel(i), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}else{
 	 					map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}				
 				}
 				list.add(map);
 			}
     	}catch(Exception e){
     		log.error(e.getMessage(),e);
     		throw new RuntimeException(e);
     	}finally{
     		DbUtil.close(rs);
     		DbUtil.close(ps);
     	}
     	
     	return list;
     	
     }
     /**
      * 
      * 返回Sql语句查询结果集所有记录
      * 
      * @param ResultSet 数据库记录集
      * 
      * @return List集合 List集合中存放的是Map Map中 key存放列名 value存放列数据
      * @throws Exception
      */
     public List<Map<String, Object>> getAllObject2ListMapFromRS(ResultSet rs){	    	
     	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
     	ResultSetMetaData rms=null;
 	    try{
 			rms=rs.getMetaData();
 			
 			while(rs.next()){
 				Map<String, Object> map = new LinkedHashMap<String, Object>();
 				for(int i=1;i<=rms.getColumnCount();i++){
 					 map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 				}
 				list.add(map);
 			}
 			
     	}catch(Exception e){
     		log.error(e.getMessage(),e);
     		throw new RuntimeException(e);
     	}
     	return list;     	
     }
     
    /**
     * 
     * 返回Sql语句查询结果集的第一行数据
     * 
     * @param String    查询Sql语句
     * @param Object[]  查询参数，没有参数用null表示
     * 
     * @throws Exception
     */
    public Object[] getOneObject2Array(String sql,Object[] objs){
    	Object[] objects=null;
    	PreparedStatement ps = null;
    	ResultSet rs=null;
    	ResultSetMetaData rms=null;
	    try{
			ps = conn.prepareStatement(sql);
			if(objs!=null){
				for(int i=0; i < objs.length; i++) {
					ps.setObject(i+1, objs[i]);
				}
			}

			rs=ps.executeQuery();
			rms=rs.getMetaData();
			if(rs.next()){
				objects=new Object[rms.getColumnCount()];
				for(int i=1;i<=rms.getColumnCount();i++){
					objects[i-1]=DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i));
				}
			}
			
    	}catch(Exception e){
    		log.error(e.getMessage()+":"+sql,e);
    		throw new RuntimeException(e);
    	}finally{
    		DbUtil.close(rs);
    		DbUtil.close(ps);
    	}
    	
    	return objects;
    }
	    
    /**
     * 
     * 返回Sql语句查询结果集
     * 
     * @param String     查询Sql语句
     * @param Object[]   查询参数，没有参数用null表示
     * 
     * @return List List集合中存放的是Object[]数组，Object数组 将结果集每一条数据以数组方式存放 
     * @throws Exception
     */
    public List<Object[]> getAllObject2ListArray(String sql,Object[] objs){
    	List<Object[]> list=new ArrayList<Object[]>();;
    	PreparedStatement ps = null;
    	ResultSet rs=null;
    	ResultSetMetaData rms=null;
    	
	    try{
			ps = conn.prepareStatement(sql);
			if(objs!=null){
				for(int i=0; i < objs.length; i++) {
					ps.setObject(i+1, objs[i]);
				}
			}

			rs=ps.executeQuery();
			rms=rs.getMetaData();
			
			while(rs.next()){
				Object[] objects=new Object[rms.getColumnCount()];
				for(int i=1;i<=rms.getColumnCount();i++){
					objects[i-1]=DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i));
				}
				list.add(objects);
			}
			
    	}catch(Exception e){
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
    	}
    	return list;
    }
	    
    /**
     * 
     * 返回Sql语句查询结果集的第一行数据
     * 
     * @param String   查询Sql
     * @param Class    查询Bean的Class
     * @param Object[] 查询参数，没有参数用null表示
     * 
     * @return Object 返回查询结果对象 （类的属性名务必与数据库表列名一一对应）
     * @throws Exception
     */
    public Object getOneObject2Bean(String sql,Class cls,Object[] objs){
    	Object obj=null;
    	try{
	    	BeanHandler a=new BeanHandler(cls);
	    	if(objs==null||objs.length==0){
	    		obj= queryRunner.query(conn, sql, a);
	    	}else{
	    		obj= queryRunner.query(conn, sql, a , objs);
	    	}	    		
    	}catch(Exception e){
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
    	}
    	return obj;
    }
	    
    /**
     * 
     * 返回Sql语句查询结果集
     * 
     * @param String    查询Sql
     * @param Class     查询Bean的Class
     * @param Object[]  查询参数，没有参数用null表示
     * 
     * @return List List集合中存放查询结果Bean
     * @throws Exception
     */
    public List<Object> getAllObject2ListBean(String sql,Class cls,Object[] objs){
    	ArrayList<Object> list=null;
    	try{
        	BeanListHandler<Object> a=new BeanListHandler(cls);
	    	if(objs==null||objs.length==0){
	    		list=(ArrayList<Object>) queryRunner.query(conn, sql, a);
	    	}else{
	    		list=(ArrayList<Object>) queryRunner.query(conn, sql, a , objs);
	    	}
    	}catch(Exception e){
    		log.error(e.getMessage(),e);
    		throw new RuntimeException(e);
    	}
    	return list;
    }
    
	/**
	 * 
	 * 查询出结果集中的第一条记录，并封装成对象
	 * 
	 * @param String
	 * @param Class<T>
	 * @param Object[]
	 * 
	 * @return 对象
	 */
	public <T> T getById(String sql,Class<T> entityClass, Object[] params) {
		T object = null;
		try {
			if (params == null) {
				object = queryRunner.query(conn, sql, new BeanHandler<T>(entityClass));
			} else {
				object = queryRunner.query(conn, sql, new BeanHandler<T>(entityClass), params);
			}
		} catch (SQLException e) {
    		log.error(e.getMessage(),e);
		}
		return object;
	}
	
	/**
	 * 
	 * 查询,将结果返回到bean中,多个bean通过List包装返回
	 * 
	 * @param Class<T>
	 * @param String
	 * @param Object[]
	 * 
	 * @return List<T>
	 */
	public <T> List<T> query(Class<T> entityClass, String sql, Object[] params) {
		List<T> list = null;
		try {
			if (params == null) {
				list = queryRunner.query(conn, sql, new BeanListHandler<T>(entityClass));
			} else {
				list = queryRunner.query(conn, sql, new BeanListHandler<T>(entityClass), params);
			}
		} catch (Exception e) {
    		log.error(e.getMessage(),e);
		}
		return list;
	}		    
    /**
     * 
     * 通用增加，修改，删除方法
     * 
     * @param String   增加，修改，删除 
     * @param Object[] 数组 没有参数传递null
     * 
     * @return 受影响的行数
     * @throws Exception
     */
    public int update(String sql,Object[] obj) throws Exception{
    	int n=-1;
    	try{
	    	if(obj==null){
	    		n=queryRunner.update(conn,sql);
	    	}else{
	    		n=queryRunner.update(conn, sql, obj);
	    	}	    		
    	}catch(Exception e){
			log.error(e.getMessage(),e);
			throw e;
    	}
    	return n;
    }
	   
	
		/**
		 * 根据字段名删除表数据
		 * @param tableName			表名
		 * @param fieldName			字段名
		 * @param fieldValue		字段值
		 * @return					int
		 */
		public int remove(String fieldValue, String tableName, String fieldName) throws Exception{
			int i = -1;			
			try {
				String sql = "";
				Object[] obj = null;
				sql = "delete "+ tableName +" where "+fieldName+" = ?";
				obj = new Object[]{fieldValue};
				i = this.update(sql, obj);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw e;
			}
			return i;
		}
		/**
		 * 
		 * 根据多个字段名删除表数据
		 * 
		 * @param fieldName
		 * @param fieldValue
		 * @param tableName
		 * @return
		 * @throws Exception
		 */
		public int removeByParamArr(String[] fieldName, String[] fieldValue, 
				String tableName) throws Exception{
			int i = -1;			
			try {
				StringBuffer sql = new StringBuffer();
				sql.append("delete "+ tableName +" where ");
				for (int j = 0; j < fieldName.length; j++) {
					if(j == 0){
						sql.append(fieldName[j] + " = ?");
					}else{
						sql.append(" and " + fieldName[j] + " = ?");
					}
				}
				
				i = this.update(sql.toString(), fieldValue);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw e;
			}
			return i;
		}

		/**
		 * 
		 * 根据主键查找表数据
		 * 
		 * @param tableName			//表名
		 * @param cls				//表的实体类的class类
		 * @param keyValue			//主键的值
		 * @param keyName			//主键名
		 * @return 					//list集合
		 */
		public List<Object> findVoListByUuid(String tableName, Class<?> cls, String keyValue, String keyName){
			List<Object> list =null;
			try {
				String sql = "SELECT * FROM "+ tableName +" WHERE "+keyName+" = ?";
				Object[] objArr = {keyValue };
				list = this.getAllObject2ListBean(sql, cls, objArr);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			
			return list;
		}
		/**
		 * 
		 * 根据主键查找表指定的字段数据
		 * 
		 * @param fieldS			//要查找的字段名
		 * @param tableName			//表名
		 * @param cls				//表的实体类的class类
		 * @param keyValue			//主键的值
		 * @param keyName			//主键名
		 * @return 					//list集合
		 */
		public List<Object> findVoFieldListByUuid(String fieldS, String tableName, 
				Class<?> cls, String keyValue, String keyName){
			List<Object> list = null;
			try {
				String sql = "SELECT "+fieldS+" FROM "+ tableName +" WHERE "+keyName+" = ?";
				Object[] objArr = {keyValue };
				
				list = this.getAllObject2ListBean(sql, cls, objArr);
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return list;
		}
		/**
		 * 
		 * 根据主键查找表数据
		 * 
		 * @param tableName 	数据表名
		 * @param KeyValue 		主键值
		 * @param cls 			数据表的实体类
		 * @param keyNmae 		主键名
		 * @return 				Object
		 */
		public Object getVoById(String tableName, String KeyValue, Class<?> cls, String keyNmae) {
			Object obj = null;
			try {
				String sql = "SELECT * FROM "+tableName+" WHERE "+keyNmae+" = ?";
				Object[] objArr = {KeyValue };
				
				obj = this.getOneObject2Bean(sql, cls, objArr);
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return obj;
		}
		/**
		 * 
		 * 根据某字段名查找结果集的条数
		 * 
		 * @param tableName		表名
		 * @param fieldName		字段名
		 * @param fieldValue	字段值
		 * @return
		 */
		public String getCountByField(String tableName, String fieldName, String fieldValue){
			String r="";

			try {
				String sql="SELECT COUNT(*) FROM "+tableName+" WHERE "+fieldName+" = ?";
				Object[] obj={fieldValue};
				
				Object[] rs=this.getOneObject2Array(sql, obj);
				
				if(rs[0]!=null){
					r=rs[0].toString();
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return r;
		}
		/**
		 * 
		 * 根据某字段名查找结果集的条数
		 * 
		 * @param tableName		表名
		 * @param fieldName		字段名
		 * @param fieldValue	字段值
		 * @return
		 */
		public String getCount(String tableName){
			return String.valueOf(this.getTotal(tableName));
		}
		
	    /**
	     * 
	     * 执行SQL语句,执行成功返回true,否则记录日志,抛出异常,返回false
	     * 
	     * @param strSql String  要执行的SQL语句
	     * @return boolean
	     * @throws Exception
	     */
	    public boolean execute(String strSql) throws Exception {
	        Statement stmt = null;
	        try {
	            stmt = conn.createStatement();
	            return stmt.execute(strSql);
	        } catch(Exception e){
	            log.error("执行方法execute(String strSql)出错:"+strSql,e);
	            throw new Exception("执行sql出错",e);
	        } finally {
	        	DbUtil.close(stmt);
	        }
	    } 
	    
	    
	    /**
	     * 
	     * 执行SQL语句,执行成功返回true,否则记录日志,抛出异常,返回false
	     * 
	     * @param strSql String  要执行的SQL语句
	     * @return boolean
	     * @throws Exception
	     */
	    public boolean executeSql(String strSql) throws Exception {
	    	Statement stmt = null;
	    	try {
	    		stmt = conn.createStatement();
	    		return stmt.execute(strSql);
	    	} catch(Exception e){
	    		log.error("执行方法execute(String strSql)出错:"+strSql,e);
	    		throw new SQLException(e.getMessage(),e);
	    	} finally {
	    		DbUtil.close(stmt);
	    	}
	    } 

    /**
     * 
     * 一次获取整颗树
     * 
     * @param Map<String, Object> 数Map  key存放树属性，value存放数属性值
     * @param String   包含子节点数量列名
     * @param String   查询Sql语句
     * @param Object[] 参数（Sql语句中参数不能使用加号连接）
     * 
     * @return json字符串
     * @throws Exception
     */
    public String getTreeJson(Map<String, Object> map,String ChildCount,String sql,Object[] objs) throws Exception{
 	   String result="";
 	   List list=new ArrayList();
 	   List<Map<String, Object>> maplist=getAllObject2ListMap(sql, objs);
 	   for (Map<String, Object> map2 : maplist) {
 		   	Map<String, Object> treeMap= new LinkedHashMap<String, Object>();
 		   	treeMap.put("cls", map.get("cls"));
 			
 		   	for (String string : map.keySet()) {
 		   		if(string.equals("cls")){
 		   			continue;
 		   		}
 		   		if(!string.equals("leaf")){
 		   			treeMap.put(string, map2.get(map.get(string)));
 		   		}else{
 		   			if(map2.get(ChildCount).equals("0")||Integer.parseInt(map2.get(ChildCount)+"")==0){
 			   			treeMap.put("leaf", true);
 			   		}else{
 			   			treeMap.put("leaf", false);
 			   			Object[] obj=new Object[objs.length];
 			   			for (int i = 0; i < obj.length; i++) {
 							obj[i]=map2.get(map.get("id"));
 						}
 			   			treeMap.put("children", getTreeJson(map, ChildCount, sql, obj ));
 			   		}
 		   		}
 		   		
 			}
 			list.add(treeMap);
 	   }
 	   result=JSONArray.fromObject(list).toString();
 	   return result;
    }

    /**
     * 一次获取整颗树
     * @param map 数Map  key存放树属性，value存放数属性值
     * 			
     * @param ChildCount  包含子节点数量列名
     * @param sql 查询Sql语句
     * @param objs 参数（Sql语句中参数不能使用加号连接）
     * @return json字符串
     * @throws Exception
     */
    public List getTreeJson2(Map<String, Object> map,String ChildCount,String sql,Object[] objs) throws Exception{
  	   String result="";
  	   List list=new ArrayList();
  	   List<Map<String, Object>> maplist=getAllObject2ListMap(sql, objs);
  	   for (Map<String, Object> map2 : maplist) {
  		   	Map<String, Object> treeMap= new HashMap<String, Object>();
  		   	for (String string : map.keySet()) {
  		   			treeMap.put(string, map2.get(map.get(string)));
  		   	}
  		   			if(map2.get(ChildCount).equals("0")||Integer.parseInt(map2.get(ChildCount)+"")==0){
  			   		}else{
  							objs[0]=map2.get(map.get("id"));
  			   			treeMap.put("children", getTreeJson2(map, ChildCount, sql, objs ));
  			   		}
  			list.add(treeMap);
  	   }
  	   return list;
     }
    

 	
   
   public List<Map<String, Object>>  getTreeJson2(Map<String,String> requestParams,String parentId) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSetMetaData rsmd=null;
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			
			// 获取前台提交的联动参数
			String node = parentId;
			String refer = StringUtil.showNull(requestParams.get("refer"));
			String refer1 =StringUtil.showNull(requestParams.get("refer1"));
			String refer2 =StringUtil.showNull(requestParams.get("refer2"));			
			String checked =StringUtil.showNull(requestParams.get("checked"));
			String strinitsql =StringUtil.showNull(requestParams.get("strinitsql"));
			String strsql =StringUtil.showNull(requestParams.get("strsql"));
			String strchecksql =StringUtil.showNull(requestParams.get("strchecksql"));
			
			String sql = strinitsql;
			
			if (!"".equals(node)) {
				// 多级展开时，取第二条sql
				if(node.startsWith("root")) {
					sql = strinitsql;
				}else {
					sql = strsql.replaceAll("\\$1", node);
				}							
			}		

			String strCurUserId =requestParams.get("strCurUserId");;
			String curDepartmentid =requestParams.get("curDepartmentid");;
			String curUnitId =requestParams.get("curUnitId");
			String curCtlUnitId =requestParams.get("curCtlUnitId");

			String oralSql = sql;
			if (sql != null && !"".equals(sql)) {

				if (refer != null)
					oralSql = oralSql.replaceAll("\\$2", refer);
				if (refer1 != null)
					oralSql = oralSql.replaceAll("\\$3", refer1);
				if (refer2 != null)
					oralSql = oralSql.replaceAll("\\$4", refer2);
				if (strCurUserId != null)
					oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
				if (curDepartmentid != null)
					oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
				
				if (curCtlUnitId != null) {
					oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
				}
				
				ps2=conn.prepareStatement(oralSql);
				rs2=ps2.executeQuery();
				rsmd=rs2.getMetaData();
				while(rs2.next()){
					Map<String, Object> treeMap= new HashMap<String, Object>();

					treeMap.put("text", rs2.getString(2));
					treeMap.put("id", rs2.getString(1));
					//如果有ICON图标则获取图标
					String iconCls = "";
					if(isExistColumn(rs2,"ICON")){
						iconCls = StringUtil.showNull(rs2.getString("ICON"));
					}
					if(!"".equals(iconCls)){
						treeMap.put("iconCls",iconCls);
					}
					//如果有clickUrl则获取clickUrl
					String clickUrl = "";
					if(isExistColumn(rs2,"URL")){
						clickUrl = StringUtil.showNull(rs2.getString("URL"));
					}
					if(!"".equals(clickUrl)){
						treeMap.put("clickUrl",clickUrl);
					}
					
					oralSql=strsql.replaceAll("\\$1", rs2.getString(1));
					if (refer != null)
						oralSql = oralSql.replaceAll("\\$2", refer);
					if (refer1 != null)
						oralSql = oralSql.replaceAll("\\$3", refer1);
					if (refer2 != null)
						oralSql = oralSql.replaceAll("\\$4", refer2);
					if (strCurUserId != null)
						oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
					if (curDepartmentid != null)
						oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
					
					if (curCtlUnitId != null) {
						oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
					}	
					
					if(checked.equals("true")){
						String checkSql=strchecksql.replaceAll("\\$1",rs2.getString(1));
						if (refer != null)
							checkSql = checkSql.replaceAll("\\$2", refer);
						if (refer1 != null)
							checkSql = checkSql.replaceAll("\\$3", refer1);
						if (refer2 != null)
							checkSql = checkSql.replaceAll("\\$4", refer2);
						if (strCurUserId != null)
							checkSql = checkSql.replaceAll("\\$CURUSER", strCurUserId);
						if (curDepartmentid != null)
							checkSql = checkSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$curUnitId",curUnitId);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$CURUNITID",curUnitId);	
						if (curCtlUnitId != null) {
							checkSql = checkSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
						}						
						
						Object[] checkVal = new DbUnits(conn).getOneObject2Array(checkSql,null);

						if(checkVal == null){
							treeMap.put("checked",false);
						}else{
							treeMap.put("checked",true);
						}
					}
					//System.out.println(oralSql);
					Object[] test=new DbUnits(conn).getOneObject2Array(oralSql,null);
					boolean isLeaf=false;
					if(test==null){
						isLeaf=true;
						if("".equals(iconCls)){
							treeMap.put("iconCls","icon-leaf");
						}
						//treeMap.put("state", "open");
					}else{
						//treeMap.put("state", "open");//不展开节点
						treeMap.put("children",this.getTreeJson2(requestParams, rs2.getString(1)));
						treeMap.put("checked",false);
					}
					list.add(treeMap);						
				}
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs2);
			DbUtil.close(rs);
			DbUtil.close(ps2);
			DbUtil.close(ps);
		}
		
		return list;
  }
   
   public List<Map<String, Object>>  getTreeJson3(Map<String,String> requestParams,String parentId) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSetMetaData rsmd=null;
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			
			// 获取前台提交的联动参数
			String node = parentId;
			String refer = StringUtil.showNull(requestParams.get("refer"));
			String refer1 =StringUtil.showNull(requestParams.get("refer1"));
			String refer2 =StringUtil.showNull(requestParams.get("refer2"));			
			String checked =StringUtil.showNull(requestParams.get("checked"));
			String strinitsql =StringUtil.showNull(requestParams.get("strinitsql"));
			String strsql =StringUtil.showNull(requestParams.get("strsql"));
			String strchecksql =StringUtil.showNull(requestParams.get("strchecksql"));
			String id = StringUtil.showNull(requestParams.get("id"));
			String state = StringUtil.showNull(requestParams.get("state"));
			String attributes = StringUtil.showNull(requestParams.get("attributes"));
			String sql = strinitsql;
			
			if (!"".equals(node)) {
				// 多级展开时，取第二条sql
				if(node.startsWith("root")) {
					sql = strinitsql;
				}else {
					sql = strsql.replaceAll("\\$1", node);
				}							
			}		

			if(!"".equals(id)){
				sql = strsql.replaceAll("\\$1", id);
			}
			String strCurUserId =requestParams.get("strCurUserId");;
			String curDepartmentid =requestParams.get("curDepartmentid");;
			String curUnitId =requestParams.get("curUnitId");
			String curCtlUnitId =requestParams.get("curCtlUnitId");

			String oralSql = sql;
			if (sql != null && !"".equals(sql)) {

				if (refer != null)
					oralSql = oralSql.replaceAll("\\$2", refer);
				if (refer1 != null)
					oralSql = oralSql.replaceAll("\\$3", refer1);
				if (refer2 != null)
					oralSql = oralSql.replaceAll("\\$4", refer2);
				if (strCurUserId != null)
					oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
				if (curDepartmentid != null)
					oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
				
				if (curCtlUnitId != null) {
					oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
				}
				
				ps2=conn.prepareStatement(oralSql);
				rs2=ps2.executeQuery(); 
				rsmd=rs2.getMetaData();
				while(rs2.next()){
					Map<String, Object> treeMap= new HashMap<String, Object>();
					Map<String, Object> treeAttributesMap= new HashMap<String, Object>();
					treeMap.put("text", rs2.getString(2));
					treeMap.put("id", rs2.getString(1));
					//如果有ICON图标则获取图标
					String iconCls = "";
					if(isExistColumn(rs2,"ICON")){
						iconCls = StringUtil.showNull(rs2.getString("ICON"));
					}
					if(!"".equals(iconCls)){
						treeMap.put("iconCls",iconCls);
					}
					//如果有clickUrl则获取clickUrl
					String clickUrl = "";
					if(isExistColumn(rs2,"URL")){
						clickUrl = StringUtil.showNull(rs2.getString("URL"));
					}
					if(!"".equals(clickUrl)){
						treeMap.put("clickUrl",clickUrl);
					}
					
					oralSql=strsql.replaceAll("\\$1", rs2.getString(1));
					if (refer != null)
						oralSql = oralSql.replaceAll("\\$2", refer);
					if (refer1 != null)
						oralSql = oralSql.replaceAll("\\$3", refer1);
					if (refer2 != null)
						oralSql = oralSql.replaceAll("\\$4", refer2);
					if (strCurUserId != null)
						oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
					if (curDepartmentid != null)
						oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
					
					if (curCtlUnitId != null) {
						oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
					}	
					
					if(checked.equals("true")){
						String checkSql=strchecksql.replaceAll("\\$1",rs2.getString(1));
						if (refer != null)
							checkSql = checkSql.replaceAll("\\$2", refer);
						if (refer1 != null)
							checkSql = checkSql.replaceAll("\\$3", refer1);
						if (refer2 != null)
							checkSql = checkSql.replaceAll("\\$4", refer2);
						if (strCurUserId != null)
							checkSql = checkSql.replaceAll("\\$CURUSER", strCurUserId);
						if (curDepartmentid != null)
							checkSql = checkSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$curUnitId",curUnitId);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$CURUNITID",curUnitId);	
						if (curCtlUnitId != null) {
							checkSql = checkSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
						}						
						
						Object[] checkVal = new DbUnits(conn).getOneObject2Array(checkSql,null);

						if(checkVal == null){
							treeMap.put("checked",false);
						}else{
							treeMap.put("checked",true);
						}
					}
					
					Object[] test=new DbUnits(conn).getOneObject2Array(oralSql,null);
					boolean isLeaf=false;
					if(test==null){
						isLeaf=true;
						//如果没有指定图标则默认图标
						if("".equals(iconCls)){
							treeMap.put("iconCls","icon-leaf");
						}
						//treeMap.put("state", "open");
					}else{
						if("closed".equals(state)){
							treeMap.put("state", "closed");
						}else{
							treeMap.put("children",this.getTreeJson2(requestParams, rs2.getString(1)));

						}
						//treeMap.put("state", "open");//不展开节点
					}
					if("true".equals(attributes)){
						for(int i=3;i<=rsmd.getColumnCount();i++){
		 					if(rsmd.getColumnLabel(i).equals("_id")||rsmd.getColumnLabel(i).equals("_parent")||rsmd.getColumnLabel(i).equals("_is_leaf")){
		 						treeAttributesMap.put(rsmd.getColumnLabel(i), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
		 					}else{
		 						treeAttributesMap.put(rsmd.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
		 					}				
		 				}
						treeMap.put("attributes", treeAttributesMap);
					}else if ("gridtrue".equals(attributes)){
						for(int i=3;i<=rsmd.getColumnCount();i++){
		 					if(rsmd.getColumnLabel(i).equals("_id")||rsmd.getColumnLabel(i).equals("_parent")||rsmd.getColumnLabel(i).equals("_is_leaf")){
		 						treeMap.put(rsmd.getColumnLabel(i), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
		 					}else{
		 						treeMap.put(rsmd.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
		 					}				
		 				}
					}
					
					
					list.add(treeMap);						
				}
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs2);
			DbUtil.close(rs);
			DbUtil.close(ps2);
			DbUtil.close(ps);
		}
		
		return list;
 }
   /**
    * 
    * @param requestParams
    * @param parentId
    * @return  重新构造树的JSON,使其有TREE的属性,异步加载
    * @throws Exception
    */
   public List<Map<String, Object>>  getTreeGridJson(Map<String,String> requestParams,String parentId) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSetMetaData rsmd=null;
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			
			// 获取前台提交的联动参数
			String node = parentId;
			String refer = StringUtil.showNull(requestParams.get("refer"));
			String refer1 =StringUtil.showNull(requestParams.get("refer1"));
			String refer2 =StringUtil.showNull(requestParams.get("refer2"));			
			String checked =StringUtil.showNull(requestParams.get("checked"));
			String strinitsql =StringUtil.showNull(requestParams.get("strinitsql"));
			String strsql =StringUtil.showNull(requestParams.get("strsql"));
			String strchecksql =StringUtil.showNull(requestParams.get("strchecksql"));
			String isopen =StringUtil.showNull(requestParams.get("isopen"));
			String sql = strinitsql;
			
			if (!"".equals(node)) {
				// 多级展开时，取第二条sql
				if(node.startsWith("root")) {
					sql = strinitsql;
				}else {
					sql = strsql.replaceAll("\\$1", node);
				}							
			}		

			String strCurUserId =requestParams.get("strCurUserId");;
			String curDepartmentid =requestParams.get("curDepartmentid");;
			String curUnitId =requestParams.get("curUnitId");
			String curCtlUnitId =requestParams.get("curCtlUnitId");

			String oralSql = sql;
			if (sql != null && !"".equals(sql)) {

				if (refer != null)
					oralSql = oralSql.replaceAll("\\$2", refer);
				if (refer1 != null)
					oralSql = oralSql.replaceAll("\\$3", refer1);
				if (refer2 != null)
					oralSql = oralSql.replaceAll("\\$4", refer2);
				if (strCurUserId != null)
					oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
				if (curDepartmentid != null)
					oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
				
				if (curCtlUnitId != null) {
					oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
				}
				
				ps2=conn.prepareStatement(oralSql);
				rs2=ps2.executeQuery();
				rsmd=rs2.getMetaData();
				while(rs2.next()){
	 				
					Map<String, Object> treeMap= new HashMap<String, Object>();

					for(int i=1;i<=rsmd.getColumnCount();i++){
	 					if(rsmd.getColumnLabel(i).equals("_id")||rsmd.getColumnLabel(i).equals("_parent")||rsmd.getColumnLabel(i).equals("_is_leaf")){
	 						treeMap.put(rsmd.getColumnLabel(i), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
	 					}else{
	 						treeMap.put(rsmd.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
	 					}				
	 				}
					oralSql=strsql.replaceAll("\\$1", rs2.getString(1));
					if (refer != null)
						oralSql = oralSql.replaceAll("\\$2", refer);
					if (refer1 != null)
						oralSql = oralSql.replaceAll("\\$3", refer1);
					if (refer2 != null)
						oralSql = oralSql.replaceAll("\\$4", refer2);
					if (strCurUserId != null)
						oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
					if (curDepartmentid != null)
						oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
					
					if (curCtlUnitId != null) {
						oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
					}	
					
					if(checked.equals("true")){
						String checkSql=strchecksql.replaceAll("\\$1",rs2.getString(1));
						if (refer != null)
							checkSql = checkSql.replaceAll("\\$2", refer);
						if (refer1 != null)
							checkSql = checkSql.replaceAll("\\$3", refer1);
						if (refer2 != null)
							checkSql = checkSql.replaceAll("\\$4", refer2);
						if (strCurUserId != null)
							checkSql = checkSql.replaceAll("\\$CURUSER", strCurUserId);
						if (curDepartmentid != null)
							checkSql = checkSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$curUnitId",curUnitId);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$CURUNITID",curUnitId);	
						if (curCtlUnitId != null) {
							checkSql = checkSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
						}						
						
						Object[] checkVal = new DbUnits(conn).getOneObject2Array(checkSql,null);

						if(checkVal == null){
							treeMap.put("checked",false);
						}else{
							treeMap.put("checked",true);
						}
					}
					
					Object[] test=new DbUnits(conn).getOneObject2Array(oralSql,null);
					boolean isLeaf=false;
					if(test==null){
						isLeaf=true;
						treeMap.put("state", "open");//不展开节点
						treeMap.put("iconCls","icon-leaf");
					}else{
						treeMap.put("state", "open");//不展开节点
						treeMap.put("children",this.getTreeGridJson(requestParams, rs2.getString(1)));
					}
					list.add(treeMap);						
				}
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs2);
			DbUtil.close(rs);
			DbUtil.close(ps2);
			DbUtil.close(ps);
		}
		
		return list;
 }
   /**
    * 
    * @param requestParams
    * @param parentId
    * @return  重新构造GRID树的JSON,异步加载
    * @throws Exception
    */
   public List<Map<String, Object>>  getTreeGridJson2(Map<String,String> requestParams,String parentId) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSetMetaData rsmd=null;
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			
			// 获取前台提交的联动参数
			String node = parentId;
			String refer = StringUtil.showNull(requestParams.get("refer"));
			String refer1 =StringUtil.showNull(requestParams.get("refer1"));
			String refer2 =StringUtil.showNull(requestParams.get("refer2"));			
			String checked =StringUtil.showNull(requestParams.get("checked"));
			String strinitsql =StringUtil.showNull(requestParams.get("strinitsql"));
			String strsql =StringUtil.showNull(requestParams.get("strsql"));
			String strchecksql =StringUtil.showNull(requestParams.get("strchecksql"));
			String id =StringUtil.showNull(requestParams.get("id"));
			String sql = strinitsql;
			
			if (!"".equals(node)) {
				// 多级展开时，取第二条sql
				if(node.startsWith("root")) {
					sql = strinitsql;
				}else {
					sql = strsql.replaceAll("\\$1", node);
				}							
			}		
			if (!"".equals(id)) {
				sql = strsql.replaceAll("\\$1", id);
			}
			String strCurUserId =requestParams.get("strCurUserId");;
			String curDepartmentid =requestParams.get("curDepartmentid");;
			String curUnitId =requestParams.get("curUnitId");
			String curCtlUnitId =requestParams.get("curCtlUnitId");

			String oralSql = sql;
			if (sql != null && !"".equals(sql)) {

				if (refer != null)
					oralSql = oralSql.replaceAll("\\$2", refer);
				if (refer1 != null)
					oralSql = oralSql.replaceAll("\\$3", refer1);
				if (refer2 != null)
					oralSql = oralSql.replaceAll("\\$4", refer2);
				if (strCurUserId != null)
					oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
				if (curDepartmentid != null)
					oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
				if (curUnitId != null)
					oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
				
				if (curCtlUnitId != null) {
					oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
				}
				
				ps2=conn.prepareStatement(oralSql);
				rs2=ps2.executeQuery();
				rsmd=rs2.getMetaData();
				while(rs2.next()){
	 				
					Map<String, Object> treeMap= new HashMap<String, Object>();

					for(int i=1;i<=rsmd.getColumnCount();i++){
	 					if(rsmd.getColumnLabel(i).equals("_id")||rsmd.getColumnLabel(i).equals("_parent")||rsmd.getColumnLabel(i).equals("_is_leaf")){
	 						treeMap.put(rsmd.getColumnLabel(i), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
	 					}else{
	 						treeMap.put(rsmd.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
	 					}				
	 				}
					oralSql=strsql.replaceAll("\\$1", rs2.getString(1));
					if (refer != null)
						oralSql = oralSql.replaceAll("\\$2", refer);
					if (refer1 != null)
						oralSql = oralSql.replaceAll("\\$3", refer1);
					if (refer2 != null)
						oralSql = oralSql.replaceAll("\\$4", refer2);
					if (strCurUserId != null)
						oralSql = oralSql.replaceAll("\\$CURUSER", strCurUserId);
					if (curDepartmentid != null)
						oralSql = oralSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$curUnitId",curUnitId);	
					if (curUnitId != null)
						oralSql = oralSql.replaceAll("\\$CURUNITID",curUnitId);	
					
					if (curCtlUnitId != null) {
						oralSql = oralSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
					}	
					
					if(checked.equals("true")){
						String checkSql=strchecksql.replaceAll("\\$1",rs2.getString(1));
						if (refer != null)
							checkSql = checkSql.replaceAll("\\$2", refer);
						if (refer1 != null)
							checkSql = checkSql.replaceAll("\\$3", refer1);
						if (refer2 != null)
							checkSql = checkSql.replaceAll("\\$4", refer2);
						if (strCurUserId != null)
							checkSql = checkSql.replaceAll("\\$CURUSER", strCurUserId);
						if (curDepartmentid != null)
							checkSql = checkSql.replaceAll("\\$CURDEPARTMENTID",curDepartmentid);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$curUnitId",curUnitId);	
						if (curUnitId != null)
							checkSql = checkSql.replaceAll("\\$CURUNITID",curUnitId);	
						if (curCtlUnitId != null) {
							checkSql = checkSql.replaceAll("\\$CURCTLUNITID", curCtlUnitId);
						}						
						
						Object[] checkVal = new DbUnits(conn).getOneObject2Array(checkSql,null);

						if(checkVal == null){
							treeMap.put("checked",false);
						}else{
							treeMap.put("checked",true);
						}
					}
					
					Object[] test=new DbUnits(conn).getOneObject2Array(oralSql,null);
					boolean isLeaf=false;
					if(test==null){
						isLeaf=true;
						treeMap.put("state", "open");//不展开节点
						treeMap.put("iconCls","icon-leaf");
					}else{
						treeMap.put("state", "closed");//不展开节点
						//treeMap.put("children",this.getTreeGridJson(requestParams, rs2.getString(1)));
					}
					list.add(treeMap);						
				}
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs2);
			DbUtil.close(rs);
			DbUtil.close(ps2);
			DbUtil.close(ps);
		}
		
		return list;
 }
   
   
   /**
    * 
    * 点击节点添加下一级节点
    * 
    * @param Map<String, Object> map 数Map  key存放树属性，value存放数属性值
    * @param String 			  查询Sql语句
    * @param Object[]            参数（Sql语句中参数不能使用加号连接）
    * 
    * @return json字符串
    * @throws Exception
    */
   public String getTreeJson(Map<String, Object> map,String sql,Object[] objs) throws Exception{
	   String result="";
	   List menu = new ArrayList();
	   List list=new ArrayList();
	   List<Map<String, Object>> maplist=getAllObject2ListMap(sql, objs);
	   Map<String, Object> treeMap1= new LinkedHashMap<String, Object>();
	   treeMap1.put("id","1");
	   treeMap1.put("text","字典类别");
	   
	   for (Map<String, Object> map2 : maplist) {
		   	Map<String, Object> treeMap= new LinkedHashMap<String, Object>();
		   	treeMap.put("cls", map.get("cls"));
			treeMap.put("leaf", map.get("leaf"));
		   	for (String string : map.keySet()) {
		   		if(string.equals("cls")||string.equals("leaf")){
		   			continue;
		   		}
		   		treeMap.put(string, map2.get(map.get(string)));
			}
			list.add(treeMap);
	   }
	   treeMap1.put("children", list);
	   menu.add(treeMap1);
	   result=JSONArray.fromObject(menu).toString();
	   return result;
   }
 	  
	
	
	
	/**
	 * 不带分页的gridJson
	 * @author GX
	 * @date   2016年3月15日 下午2:17:47
	 * @QQ     619314981
	 * @param request
	 * @param response
	 * @param sql
	 * @return
	 */
	public String getGridJsonDate2notPage(HttpServletRequest request , HttpServletResponse response,String sql){
		String treeJson = "";
		Map<String, String > map = new HashMap<String, String>();
		try {
			conn = new DBConnect().getConnect();
			DbUnits db = new DbUnits(conn);
			List list = db.getAllObject2ListMap(sql, null);
			
			Integer counts = list.size();
			treeJson = "{\"rows\":" + JSONArray.fromObject(list).toString();
			if(counts==null){
				treeJson+=",\"total\":0,\"success\":true}";
			}else{
				treeJson+=",\"total\":"+counts+",\"success\":true}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
		}
		return treeJson;
	}
	
	public String getGridJsonData2(HttpServletRequest request,
			HttpServletResponse response,String oraSql){

		String treeJson="";
		String qryWhere = StringUtil.showNull(request.getParameter("qryWhere"));
		String start = StringUtil.showNull(request.getParameter("page"));//datagrid控件的pagination属性为true时才会传这个参数的值，为false时则为空
		String limit = StringUtil.showNull(request.getParameter("rows"));//datagrid控件的pagination属性为true时才会传这个参数的值，为false时则为空
		String sort = StringUtil.showNull(request.getParameter("sort"));
		String dir = StringUtil.showNull(request.getParameter("order"));//原来是request.getParameter("dir")的(cf改)
		String isUserPage = StringUtil.showNull(request.getAttribute("USERPAGE"));
		if(qryWhere.trim().startsWith("and checker")){
			qryWhere="";
		}
		if(!qryWhere.equals("") && !qryWhere.trim().startsWith("and")){
			if(qryWhere.trim().equals("all")){
				qryWhere="";
			}else{
				qryWhere=" and "+qryWhere;
			}
		};

		Connection conn = null;

		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			conn = new DBConnect().getConnect();

			UserSession us = (UserSession) request.getSession().getAttribute("userSession");
			if (us == null)
				us = new UserSession();

			String strCurUserId = us.getUserId();
			
			
			
			String countSql="SELECT COUNT(*) FROM ("+oraSql+") WHERE 1=1 "+qryWhere;
			Object[] counts=this.getOneObject2Array(countSql,null);
			String strSql ="";

			if(oraSql.indexOf("ROWNUM")>0){
				strSql=oraSql;
			}else{
				if("".equals(sort)){
					strSql = "SELECT T.*,ROWNUM FROM ("+oraSql+") T WHERE 1=1 "+qryWhere;
				}else{
					strSql = "SELECT T.*,ROWNUM  FROM ("+oraSql+") T WHERE 1=1 "+qryWhere+" ORDER BY  "+sort+" "+dir;	
				}						
			}
			
			
			//datagrid控件的pagination属性为true时才会传这个参数的值，为false时则为空,如果为空则不分页(cf改)
			if(!"".equals(start) && !"".equals(limit)){
				
				int startNum = Integer.parseInt("".equals(start)?"1":start)-1;
				int limitNum = Integer.parseInt("".equals(limit)?"1":limit);
				int num = startNum*limitNum;
				if(!isUserPage.equalsIgnoreCase("no")){
					strSql = "select *  from ( \n"
							+ "select COMBOX_SQL.* , ROWNUM COMBOX_ROWNUM  from ( \n"
							+ strSql + ") COMBOX_SQL \n"
							+ " limit "+num+","+limitNum+") \n"
							+ " where COMBOX_ROWNUM > " + startNum
							+ "\n";				
				}
			}

			list=this.getAllObject3ListMapUpper(strSql, null);
			
			treeJson = "{\"rows\":";
			treeJson=treeJson+JSONArray.fromObject(list).toString();
			if(counts==null){
				treeJson+=",\"total\":0,\"success\":true}";
			}else{
				treeJson+=",\"total\":"+counts[0]+",\"success\":true}";
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			DbUtil.close(conn);
		}
		return treeJson;			
	}
	
	
	/**
	 * 
	 * 页面树构造通用数据显示列表控件
	 * 带统计
	 * 
	 */	
	public String getGridJsonDataTotal(HttpServletRequest request,
			HttpServletResponse response,String oraSql){

		String treeJson="";
		String qryWhere = StringUtil.showNull(request.getParameter("qryWhere"));
		String start = StringUtil.showNull(request.getParameter("page"));
		String limit = StringUtil.showNull(request.getParameter("rows"));
		String sort = StringUtil.showNull(request.getParameter("sort"));
		String dir = StringUtil.showNull(request.getParameter("dir"));
		String isUserPage = StringUtil.showNull(request.getAttribute("USERPAGE"));
		if(qryWhere.trim().startsWith("and checker")){
			qryWhere="";
		}
		if(!qryWhere.equals("") && !qryWhere.trim().startsWith("and")){
			if(qryWhere.trim().equals("all")){
				qryWhere="";
			}else{
				qryWhere=" and "+qryWhere;
			}
		};

		Connection conn = null;

		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		try {
			conn = new DBConnect().getConnect();

			UserSession us = (UserSession) request.getSession().getAttribute("userSession");
			if (us == null)
				us = new UserSession();

			String strCurUserId = us.getUserId();
			
			
			
			String countSql="SELECT COUNT(*) FROM ("+oraSql+") WHERE 1=1 "+qryWhere;
			Object[] counts=this.getOneObject2Array(countSql,null);
			String strSql ="";

			if(oraSql.indexOf("ROWNUM")>0){
				strSql=oraSql;
			}else{
				if("".equals(sort)){
					strSql = "SELECT T.*,ROWNUM FROM ("+oraSql+") T WHERE 1=1 "+qryWhere;
				}else{
					strSql = "SELECT T.*,ROWNUM  FROM ("+oraSql+") T WHERE 1=1 "+qryWhere+" ORDER BY  "+sort+" "+dir;	
				}						
			}
			
			
			int startNum = Integer.parseInt("".equals(start)?"1":start)-1;
			int limitNum = Integer.parseInt("".equals(limit)?"1":limit);
			int num = startNum*limitNum;
			if(!isUserPage.equalsIgnoreCase("no")){
				strSql = "select *  from ( \n"
					+ "select COMBOX_SQL.* , ROWNUM COMBOX_ROWNUM  from ( \n"
					+ strSql + ") COMBOX_SQL \n"
					+ " limit "+num+","+limitNum+") \n"
					+ " where COMBOX_ROWNUM > " + startNum
					+ "\n";				
			}

			//System.out.println("strSql===="+strSql);
			list=this.getAllObject2ListMapUpper(strSql, null);
			double a =0;
			double b =0;
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						a+= Double.parseDouble(list.get(i).get("ITEMPOINT").toString());
						b+= Double.parseDouble(list.get(i).get("POINT").toString());
				}
			}
			treeJson = "{\"rows\":";
			treeJson=treeJson+JSONArray.fromObject(list).toString();
			if(counts==null){
				treeJson+=",\"total\":0,\"success\":true,\"footer\":[{\"ITEMDESC\":\"<div style=\'text-align:right\'>标准分/总分</style>:\",\"ITEMPOINT\":"+a+",\"POINT\":\"<span id = \'grade\' name = \'grade\'>"+b+"</span>\",\"iconCls\":\"icon-sum\"}]}";
			}else{
				treeJson+=",\"total\":"+counts[0]+",\"success\":true,\"footer\":[{\"ITEMDESC\":\"<div style=\'text-align:right\'>标准分/总分</style>:\",\"ITEMPOINT\":"+a+",\"POINT\":\"<span id = \'grade\' name = \'grade\'>"+b+"</span>\",\"iconCls\":\"icon-sum\"}]}";
			}
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			DbUtil.close(conn);
		}
		return treeJson;			
	}
	
 		
	/**
	 * 
	 * 页面树构造通用数据显示列表控件
	 * 
	 * 通过回显的数据库进行配置
	 * 
	 */	
	public String getBackQueryGridJsonData(String userId,String tableId,String start,String limit){

		String treeJson="";
		/*
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();

		try {
			conn = new DBConnect().getConnect();
			
			GridSql gridSql=new SystemService(conn).getGridSql(userId, tableId);
			
			if(gridSql==null){
				return treeJson;
			};
			
			String countSql=gridSql.getCountSql();
			Object[] counts=this.getOneObject2Array(countSql,null);
			
			String strSql =gridSql.getFinishSql();
			
			int startNum = Integer.parseInt(start);
			int limitNum = Integer.parseInt(limit);
			
			strSql = "select *  from ( \n"
				+ "select COMBOX_SQL.* , ROWNUM COMBOX_ROWNUM  from ( \n"
				+ strSql + ") COMBOX_SQL \n"
				+ " where ROWNUM <= "
				+ (startNum + limitNum) + " ) \n"
				+ " where COMBOX_ROWNUM > " + startNum
				+ "\n";
			
			list=this.getAllObject2ListMap(strSql, null);
			
			ps = conn.prepareStatement(strSql);
			rs=ps.executeQuery();

			treeJson = "{\"results\":";
			treeJson=treeJson+JSONArray.fromObject(list).toString();
			if(counts==null){
				treeJson+=",\"totalCount\":0}";
			}else{
				treeJson+=",\"totalCount\":"+counts[0]+"}";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}finally{
			DbUtil.close(rs);
			DbUtil.close(ps);
			DbUtil.close(conn);
		}*/
		return treeJson;			
	}		
	/**
	 * 
	 * 页面树构造通用数据显示列表控件
	 * 
	 * 
	 */	
	public String getGridJsonData(ResultSet rs){

		String treeJson="";
		int count=0;
     	ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
     	PreparedStatement ps = null;
     	ResultSetMetaData rms=null;
 	    try{
 			rms=rs.getMetaData();
 			while(rs.next()){
 				count++;
 				Map<String, Object> map = new LinkedHashMap<String, Object>();
 				for(int i=1;i<=rms.getColumnCount();i++){
 					if(rms.getColumnLabel(i).equals("_id")||rms.getColumnLabel(i).equals("_parent")||rms.getColumnLabel(i).equals("_is_leaf")){
 	 					map.put(rms.getColumnLabel(i), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}else{
 	 					map.put(rms.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs.getObject(rms.getColumnLabel(i)), rms.getColumnType(i)));
 					}				
 				}
 				list.add(map);
 			}
     	}catch(Exception e){
     		log.error(e.getMessage(),e);
     	}finally{
     		DbUtil.close(rs);
     		DbUtil.close(ps);
     	}
	     	
		treeJson = "{\"results\":";
		treeJson=treeJson+JSONArray.fromObject(list).toString();
		treeJson+=",\"totalCount\":"+count+",\"success\":true}";

		return treeJson;			
	}	
	
	public static String getOneValue(Connection conn, String sql) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(ps);
		}
		return null;
	}
	
	/**
	 * 
	 * 设置查看全部部门功能
	 * 
	 */
	public boolean isCheckAll(String deptId){
		boolean all = true;
		String isall="";
		String isallsql = "select isall from MT_BS_DEPT  where dept_id=? ";
		Object[] obj = getOneObject2Array(isallsql, new Object[]{deptId});
		if(obj != null && obj[0] != null){
			isall = StringUtil.showNull(obj[0]);
		}
		if(!"0".equals(isall)){
			all = false;
		}
		return all;
	}
	
	
	
	  /**
	 * @author GX
	 * @date   2016年3月26日 下午3:56:13
	 * @QQ     619314981
	 * @param requestParams
	 * @param parentId
	 * @return
	 * @throws Exception
	 * 
	 * 简单的构造树
	 */
	public List<Map<String, Object>>  getSimpleTreeJson(Map<String,String> requestParams,String parentId) throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSetMetaData rsmd=null;
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
		
			String node = parentId;
			String refer = StringUtil.showNull(requestParams.get("refer"));
			String refer1 =StringUtil.showNull(requestParams.get("refer1"));
			String strinitsql =StringUtil.showNull(requestParams.get("strinitsql"));
			String strsql =StringUtil.showNull(requestParams.get("strsql"));
			String strchecksql = StringUtil.showNull(requestParams.get("strchecksql"));
			String check1 =StringUtil.showNull(requestParams.get("check1"));
			String attributes = StringUtil.showNull(requestParams.get("attributes"));
			String checked = StringUtil.showNull(requestParams.get("checked"));
			String id = StringUtil.showNull(requestParams.get("id"));
			String state = StringUtil.showNull(requestParams.get("state"));
			String sql = strinitsql;
			
			if (!"".equals(node)) {
				// 多级展开时，取第二条sql
				if(node.startsWith("root")) {
					sql = strinitsql;
				}else {
					sql = strsql.replaceAll("\\$1", node);
				}							
			}		
			if(!"".equals(id)){
				sql = strsql.replaceAll("\\$1", id);
			}
			String oralSql = sql;
			if (sql != null && !"".equals(sql)) {

				if (refer != null)
					oralSql = oralSql.replaceAll("\\$2", refer);
				if (refer1 != null)
					oralSql = oralSql.replaceAll("\\$3", refer1);
				
				ps2=conn.prepareStatement(oralSql);
				rs2=ps2.executeQuery(); 
				rsmd=rs2.getMetaData();
				while(rs2.next()){
					Map<String, Object> treeMap= new HashMap<String, Object>();
					Map<String, Object> treeAttributesMap= new HashMap<String, Object>();
					treeMap.put("id", rs2.getString(1));
					treeMap.put("text", rs2.getString(2));
					
					oralSql=strsql.replaceAll("\\$1", rs2.getString(1));
					Object[] test = null;
					if("".equals(oralSql)){
						
					}else{
						if (refer != null) oralSql = oralSql.replaceAll("\\$2", refer);
						if (refer1 != null) oralSql = oralSql.replaceAll("\\$3", refer1);
						test=new DbUnits(conn).getOneObject2Array(oralSql,null);
					}
					
					if("true".equals(checked)){
						String checkSql = strchecksql.replaceAll("\\$5", rs2.getString(1));
						if (check1 != null) checkSql = checkSql.replaceAll("\\$6", check1);
						Object[] checkVal = new DbUnits(conn).getOneObject2Array(checkSql,null);
						
						if(checkVal == null){
							treeMap.put("checked",false);
						}else{
							treeMap.put("checked",true);
						}
					}
					boolean isLeaf = false;
					if(test==null){
						isLeaf=true;
						treeMap.put("iconCls","icon-leaf");
					}else{
						if(state != null && "true".equals(state)){
							treeMap.put("state","open");
						}else{
							treeMap.put("state","closed");
						}
						treeMap.put("children",this.getSimpleTreeJson(requestParams, rs2.getString(1)));
					}
					
					if("true".equals(attributes)){
						for(int i=3;i<=rsmd.getColumnCount();i++){
		 					treeAttributesMap.put(rsmd.getColumnLabel(i).toUpperCase(), DbUtil.getStringValue(rs2.getObject(rsmd.getColumnLabel(i)), rsmd.getColumnType(i)));
		 				}
						treeMap.put("attributes", treeAttributesMap);
					}
					list.add(treeMap);						
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs2);
			DbUtil.close(rs);
			DbUtil.close(ps2);
			DbUtil.close(ps);
		}
		return list;
 }
	
	/**
	 * @author GX
	 * @date   2016年3月16日 下午9:48:13
	 * @QQ     619314981
	 * @param sql
	 * @param request 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getGridJson(String sql, HttpServletRequest request) {
	
		String page = StringUtil.showNull(request.getParameter("page"));
		String rows = StringUtil.showNull(request.getParameter("rows"));
		String sort = StringUtil.showNull(request.getParameter("sort"));
		String order = StringUtil.showNull(request.getParameter("order"));
		
		List list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rd = null;
		int pag = 0;
		int limt = 0;
		int start = 0;
		int end = 0;
		if(!"".equals(sort) && !"".equals(order)){
			sql += " order by " + sort +" "+ order ;
		}
		String pageSql  = sql;
		
		if(!"".equals(page) && !"".equals(rows)){
			pag = Integer.parseInt(page);
			limt = Integer.parseInt(rows);
			start = (pag-1)*limt+1;
			end = limt*pag;
			pageSql = " select * from ( select A.* , ROWNUM RN from ("
					+ sql+") A where rownum <= "
					+end+"  ) where RN >="+start;
		}
		
		
		try {
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();		
			rd = ps.getMetaData();
			while(rs.next()){
				Map map = new HashMap<String, String>();
				int i = rd.getColumnCount();
				for(int j = 1; j<=i;j++){
					map.put(rd.getColumnName(j), rs.getString(j));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 检查rs结果集是否存在columnName字段
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public boolean isExistColumn(ResultSet rs,String columnName){
		try {
			if(rs.findColumn(columnName) > 0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return false;
	}
}
