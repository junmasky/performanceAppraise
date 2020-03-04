package com.util.db;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;


import com.util.log.Log;
import com.util.string.StringUtil;

/**
 *
 * 数据库专用操作类
 *
 */

public class DbUtil {
    private Connection conn;

    private static Log log = new Log(DbUtil.class) ;

    /**
     * 
     * 初始化对象，需要传入CONN对象
     * 
     * @param conn DBConnect
     */
    public DbUtil(Connection conn) throws Exception {
        this.conn = conn;
    }
    /**
     * 
     * 执行SQL得到记录集,请记住,这个记录集并没有关闭,
     * 请在调用本方法之后,显式的执行关闭记录rs.close()
     * 
     * @param strSql String
     * 
     * @return ResultSet
     * @throws Exception
     */
    public ResultSet getResultSet(String strSql) throws Exception {
        ResultSet rs= null;
        try {
            rs=conn.createStatement().executeQuery(strSql);
        } catch(Exception e){
            log.error("执行方法getResultSet(String strSql)出错:"+strSql,e);
            throw new Exception("执行sql出错",e);
        } 
        return rs;
    }
    /**
     * 
     * 执行SQL语句,执行成功返回true,否则记录日志,抛出异常,返回false
     * 
     * @param strSql String  要执行的SQL语句
     * 
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
            throw new Exception(e.getMessage(),e);
        } finally {
        	DbUtil.close(stmt);
        }
    }

    /**
     * 
     * 执行带参数的SQL语句,执行成功返回true,否则记录日志,抛出异常,返回false
     * 
     * @param strSql String  要执行的SQL语句
     * @param Object[] 参数  要求与SQl字符串参数顺序一致
     * 
     * @return boolean
     * @throws Exception
     */
    public boolean execute(String strSql, Object[] args) throws Exception {
    	PreparedStatement ps = null;
        try {
			if(args == null) {
				throw new Exception("参数不能为空!!");
			}

			ps = conn.prepareStatement(strSql);

			for(int i=0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}

			return ps.execute();

        } catch(Exception e){
            log.error("执行方法execute(String strSql, Object[] args)出错:" + strSql,e);
            throw new Exception(e.getMessage(),e);
        } finally {
        	DbUtil.close(ps);
        }
    }

