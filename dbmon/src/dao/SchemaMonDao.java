package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import controller.SchemaController;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import sql.SchemaSQL;
import db.DBConnection;
import db.DbClose;
import dto.TablespaceDto;

/**
 * 
* 1. 패키지명 : dao
* 2. 타입명 : SchemaMonDao.java
* 3. 작성일 : 2015. 8. 23. 오후 4:27:58
* 4. 작성자 : 조성일
* 5. 설명 : schema monitoring tab 의 DB 관련 클래스
 */
public class SchemaMonDao {
	private static final Logger LOG = Logger.getLogger(SchemaMonDao.class);
	
	private static SchemaMonDao instance;
	
	private SchemaMonDao(){
		
	}
	
	public static SchemaMonDao getInstance(){
		if(instance == null){
			instance = new SchemaMonDao();
		}
		
		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : getObjectList
	* 2. 작성일 : 2015. 8. 23. 오후 4:39:07
	* 3. 작성자 : 조성일
	* 4. 설명 : 유저 별 오브젝트 개수 리스트
	* @return
	 */
	public ArrayList<PieChart.Data> getObjectList(){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<PieChart.Data> list = new ArrayList<PieChart.Data>();

		try {
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.objectcomp);

			while (rs.next()) {
				list.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
			}
			
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		} finally {
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getUserList
	* 2. 작성일 : 2015. 8. 23. 오후 4:47:44
	* 3. 작성자 : 조성일
	* 4. 설명 : DB 내에 저장되어있는 유저 리스트
	* @return
	 */
	public ArrayList<String> getUserList(){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			Connection conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.getuserlist);

			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		} finally {
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getTableList
	* 2. 작성일 : 2015. 8. 23. 오후 4:57:01
	* 3. 작성자 : 조성일
	* 4. 설명 : 유저 별 테이블 용량 리스트
	* @param user
	* @return
	 */
	public ArrayList<PieChart.Data> getTableList(String user){
		ResultSet rs = null;
		PreparedStatement ps = null;

		ArrayList<PieChart.Data> list = new ArrayList<PieChart.Data>();

		try {
			Connection conn = DBConnection.getConnection();

			ps = conn.prepareStatement(SchemaSQL.tablecomp);
			ps.setString(1, user);

			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
			}

		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		} finally {
			DbClose.close(rs);
			DbClose.close(ps);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getIndexList
	* 2. 작성일 : 2015. 8. 23. 오후 5:00:32
	* 3. 작성자 : 조성일
	* 4. 설명 : 유저 별 인덱스 용량 리스트
	* @param user
	* @return
	 */
	public ArrayList<PieChart.Data> getIndexList(String user){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PieChart.Data> list = new ArrayList<PieChart.Data>();

		try {
			Connection conn = DBConnection.getConnection();
			ps = conn.prepareStatement(SchemaSQL.indexcomp);
			ps.setString(1, user);

			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(ps);
		}
		
		return list;
	}
	
	/**
	 * 
	* 1. 메소드명 : getTablespaceList
	* 2. 작성일 : 2015. 8. 23. 오후 5:18:07
	* 3. 작성자 : 조성일
	* 4. 설명 : DB 내 테이블스페이스 리스트
	* @return
	 */
	public ArrayList<TablespaceDto> getTablespaceList(){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<TablespaceDto> list = new ArrayList<TablespaceDto>();
		
		try{
			Connection conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.table_amount_check);
			
			while (rs.next()) {
				TablespaceDto dto = new TablespaceDto();
				dto.setTablespace(rs.getString("TABLESPACE"));
				dto.setTotalMb(rs.getString("TOTALMB"));
				dto.setUsedMb(rs.getDouble("USEDMB"));
				dto.setFreeMb(rs.getDouble("FREEMB"));
				dto.setUsedPer(rs.getDouble("USED_PER"));
				dto.setFreePer(String.valueOf(rs.getDouble("FREE_PER")));
				dto.setMaxMb(rs.getDouble("MAX_DBF_MB"));
				dto.setMinMb(rs.getDouble("MIN_DBF_MB"));
				dto.setAvgMb(rs.getDouble("AVG_DBF_MB"));
				dto.setDistributePer(rs.getInt("DISTRIBUTION_PER"));
				dto.setDistState(rs.getString("DIST_STATUS"));
				dto.setFileCnt(rs.getInt("FILE_CNT"));

				list.add(dto);
			}
		}catch(SQLException e){
			LOG.debug(e.getMessage());
		}finally{
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		
		return list;
	}
}
