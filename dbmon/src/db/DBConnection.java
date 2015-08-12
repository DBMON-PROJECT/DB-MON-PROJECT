package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import common.DialogCommon;

/**
 * 
* 1. 패키지명 : db
* 2. 타입명 : DBConnection.java
* 3. 작성일 : 2015. 8. 11. 오후 12:28:01
* 4. 작성자 : 길용현
* 5. 설명 : DB 관련 클래스 
 */
public class DBConnection {
	private static Connection conn;
	private static int connectCnt;
	
	private DBConnection(){
		
	}
	
	/**
	 * 
	* 1. 메소드명 : classLoding
	* 2. 작성일 : 2015. 8. 11. 오후 1:36:25
	* 3. 작성자 : 길용현
	* 4. 설명 : JDBC Driver 로딩
	 */
	private static void classLoding(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	/**
	 * 
	* 1. 메소드명 : getConnection
	* 2. 작성일 : 2015. 8. 11. 오후 1:36:41
	* 3. 작성자 : 길용현
	* 4. 설명 : Connection 객체 생성
	* @param url
	* @param userId
	* @param userPwd
	* @param newLogin
	* @return
	 */
	public static Connection getConnection(String url,String  userId,String  userPwd, boolean newLogin){
		if(connectCnt == 0){
			classLoding();
		}
		
		try{
			if(newLogin){
				if(isConnection()){
					DbCommon.close(conn);
				}
			}
			
			if(!isConnection()){
				conn = DriverManager.getConnection(url, userId, userPwd);
			}
			
			connectCnt++;
		}catch(SQLException e){
			DialogCommon.alert(e.getMessage());
		}
		
		return conn;
	}
	
	public static boolean isConnection(){
		try{
			if(conn == null || conn.isClosed()){
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
}