    /**
     * 
     * 执行SQL语句,执行成功返回实际影响行数,否则抛出异常
     * 
     * @param String  要执行的SQL语句
     * 
     * @return int
     * @throws Exception
     */
    public int executeUpdate(String sql) throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch(Exception e){
            log.error("执行方法executeUpdate(String sql)出错:"+sql,e);
            throw new Exception(e.getMessage(),e);
        } finally {
        	DbUtil.close(stmt);
        }
    }

    /**
     * 
     * 执行带参数的SQL语句,执行成功返回实际影响行数,否则抛出异常
     * 
     * @param strSql String  要执行的SQL语句
     * @param Object[]  参数  要求与SQL语句参数顺序一致
     * @return int
     * @throws Exception
     */
    public int executeUpdate(String strSql, Object[] args) throws Exception {
    	PreparedStatement ps = null;
        try {
			if(args == null) {
				throw new Exception("参数不能为空!!");
			}

			ps = conn.prepareStatement(strSql);

			for(int i=0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}

			return ps.executeUpdate();

        } catch(Exception e){
            log.error("执行方法executeUpdate(String strSql, Object[] args)出错:" + strSql,e);
            throw new Exception(e.getMessage(),e);
        } finally {
        	DbUtil.close(ps);
        }
    }

    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回null
     * 
     * @param String  要执行的SQL语句
     * @param Object[]
     * 
     * @return Object类型,需要自己强制转换
     * @throws Exception
     */
    public Object queryForObject(String sql,Object[] args) throws Exception {
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         ResultSetMetaData rms=null;
         Object result = null;
         try {
        	 String strSql=DbUtil.getRows(sql, 1);
        	 ps = conn.prepareStatement(strSql);

        	 if(args == null) {
        		 args=new Object[0];
        	 }

        	 for(int i=0; i < args.length; i++) {
        		 ps.setObject(i+1, args[i]);
        	 }
             rs = ps.executeQuery();
             rms=rs.getMetaData();
             
             if(rs.next()) {
            	 result =DbUtil.getStringValue(rs.getObject(1), rms.getColumnType(1));
             }
             
         } catch(Exception e){
        	 log.error("执行方法queryForObject(String sql,Object[] args)出错:" + sql,e);
             throw new Exception(e.getMessage(),e);
         } finally {
        	 DbUtil.close(rs);
        	 DbUtil.close(ps);
         }
         return result;
    }
    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回null
     * 
     * @param strSql String  要执行的SQL语句
     * @param Object[]
     * 
     * @return Object类型,需要自己强制转换
     * @throws Exception
     */
    public String queryForBlob(String sql,Object[] args) throws Exception {
    	 log.debug(sql);
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         String result = null;
         try {
        	 String strSql=DbUtil.getRows(sql, 1);
        	 ps = conn.prepareStatement(strSql);

        	 if(args == null) {
        		 args=new Object[0];
        	 }

        	 for(int i=0; i < args.length; i++) {
        		 ps.setObject(i+1, args[i]);
        	 }
             rs = ps.executeQuery();

             if(rs.next()) {
            	 result = StringUtil.getBlobValue(rs.getBytes(1));
             }
             
         } catch(Exception e){
        	 log.error("执行方法queryForObject(String sql,Object[] args)出错:" + sql,e);
             throw new Exception(e.getMessage(),e);
         } finally {
        	 DbUtil.close(rs);
        	 DbUtil.close(ps);
         }
         return result;
    }
    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回null
     * 
     * @param strSql String  要执行的SQL语句
     * @param Object[]
     * 
     * @return Object类型,需要自己强制转换
     * @throws Exception
     */
    public String queryForClob(String sql,Object[] args) throws Exception {
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         String result = null;
         try {
        	 String strSql=DbUtil.getRows(sql, 1);
        	 ps = conn.prepareStatement(strSql);

        	 if(args == null) {
        		 args=new Object[0];
        	 }

        	 for(int i=0; i < args.length; i++) {
        		 ps.setObject(i+1, args[i]);
        	 }
             rs = ps.executeQuery();

             if(rs.next()) {
            	 result = StringUtil.getClobValue(rs.getClob(1));
             }
             
         } catch(Exception e){
        	 log.error("执行方法queryForObject(String sql,Object[] args)出错:" + sql,e);
             throw new Exception(e.getMessage(),e);
         } finally {
        	 DbUtil.close(rs);
        	 DbUtil.close(ps);
         }
         return result;
    }
    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回null;
     * 
     * @param strSql String  要执行的SQL语句
     * @param  Object[]
     * 
     * @return String类型,需要自己强制转换
     * @throws Exception
     */
    public String queryForString(String strSql, Object[] object) throws Exception {
    	String result = String.valueOf(queryForObject(strSql,object));
    	if(!"null".equals(result)) {
    		return result;
    	}
        return null;
    }
    /**
     * 
     * 获取第一条记录第一列
     * 
     * @param String  要执行的SQL语句
     * @throws Exception
     */    
    public String queryForString(String strSql)
    	throws Exception
	  {
	    String result = String.valueOf(queryForObject(strSql));
	    if (!("null".equals(result))) {
	      return result;
	    }
	    return null;
	  }
    
    /**
     * 
     * 获取第一条记录第一列
     * 
     * @param String  要执行的SQL语句
     * @throws Exception
     */  
	public Object queryForObject(String sql)
	    throws Exception
	  {
	   	 PreparedStatement ps = null;
	     ResultSet rs = null;
	     ResultSetMetaData rms=null;
	     Object result = null;
	     try {
	    	 String strSql=DbUtil.getRows(sql, 1);
	    	 ps = conn.prepareStatement(strSql);
	
	         rs = ps.executeQuery();
	         rms=rs.getMetaData();
	         
	         if(rs.next()) {
	        	 result =DbUtil.getStringValue(rs.getObject(1), rms.getColumnType(1));
	         }
	         
	     } catch(Exception e){
	    	 log.error("执行方法queryForObject(String sql,Object[] args)出错:" + sql,e);
	         throw new Exception(e.getMessage(),e);
	     } finally {
	    	 DbUtil.close(rs);
	    	 DbUtil.close(ps);
	     }
	     return result;
	  }
    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回-1;
     * 
     * @param strSql String  要执行的SQL语句
     * @return int类型,需要自己强制转换
     * @throws Exception
     */
    public int queryForInt(String strSql, Object[] object) throws Exception {
    	String result = String.valueOf(queryForObject(strSql,object));

    	if(!"null".equals(result) && !"".equals(result)) {
    		return Integer.parseInt(result);
    	}
    	return -1;
    }
    /**
     * 
     * 获取第一条记录第一列
     * 
     * @param String  要执行的SQL语句
     * @throws Exception
     */    
    public int queryForInt(String strSql)
	    throws Exception
	  {
	    String result = String.valueOf(queryForObject(strSql));
	
	    if (!("null".equals(result))) {
	      return Integer.parseInt(result);
	    }
	    return -1;
	  }
    /**
     * 
     * 执行带参数的SQL语句,执行成功返回执行结果的第一行第一列的记录,否则记录日志,抛出异常,返回-1;
     * 
     * @param strSql String  要执行的SQL语句
     * @return int类型,需要自己强制转换
     * @throws Exception
     */
    public double queryForDouble(String strSql, Object[] object) throws Exception {
    	String result = String.valueOf(queryForObject(strSql,object));

    	if(!"null".equals(result)) {
    		return Double.parseDouble(result);
    	}
    	return -1;
    }    
 
    /**
     * 得到记录集的列数。
     * @param RS ResultSet
     * @return int  返回记录集的列数
     */
    public int getColCount(ResultSet RS) {
        int colCount = 0;
        try {
            ResultSetMetaData RSMD = RS.getMetaData();
            colCount = RSMD.getColumnCount();
        } catch (Exception e) {
            log.error("getColCount函数执行出错=:"+e.getMessage(),e);
            colCount = -1;
        }
        return colCount;
    }

	/**
	 * 返回前N行数据的SQL语句
	 * @param sql
	 * @param n  
	 * @return
	 */
	public static String getRows(String sql, int n){
		return " SELECT * FROM ( " + sql + ") a WHERE rownum<=" + n;
	} 
	
	/**
	 * 
	 * 返回指定偏移位置指定数量记录的SQL语句
	 * 
	 * 该语句主要用于分页处理
	 * 
	 * @param sql
	 * @param n  
	 * @return
	 */	
	public static String getLimitString(String sql, int offset, int limit) {
		StringBuffer sb = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			sb.append("select * from ( select row_.*, rownum rownum_ from ( ").append(sql)
					.append(" ) row_ where rownum <=").append(offset + limit).append(") where rownum_ >")
					.append(offset);
		} else {
			sb.append("select * from ( ").append(sql).append(" ) where rownum<=").append(limit);
		}
		String strSql=sb.toString();
		return strSql;
	}	
	/**
	 * 
	 * 功能：分割参数数组，并且插入到PreparedStatement对象中
	 * 
	 * @param pstmt
	 *            PreparedStatement対象
	 * @param parms
	 *            存储过程需要的参数（参数是以数组的形式）
	 * @return
	 * @throws Exception
	 */
	private static void prepareCommand(PreparedStatement pstmt, String[] parms)	throws Exception {
		try {
			if (parms != null) {
				for (int i = 0; i < parms.length; i++) {
					try {
						pstmt.setDate(i + 1, java.sql.Date.valueOf(parms[i]));
					} catch (Exception e) {
						try {
							pstmt.setDouble(i + 1, Double.parseDouble(parms[i]));
						} catch (Exception e1) {
							try {
								pstmt.setInt(i + 1, Integer.parseInt(parms[i]));
							} catch (Exception e2) {
								try {
									pstmt.setString(i + 1, parms[i]);
								} catch (Exception e3) {
									try{
										pstmt.setObject(i + 1, parms[i]);
									}catch(Exception errs){
										log.error("执行方法prepareCommand(PreparedStatement pstmt, String[] parms)出错:"+ errs,e);	
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * 功能：执行插入或更新数据库语句;
	 * 
	 * @param Connection
	 * @param String
	 *            SQL语句
	 * @param String[]
	 *           参数（参数是以数组的形式）
	 * @return int 更新或插入的行数
	 * @throws Exception
	 * 
	 */
	public static int executeNonQuery(Connection conn,String cmdtext, String[] parms)throws Exception {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(cmdtext);
			prepareCommand(pstmt, parms);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			log.error("执行方法executeNonQuery(Connection conn,String cmdtext, String[] parms)出错:"+ e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(pstmt);
		}
	}
	/**
	 * 
	 * 功能：事务执行多条插入或更新数据库语句;
	 * 
	 * @param Connection
	 * @param ArrayList<String>
	 *            SQL语句
	 * @param ArrayList<String[]>
	 *            参数      如果有值则要求与sql语句list顺序一致
	 * @return int 更新或插入的行数
	 * @throws Exception
	 * 
	 */
	public static boolean executeNonQuery(Connection conn,ArrayList<String> cmdtext,ArrayList<String[]> params) throws Exception {
		PreparedStatement pstmt = null;
		boolean flag = true;
		try {
			conn.setAutoCommit(false);
			if(cmdtext!=null){
				for(int i=0;i<cmdtext.size();i++){
					pstmt = conn.prepareStatement(cmdtext.get(i));
					if(params!=null){
						prepareCommand(pstmt, params.get(i));
					}
					pstmt.executeUpdate();
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			flag = false;
			DbUtil.rollback(conn);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(pstmt);
		}
		return flag;
	}
	/**
	 * 
	 * 功能：执行Select语句，返回一个ArrayList;
	 * 
	 * @param cmdtext
	 *            SQL语句/存储过程名
	 * @param parms
	 *            存储过程需要的参数（参数是以数组的形式）
	 * @return 结果集(ArrayList)
	 * @throws Exception
	 */
	public static ArrayList executeReaderList(Connection conn,String cmdtext, String[] parms)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(cmdtext);
			prepareCommand(pstmt, parms);
			rs = pstmt.executeQuery();

			ArrayList<Object[]> al = new ArrayList<Object[]>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int column = rsmd.getColumnCount();

			while (rs.next()) {
				Object[] ob = new Object[column];
				for (int i = 1; i <= column; i++) {
					ob[i - 1] = rs.getObject(i);
				}
				al.add(ob);
			}

			return al;

		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 功能：执行Select语句，返回一个ArrayList;
	 * 
	 * @param cmdtext
	 *            SQL语句/存储过程名
	 * @param parms
	 *            存储过程需要的参数（参数是以数组的形式）
	 * @return 结果集(ArrayList)
	 * @throws Exception
	 */
	public ArrayList executeReaderList(String cmdtext, String[] parms)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(cmdtext);
			prepareCommand(pstmt, parms);
			rs = pstmt.executeQuery();

			ArrayList<Object[]> al = new ArrayList<Object[]>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int column = rsmd.getColumnCount();

			while (rs.next()) {
				Object[] ob = new Object[column];
				for (int i = 1; i <= column; i++) {
					ob[i - 1] = rs.getObject(i);
				}
				al.add(ob);
			}

			return al;

		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
	
	/**
	 * 功能：执行Select语句，返回一个SortedMap[];
	 * 
	 * @param conn
	 * @param cmdtext
	 *            SQL语句
	 * @param parms
	 *            参数（参数是以数组的形式）
	 * @return 结果集(SortedMap[])
	 * @throws Exception
	 */
	public static SortedMap[] executeReaderMap(Connection conn,String cmdtext, String[] parms) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Result result = null;
		SortedMap[] sortedMap = null;

		try {
			pstmt = conn.prepareStatement(cmdtext);
			prepareCommand(pstmt, parms);
			rs = pstmt.executeQuery();

			result = ResultSupport.toResult(rs);
			sortedMap = result.getRows();

			return sortedMap;

		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}

	/**
	 * 功能：执行Select语句，返回结果集中的第一行的name所指定的列的值，当结果集为空时，返回null
	 * 
	 * @param conn
	 * @param cmdtext
	 *            SQL语句
	 * @param name
	 *            需要取数的列名
	 * @param parms
	 *            存储过程需要的参数（参数是以数组的形式）
	 * @return 结果集中的第一行的name所指定的列的值，当结果集为空时，返回null
	 * @throws Exception
	 */
	public static Object executeScalar(Connection conn,String cmdtext, String name,	String[] parms) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(DbUtil.getRows(cmdtext, 1));
			prepareCommand(pstmt, parms);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getObject(name);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
	/**
	 * 
	 * 功能：执行Select语句，返回结果集中的第一行的index所指定的列的值，当结果集为空时，返回null
	 * 
	 * @param conn
	 * @param cmdtext
	 *            SQL语句/存储过程名
	 * @param index
	 *            需要取得列号
	 * @param parms
	 *            存储过程需要的参数（参数是以数组的形式）
	 * @return 结果集中的第一行的index所指定的列的值，当结果集为空时，返回null
	 * @throws Exception
	 */
	public static Object executeScalar(Connection conn,String cmdtext, int index, String[] parms)throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(DbUtil.getRows(cmdtext, 1));
			prepareCommand(pstmt, parms);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getObject(index);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("执行方法executeScalar(Connection conn,String cmdtext, int index, String[] parms)出错："+e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
		}
	}
    /**
     * 
     * 更新数据库
     * 
     */
	public void update(String table, String keyName, String keyValue, String field, String value)throws Exception{
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try{
	      String sql = "update " + table + " set " + field + "=?  where " + keyName + "=? ";

	      ps = this.conn.prepareStatement(sql);
	      int ii = 1;
	      ps.setString(ii++, value);
	      ps.setString(ii++, keyValue);
	      ps.execute();
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally {
	      close(rs);
	      close(ps);
	    }
	}
	
	  
	public Map getFieldType(String table)throws Exception{
		Map map = new HashMap();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      String sql = "select * from " + table + " where 1=2 ";
	      ps = this.conn.prepareStatement(sql);
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();
	      
	      for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	        map.put(RSMD.getColumnLabel(i).toLowerCase(), Integer.valueOf(RSMD.getColumnType(i)));
	      }
	    }
	    catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return map;
	  }
	
	  public List getList(String table, String field, String value)throws Exception{
		List list = new ArrayList();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      
	      String sql = "select * from " + table + " where " + field + "=? order by rowid ";

	      ps = this.conn.prepareStatement(sql);
	      ps.setString(1, value);
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();
	      while (rs.next()) {
	    	Map map = new HashMap();
	        for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	          map.put(RSMD.getColumnLabel(i).toLowerCase(), getStringValue(rs.getObject(RSMD.getColumnLabel(i)), RSMD.getColumnType(i)));
	        }
	        list.add(map);
	      };
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return list;
	  }
	  	  
	  public List getList(String table, String field, String value, String orderby)throws Exception{
		List list = new ArrayList();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      
	        String sql = "select * from " + table + " where " + field + "=? ";

	        String order = "rowid";
	        if (!("".equals(orderby))) {
	          order = orderby + "," + order;
	        }
	        sql = sql + " order by " + order;

	      ps = this.conn.prepareStatement(sql);
	      ps.setString(1, value);
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();
	      while (rs.next()) {
	    	Map map = new HashMap();
	        for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	          map.put(RSMD.getColumnLabel(i).toLowerCase(), getStringValue(rs.getObject(RSMD.getColumnLabel(i)), RSMD.getColumnType(i)));
	        }
	        list.add(map);
	      };
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }
	    finally{
	      close(rs);
	      close(ps);
	    }
	    return list;
	  }
	  
	  public static String getStringValue(Object value, int type){
	    String result = null;

	    switch (type)
	    {
	    case 2005:
	      Clob clob = (Clob)value;
	      result = getClobToString(clob);
	      break;
	      /**
	    case 2:
	      BigDecimal v = (BigDecimal)value;
	      if (v != null) {
	        result = new DecimalFormat("##0.00").format(v.doubleValue()); break;
	      }
	      result = "0.00";

	      break;
	      */
	    default:
	      result = String.valueOf(value);
	    }

	    return StringUtil.showNull(result);
	 }

	/**
	 * 
	 * 通用获取表所有数据
	 * 
	 */	 	  
	  public Map get(String table, String field, String value)throws Exception{
		Map map = new HashMap();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      
	      String sql = "select * from " + table + " where " + field + "=? ";
	      ps = this.conn.prepareStatement(sql);
	      ps.setString(1, value);
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();
	      if (rs.next()) {
	        for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	          map.put(RSMD.getColumnLabel(i).toLowerCase(), getStringValue(rs.getObject(RSMD.getColumnLabel(i)), RSMD.getColumnType(i)));
	        }
	      }
	      
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return map;
	  }
	  
	  
	  /**
	   * 
	   * 获取查询的数据,返回为map
	   * 
	   * @param sql
	   * @return map
	   */
	  public Map getEntityMap(String sql,Object[]args)throws Exception{
		Map map = new HashMap();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      
	      ps = this.conn.prepareStatement(sql);
	      
	      if(args == null) {
     		 args=new Object[0];
     	 }

     	 for(int i=0; i < args.length; i++) {
     		 ps.setObject(i+1, args[i]);
     	 }
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();
	      if (rs.next()) {
	        for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	          map.put(RSMD.getColumnLabel(i).toLowerCase(), getStringValue(rs.getObject(RSMD.getColumnLabel(i)), RSMD.getColumnType(i)));
	        }
	      }
	      
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return map;
	  }
		  
	/**
	 * 
	 * 获取表指定字段的值
	 * 
	 */	 	  
	  public Object get(String table, String field, String value, String returnField)
	    throws Exception
	  {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      String sql = "select * from " + table + " where " + field + "=? ";
	      ps = this.conn.prepareStatement(sql);
	      ps.setString(1, value);
	      rs = ps.executeQuery();
	      if (rs.next()) {
	    	Object localObject1 = rs.getObject(returnField);
	        return localObject1;
	      }
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return null;
	  }
	/**
	 * 
	 * 判断表字段是否存在
	 * 
	 */	  
	  public boolean checkFieldExists(String table, String fieldName)
	    throws Exception
	  {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      String sql = " select 1 from user_tab_columns a  where a.COLUMN_NAME = UPPER('" + fieldName + "') " + " and a.table_name=UPPER('" + table + "') ";

	      ps = this.conn.prepareStatement(sql);
	      rs = ps.executeQuery();
	      if (rs.next()) {
	        return true;
	      }
	    }catch (Exception e){
	    }finally{
	      close(rs);
	      close(ps);
	    }
	    return false;
	  }
	  
	/**
	 * 
	 * 判断视图/表是否存在
	 * @param strTable 视图名/表名
	 * @return
	 */
	public boolean checkTableExist(String strTable){
	      ResultSet rs=null;
	      PreparedStatement ps=null;
	      boolean bResult=false;
	      try{
	          String strSql = "select 1 from " + strTable +" where 1=2";
	          ps=conn.prepareStatement(strSql); 
	          rs=ps.executeQuery();
	          bResult=true;
	      }catch (Exception e){
	      }finally{
	    	  DbUtil.close(rs);
	    	  DbUtil.close(ps);
	      }
	      return bResult;
	}
		
	/**
	 * 
	 * 删除数据
	 * 
	 */		
	  public void del(String table, String field, String value)throws Exception{
		  
		if(StringUtil.showNull(table).equalsIgnoreCase("")){
			throw new RuntimeException("表名为空");
		}  
	    PreparedStatement ps = null;
	    try {
	      String sql = "DELETE FROM " + table + " WHERE " + field + "=?";
	    
	      ps = this.conn.prepareStatement(sql);
	      ps.setString(1,value);
	      ps.executeUpdate();
	    
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    } finally {
	      close(ps);
	    }
	  }
	  
	/**
	 * 
	 * 通用新增
	 * 
	 */
	  public void add(String table, String field, Map map) throws Exception{
		  
		if(StringUtil.showNull(table).equals("")){
			throw new RuntimeException("表名为空");
		}
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql = "";
	    
	    try{
	      String sql1 = "";
	      String sql2 = "";
	      sql = "select * from " + table + " where 1=2 ";
	      ps = this.conn.prepareStatement(sql);
	      rs = ps.executeQuery();
	      ResultSetMetaData RSMD = rs.getMetaData();

	      Iterator it = map.keySet().iterator();

	      List valueList = new ArrayList();

	      while (it.hasNext()) {
	        String key = String.valueOf(it.next()).toLowerCase();
	        String value = String.valueOf(map.get(key));

	        for (int i = 1; i <= RSMD.getColumnCount(); ++i) {
	          String columnName = RSMD.getColumnLabel(i).toLowerCase();

	          if ((!(StringUtil.showNull(field).toLowerCase().equals(columnName))) && (key.equals(columnName))) {
	            sql1 = sql1 + "," + key;
	            sql2 = sql2 + ",? ";

	            value = StringUtil.showNull(value);
	            int fieldType = RSMD.getColumnType(i);
	            if (fieldType == 2) {
	              value = value.replaceAll(",", "");
	            }
	            value = value.replaceAll("\"", "&quot;");
	            valueList.add(value);
	          }
	        }

	      }

	      sql = "insert into " + table + " (" + sql1.substring(1) + ") values (" + sql2.substring(1) + ") ";

	      ps = this.conn.prepareStatement(sql);
	      for (int i = 0; i < valueList.size(); ++i) {
	        ps.setString(i + 1, (String)valueList.get(i));
	      }

	      ps.execute();
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    } finally {
	      close(rs);
	      close(ps);
	    }
	  }
	/**
	 * 
	 * 通用获取数据集
	 * 
	 */
	  public List<Map<String, String>> query(String querySQL, List<String> setValues, List<String> getArguments)
	    throws Exception{
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List result = new ArrayList();
	    try {
	      ps = this.conn.prepareStatement(querySQL);
	      int n;
	      if ((setValues != null) && (setValues.size() > 0)) {
	        n = 1;
	        for (Object currentArgument : setValues) {
	          ps.setString(n++, currentArgument.toString());
	        }
	      }
	      rs = ps.executeQuery();

	      while (rs.next()) {
	        Map row = new HashMap();
	        for (int i = 0; (getArguments != null) && (i < getArguments.size()); ++i) {
	          row.put(getArguments.get(i), StringUtil.showNull(rs.getString((String)getArguments.get(i))));
	        }
	        result.add(row);
	      }
	    }catch (Exception e){
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	    } finally {
	      close(rs);
	      close(ps);
	    }

	    return result;
	  }
	  
    /**
     * 安全关闭RS的代码
     * @param rs ResultSet
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            log.error("安全关闭RS执行出错=:"+e.getMessage(),e);
        }
    }

    /**
     * 安全关闭Statement
     * @param st Statement
     */
    public static void close(Statement st){
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
        	log.error("安全关闭St执行出错=:"+e.getMessage(),e);
        }
    }

    /**
     * 安全关闭数据库连接
     * @param conn1 Connection
     */
    public static void close(Connection conn){
        try {
            if (conn != null) {
            	conn.close();
            }
        } catch (Exception e) {
        	log.error("安全关闭conn执行出错=:"+e.getMessage(),e);
        }
    }
    
	/**
	 * 
	 * 将clob值转成Sring
	 * 
	 */	 	  
	  public static String getClobToString(Clob clob) {
	    if (clob != null) {
	      try {
	        return clob.getSubString(1L, (int)clob.length());
	      } catch (SQLException e) {
	    	log.error(e.getMessage(), e);
	    	throw new RuntimeException(e);
	      }
	    }

	    return null;
	}    
	  
	/**
	 * 
	 * 获取clob字段的值
	 * 
	 */
    public static String getClobString(Clob clob) throws SQLException{
    	return getClobToString(clob);
    }
    
	/**
	 * 
	 * 获取blob字段的值
	 * 
	 */
	public static String getBlobValue(byte[] blob,String charCode) {
		String value =  "";
		
		if(blob != null) {
			try {
				value=new String(blob,charCode);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return value;
	}
	/**
	 * 
	 * 获取blob字段的值
	 * 
	 */
	public static String getBlobValue(byte[] blob) {
		String value =  "";
		
		if(blob != null) {
			try {
				value=new String(blob);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return value;
	}    
	/**
	 * 
	 * 回滚
	 * 
	 */    
    public static void rollback(Connection conn){
      try{
        if (conn != null){
            conn.rollback();
            conn.setAutoCommit(true);       	
        }
      }catch (Exception e) {
        log.error("回滚出错=:" + e.getMessage(),e);
      }
    }
	/**
	 * 
	 * 自动提交
	 * 
	 */   
    public static void setAutoCommit(Connection conn,boolean autoCommit){
      try{
        if (conn != null){
        	conn.setAutoCommit(autoCommit);
        }
      }catch (Exception e) {
        log.error("设置自动提交出错=:" + e.getMessage(),e);
      }
    }  
    
    
    
    /**
     * 表是否存在  主键
     * @param tableName 表名
     * @return
     * @throws Exception
     */
    public boolean getTableKey(String tableName) throws Exception {
    	DatabaseMetaData dbMeta = null;
    	ResultSet pkRSet = null;
    	boolean isKey = false;
    	
        try {
        	dbMeta =  conn.getMetaData();
        	
        	pkRSet = dbMeta.getPrimaryKeys(null, null, tableName);
        	
        	while(pkRSet.next()) {
     			System.err.println("****** MT_COM_ATTACH_DATA2 ******"); 
     			System.err.println("TABLE_CAT : "+pkRSet.getObject(1)); 
     			System.err.println("TABLE_SCHEM: "+pkRSet.getObject(2)); 
     			System.err.println("TABLE_NAME : "+pkRSet.getObject(3)); 
     			System.err.println("COLUMN_NAME: "+pkRSet.getObject(4)); 
     			System.err.println("KEY_SEQ : "+pkRSet.getObject(5)); 
     			System.err.println("PK_NAME : "+pkRSet.getObject(6)); 
     			System.err.println("****** ******* ******"); 
     			
     			if(pkRSet.getObject(6)!=null)
     				isKey = true;
     		} 
        	
        } catch(Exception e){
            log.error(e.getMessage(),e);
            throw new Exception(e);
        } finally {
        	DbUtil.close(pkRSet);
        }
        
        return isKey;
    }
    
    /**
     * 取主键字段名
     * @param tableName
     * @return
     * @throws Exception
     */
    public String getTableKeyName(String tableName) throws Exception {
    	DatabaseMetaData dbMeta = null;
    	ResultSet pkRSet = null;
    	String keyName = "";
    	
        try {
        	dbMeta =  conn.getMetaData();
        	
        	pkRSet = dbMeta.getPrimaryKeys(null, null, tableName);
        	
        	while(pkRSet.next()) {
     			System.err.println("****** MT_COM_ATTACH_DATA2 ******"); 
     			System.err.println("TABLE_CAT : "+pkRSet.getObject(1)); 
     			System.err.println("TABLE_SCHEM: "+pkRSet.getObject(2)); 
     			System.err.println("TABLE_NAME : "+pkRSet.getObject(3)); 
     			System.err.println("COLUMN_NAME: "+pkRSet.getObject(4)); 
     			System.err.println("KEY_SEQ : "+pkRSet.getObject(5)); 
     			System.err.println("PK_NAME : "+pkRSet.getObject(6)); 
     			System.err.println("****** ******* ******"); 
     			
     			if(pkRSet.getObject(4)!=null)
     				keyName += StringUtil.showNull(pkRSet.getObject(4))+",";
     		}
        	
        	if(!keyName.equals(""))
        		keyName = keyName.substring(0, keyName.length()-1);
        	
        } catch(Exception e){
            log.error(e.getMessage(),e);
            throw new Exception(e);
        } finally {
        	DbUtil.close(pkRSet);
        }
        
        return keyName;
    }
	/**
	 * 通过SQL，得到一个记录的集合
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getList(String sql) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List list = new ArrayList();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 1; i <= RSMD.getColumnCount(); i++) {
					map.put(RSMD.getColumnLabel(i).toLowerCase(),
							getStringValue(rs.getObject(RSMD.getColumnLabel(i)), RSMD.getColumnType(i)));
				}
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
		}

	}

	/**
	 * 新增记录
	 * 
	 * @param table
	 *            : 表名
	 * @param field
	 *            : 自动主键;新增时去掉自动主键,没有自动主键可以为空
	 * @param map
	 *            : 新增记录,K=表的字段名(小写),V=表单的值
	 */
	public int addInt(String table, String field, Map map) throws Exception {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			String sql1 = "";
			String sql2 = "";
			sql = "select * from " + table + " where 1=2 ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			
			Iterator it = map.keySet().iterator();
			
			List<String> valueList = new ArrayList<String>();
			
			while (it.hasNext()) {
				String key = String.valueOf(it.next()).toLowerCase();
				String value = String.valueOf(map.get(key));
				
				for (int i = 1; i <= RSMD.getColumnCount(); i++) {
					String columnName = RSMD.getColumnLabel(i).toLowerCase();
					
					if (!StringUtil.showNull(field).toLowerCase().equals(columnName) && key.equals(columnName)) {
						sql1 += "," + key;
						sql2 += "," + "? ";
						
						//金额类型去掉千分位
						value = StringUtil.showNull(value);
						int fieldType = RSMD.getColumnType(i) ;
						if(fieldType == Types.NUMERIC||fieldType == Types.DECIMAL) {
							value = value.replaceAll(",", "") ;
						}
						value = value.replaceAll("\"","&quot;") ;
						valueList.add(value);
					}
				}
				
			}

			sql = "insert into " + table + " (" + sql1.substring(1)
					+ ") values (" + sql2.substring(1) + ") ";
			
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < valueList.size(); i++) {
				ps.setString((i+1), valueList.get(i));
			}
			
			return ps.executeUpdate();

		} catch (Exception e) {
			log.debug("执行SQL出错:" + sql);
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
		}
	}


	/**
	 * 修改记录
	 * 
	 * @param table
	 *            : 表名
	 * @param field
	 *            : 主键字段名
	 * @param map
	 *            : 修改记录,K=表的字段名(小写),V=表单的值
	 */
	public int updateInt(String table, String field, Map map) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "", sql1 = "";
			sql = "select * from " + table + " where 1=2 ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			
			Iterator it = map.keySet().iterator();
			
			List<String> valueList = new ArrayList<String>();
			
			while (it.hasNext()) {
				String key = String.valueOf(it.next()).toLowerCase();
				String value = String.valueOf(map.get(key));
				
				for (int i = 1; i <= RSMD.getColumnCount(); i++) {
					String columnName = RSMD.getColumnLabel(i).toLowerCase();
					
					if (!StringUtil.showNull(field).toLowerCase().equals(columnName) && key.equals(columnName)) {
						sql1 += "," + key + "=? ";
						
						//金额类型去掉千分位
						value = StringUtil.showNull(value);
						int fieldType = RSMD.getColumnType(i) ;
						if(fieldType == Types.NUMERIC||fieldType == Types.DECIMAL) {
							value = value.replaceAll(",", "") ;
						}
						value = value.replaceAll("\"","&quot;") ;
						valueList.add(value);
					}
				}
				
			}
			
			String fieldValue = StringUtil.showNull(map.get(StringUtil.showNull(field).toLowerCase()));

			sql = "update " + table + " set " + sql1.substring(1) + " where " + field + " ='" + fieldValue + "'";
			//System.out.println(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < valueList.size(); i++) {
				ps.setString((i+1), valueList.get(i));
			}
			
			return ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
		}
	}

	
	/**
	 * @param table
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public String getPreparedAddSql(String table,Map map)throws Exception {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			String sql1 = "";
			String sql2 = "";
			sql = "select * from " + table + " where 1=2 ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			
			Iterator it = map.keySet().iterator();
			
			while (it.hasNext()) {
				String key = String.valueOf(it.next()).toLowerCase();
				
				for (int i = 1; i <= RSMD.getColumnCount(); i++) {
					String columnName = RSMD.getColumnLabel(i).toLowerCase();
					
					if (key.equals(columnName)) {
						sql1 += "," + key;
						sql2 += "," + "? ";
					}
				}
				
			}

			sql = "insert into " + table + " (" + sql1.substring(1)
					+ ") values (" + sql2.substring(1) + ") ";

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
		}
		return sql ;
	}

	public void batchAdd(String sql,String table,Map [] maps) throws Exception {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			String tempSql = "select * from " + table + " where 1=2 " ;
			ps = conn.prepareStatement(tempSql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			ps = conn.prepareStatement(sql);
			for(int j = 0; j < maps.length; j++){
				Map map = maps[j] ;
				
				Iterator it = map.keySet().iterator();
				
				List<String> valueList = new ArrayList<String>();
				
				while (it.hasNext()) {
					String key = String.valueOf(it.next()).toLowerCase();
					String value = String.valueOf(map.get(key));
					
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String columnName = rsmd.getColumnLabel(i).toLowerCase();
						
						if (key.equals(columnName)) {
							//金额类型去掉千分位
							
							value = StringUtil.showNull(value);
							int fieldType = rsmd.getColumnType(i) ;
							if(fieldType == Types.NUMERIC) {
								value = value.replaceAll(",", "") ;
							}
							value = value.replaceAll("\"","&quot;") ;
							valueList.add(value);
						}
					}
					
				}
				
				for (int i = 0; i < valueList.size(); i++) {
					ps.setString((i+1), valueList.get(i));
				}
				
				ps.addBatch();
			}
			
			ps.executeBatch() ;

		} catch (Exception e) {
			log.debug("执行SQL出错:" + sql);
			e.printStackTrace();
			throw e;
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 普遍更新语句
	 * 
	 * @param updateSql
	 * @param setValues
	 *            替换sql中的？
	 */
	public int update(String updateSql, java.util.List<String> setValues) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(updateSql);
			if (setValues != null && setValues.size() > 0) {
				int n = 1;
				for (String currentArgument : setValues) {
					ps.setString(n++, currentArgument);
				}
			}
			return ps.executeUpdate();
		}finally {
			DbUtil.close(ps);
		}
	}
	
	
}

