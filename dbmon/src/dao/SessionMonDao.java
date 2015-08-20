package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sql.SessionSQL;
import controller.SessionController;
import db.DBConnection;
import dto.BindCheckDto;
import dto.PlanCheckDto;
import dto.SessionCheckDto;


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
	
	public ArrayList<SessionCheckDto> getSessionCheckSqlData()
			throws ClassNotFoundException {
		ArrayList<SessionCheckDto> list = new ArrayList<SessionCheckDto>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SessionSQL.sessionCheck);

			while (rs.next()) {
				SessionCheckDto dto = new SessionCheckDto();
				dto.setSqlId(rs.getString("sqlId"));
				dto.setOsUser(rs.getString("osUser"));
				dto.setLogonTime(rs.getString("logonTime"));
				dto.setPaddr(rs.getString("paddr"));
				dto.setUsername(rs.getString("username"));
				dto.setType(rs.getString("type"));
				System.out.println(dto.getLogonTime());
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<BindCheckDto> getBindCheckSqlData(String str)
			throws Exception {
		ArrayList<BindCheckDto> list = new ArrayList<BindCheckDto>();

		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SessionSQL.bindCheck);
			ps.setString(1, str);
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
		
	public String getTextCheckSqlData(String str) throws Exception {
		String text = null;

		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SessionSQL.textcheck);
			ps.setString(1, str);
			rs = ps.executeQuery();

			while (rs.next()) {

				text = rs.getString("SQL_FULLTEXT");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public StringBuffer getPlanCheckSqlData(String str) throws Exception {

		StringBuffer sb = new StringBuffer();

		ResultSet rs = null;
		PreparedStatement ps = null;

		Connection conn = DBConnection.getConnection();
		ps = conn.prepareStatement(SessionSQL.plancheck);
		ps.setString(1, str);
		rs = ps.executeQuery();

		while (rs.next()) {
			PlanCheckDto dto = new PlanCheckDto();
			dto.setPlanTableOutPut(rs.getString("planTableOutPut"));

			sb.append(dto + "\r\n");

		}

		return sb;
	}	
}
