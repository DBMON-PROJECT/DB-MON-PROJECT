package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
* 1. 패키지명 : db
* 2. 타입명 : DbClose.java
* 3. 작성일 : 2015. 8. 12. 오후 12:23:45
* 4. 작성자 : 길용현
* 5. 설명 : DB Connection 관련 close 담당 클래스
 */
public class DbClose {
	public static void close(Connection conn){
		try{
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		try{
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt){
		try{
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt){
		try{
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
