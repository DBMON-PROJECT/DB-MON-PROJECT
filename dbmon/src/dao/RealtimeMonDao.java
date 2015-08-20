package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import sql.RealtimeSQL;
import db.DBConnection;
import db.DbCommon;
import dto.PerformenceDto;
import dto.TopSqlDto;
import dto.WaitEventDto;

/**
 * 
* 1. 패키지명 : main.dao
* 2. 타입명 : ChartDao.java
* 3. 작성일 : 2015. 8. 12. 오후 9:08:15
* 4. 작성자 : 길용현
* 5. 설명 : 실시간 모니터링 탭에 해당하는 데이터를 추출하는 클래스
 */
public class RealtimeMonDao {
	private static RealtimeMonDao instance;
	private static HashMap<Integer, HashSet<String>> jdbcMap = new HashMap<Integer, HashSet<String>>();
	private static int day;
	
	private RealtimeMonDao(){
		
	}
	
	public static RealtimeMonDao getInstance(){
		if(instance == null){
			instance = new RealtimeMonDao();
		}
		
		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : getPerformenceData
	* 2. 작성일 : 2015. 8. 13. 오후 8:43:15
	* 3. 작성자 : 길용현
	* 4. 설명 : Perfomence 항목의 데이터를 추출
	* @return
	 */
	public ArrayList<PerformenceDto> getPerformenceData(){
		ArrayList<PerformenceDto> list = new ArrayList<PerformenceDto>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(RealtimeSQL.performenceSql);
			
			while(rs.next()){
				PerformenceDto dto = new PerformenceDto();
				dto.setType(rs.getString("TYPE"));
				dto.setValue(rs.getDouble("RATIO"));
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getWaitEventData
	* 2. 작성일 : 2015. 8. 13. 오후 9:41:38
	* 3. 작성자 : 길용현
	* 4. 설명 : Wait Event 항목의 데이터를 추출
	* @return
	 */
	public ArrayList<WaitEventDto> getWaitEventData(){
		ArrayList<WaitEventDto> list = new ArrayList<WaitEventDto>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(RealtimeSQL.waitEventSql);
			
			while(rs.next()){
				WaitEventDto dto = new WaitEventDto();
				dto.setEventName(rs.getString("EVENT_NAME"));
				dto.setAverageWait(rs.getDouble("AVERAGE_WAIT"));
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getTopSqlData
	* 2. 작성일 : 2015. 8. 14. 오전 3:03:58
	* 3. 작성자 : 길용현
	* 4. 설명 : TOP3 SQL 항목의 데이터를 추출
	* @return
	 */
	public ArrayList<TopSqlDto> getTopSqlData(){
		ArrayList<TopSqlDto> list = new ArrayList<TopSqlDto>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(RealtimeSQL.topSql);
			
			while(rs.next()){
				TopSqlDto dto = new TopSqlDto();
				dto.setType(rs.getString("TYPE"));
				dto.setTop1(rs.getDouble("TOP1"));
				dto.setTop2(rs.getDouble("TOP2"));
				dto.setTop3(rs.getDouble("TOP3"));
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : initJdbcMap
	* 2. 작성일 : 2015. 8. 15. 오후 6:28:50
	* 3. 작성자 : 길용현
	* 4. 설명 : JDBC Connection Map 초기화
	 */
	public void initJdbcMap(){
		jdbcMap = new HashMap<Integer, HashSet<String>>();
		
		for(int i=0; i<24; i++){
			jdbcMap.put(i, new HashSet<String>());
		}
		
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	* 1. 메소드명 : getJdbcConnectData
	* 2. 작성일 : 2015. 8. 15. 오후 6:14:08
	* 3. 작성자 : 길용현
	* 4. 설명 : JDBC Connection Process 데이터 추출
	* @return
	 */
	public HashMap<Integer, HashSet<String>> getJdbcConnectData(){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		
		try{
			Calendar cal = Calendar.getInstance();
			
			if(day != cal.get(Calendar.DAY_OF_MONTH)){
				initJdbcMap();
			}
			
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(RealtimeSQL.jdbcConnectSql);
			
			String paddr = "";
			int time = cal.get(Calendar.HOUR_OF_DAY);
			
			while(rs.next()){
				HashSet<String> set = jdbcMap.get(time);
				set.add(rs.getString("paddr"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		
		return jdbcMap;
	}
	
	/**
	 * 
	* 1. 메소드명 : getOnlineUsersData
	* 2. 작성일 : 2015. 8. 14. 오전 3:20:38
	* 3. 작성자 : 길용현
	* 4. 설명 : Online Users 데이터 추출
	* @return
	 */
	public int getOnlineUsersData(){
		Statement stmt = null;
		ResultSet rs = null;
		int cnt = -1;
		
		try{
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(RealtimeSQL.onlineUsersSql);
			
			while(rs.next()){
				TopSqlDto dto = new TopSqlDto();
				cnt = rs.getInt("CNT");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		
		return cnt;
	}
}
