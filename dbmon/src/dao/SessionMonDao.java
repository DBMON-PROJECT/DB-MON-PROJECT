package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sql.SessionSQL;

import common.ConstantCommon;

import controller.SessionController;
import db.DBConnection;
import dto.BindCheckDto;
import dto.SessionCheckDto;

/**
 * 
* 1. 패키지명 : dao
* 2. 타입명 : SessionMonDao.java
* 3. 작성일 : 2015. 8. 21. 오후 1:50:45
* 4. 작성자 : 정석준
* 5. 설명 : Session Monitoring Tab DB 관련 클래스
 */
public class SessionMonDao {
	private SessionController sessioncontroller;
	private static SessionMonDao instance;

	private SessionMonDao() {

	}
		
	public static SessionMonDao getInstance() {
		if (instance == null) {
			instance = new SessionMonDao();
		}

		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : getSessionCheckSqlData
	* 2. 작성일 : 2015. 8. 21. 오후 1:51:28
	* 3. 작성자 : 정석준
	* 4. 설명 : session information 항목의 데이터를 추출
	* @return
	 */
	public ArrayList<SessionCheckDto> getSessionCheckSqlData(){
		ArrayList<SessionCheckDto> list = new ArrayList<SessionCheckDto>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SessionSQL.sessionCheck);

			while (rs.next()) {
				SessionCheckDto dto = new SessionCheckDto();
				dto.setSqlId(rs.getString("SQL_ID"));
				dto.setOsUser(rs.getString("OSUSER"));
				dto.setLogonTime(rs.getString("LOGON_TIME"));
				dto.setPaddr(rs.getString("PADDR"));
				dto.setUsername(rs.getString("USERNAME"));
				dto.setType(rs.getString("TYPE"));
				dto.setCommand(rs.getString("COMMAND"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setProgram(rs.getString("PROGRAM"));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 
	* 1. 메소드명 : getBindCheckSqlData
	* 2. 작성일 : 2015. 8. 21. 오후 1:52:48
	* 3. 작성자 : 정석준
	* 4. 설명 : bind variable 항목의 데이터를 추출
	* @param sqlId
	* @return
	 */
	public ArrayList<BindCheckDto> getBindCheckSqlData(String sqlId){
		ArrayList<BindCheckDto> list = new ArrayList<BindCheckDto>();

		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SessionSQL.bindCheck);
			ps.setString(1, sqlId);
			rs = ps.executeQuery();

			while (rs.next()) {
				BindCheckDto dto = new BindCheckDto();
				dto.setSanpId(rs.getString("sanpId"));
				dto.setSqlId(rs.getString("sqlId"));
				dto.setName(rs.getString("name"));
				dto.setPosition(rs.getString("position"));
				dto.setDatatypeString(rs.getString("datatypeString"));
				dto.setLastCaptured(rs.getDate("lastCaptured"));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		
	/**
	 * 
	* 1. 메소드명 : getTextCheckSqlData
	* 2. 작성일 : 2015. 8. 21. 오후 1:53:59
	* 3. 작성자 : 정석준
	* 4. 설명 : sql full text 항목의 데이터를 추출
	* @param str
	* @return
	 */
	public void getTextCheckSqlData(String sqlId){
		String text = null;
		
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SessionSQL.textcheck);
			ps.setString(1, sqlId);
			rs = ps.executeQuery();
			
			ConstantCommon.sqlFullText = new StringBuffer();
			
			while (rs.next()) {
				ConstantCommon.sqlFullText.append(rs.getString("SQL_FULLTEXT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* 1. 메소드명 : getPlanCheckSqlData
	* 2. 작성일 : 2015. 8. 21. 오후 1:58:32
	* 3. 작성자 : 정석준
	* 4. 설명 : plan text 항목의 데이터를 추출
	* @param str
	 */
	public void getPlanCheckSqlData(String sqlId){
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SessionSQL.plancheck);
			ps.setString(1, sqlId);
			rs = ps.executeQuery();

			ConstantCommon.planText = new StringBuffer();

			while (rs.next()) {
				ConstantCommon.planText.append(rs.getString("planTableOutPut")+ "\r\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
