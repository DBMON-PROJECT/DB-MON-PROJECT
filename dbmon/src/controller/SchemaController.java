package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sql.SchemaSQL;

import common.BackgroundTableCell;
import common.ControlCommon;
import common.DialogCommon;
import common.TableColumnCommon;

import db.DBConnection;
import db.DbCommon;
import dto.TablespaceDto;

public class SchemaController implements Initializable{
	private MainController  main;
	
	
    @FXML private TableColumn<?, ?> distributePer;
    @FXML private TableColumn<?, ?> fileCnt;
    @FXML private TableColumn<?, ?> freeMb;
    @FXML private TableColumn<?, ?> avgMb;
    @FXML private TableColumn<?, ?> tablespace;
    @FXML private TableColumn<?, ?> freePer;
    @FXML private TableColumn<?, ?> totalMb;
    @FXML private TableColumn<?, ?> usedPer;
    @FXML private TableColumn<?, ?> maxMb;
    @FXML private TableColumn<?, ?> minMb;
    @FXML private TableColumn<?, ?> distState;
    @FXML private TableColumn<?, ?> usedMb;
	

	@FXML private PieChart objectChart;
	@FXML private PieChart tableChart;
	@FXML private PieChart indexChart;
	@FXML private ListView<String> userList;
    @FXML private TableView<TablespaceDto> sessionTv;
	@FXML private TextArea userSqlText;

	
	
	ObservableList<PieChart.Data> pieChartDataA = FXCollections
			.observableArrayList();
	ObservableList<PieChart.Data> pieChartDataB = FXCollections
			.observableArrayList();
	ObservableList<PieChart.Data> pieChartDataC = FXCollections
			.observableArrayList();
	ObservableList<String> userList2 = FXCollections.observableArrayList();

	List<PieChart.Data> objectPieList = new ArrayList<PieChart.Data>();
	List<PieChart.Data> tablePieList = new ArrayList<PieChart.Data>();
	List<PieChart.Data> indexPieList = new ArrayList<PieChart.Data>();	
	List<String> userList1 = new ArrayList<String>();

	private void getobjectPie() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Connection conn = DBConnection
					.createConnection("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", "oracle");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.objectcomp);

			while (rs.next()) {
				objectPieList.add(new PieChart.Data(rs.getString(1), rs
						.getDouble(2)));
			}
			//LOG.debug("getobjectPie() while finish");
		} catch (SQLException e) {
		//	LOG.debug("getobjectPie()  SQL Exception");
			e.printStackTrace();
		} finally {
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
		pieChartDataA = FXCollections.observableArrayList(objectPieList);
		objectChart.setLabelsVisible(false);
		objectChart.setLegendVisible(false);
		objectChart.setData(pieChartDataA);

	}
	
	@FXML
    void schemaSearchHandle(ActionEvent event) {
		if(!DBConnection.isConnection()){
			DialogCommon.alert("데이터베이스에 연결되어있지 않습니다.");
			return;
		}
		
		getobjectPie();
		getuserlist();
		try {
			initTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objectChartlistener();
    }

	private void objectChartlistener(){
		
		for (final PieChart.Data data : objectChart.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {  
		             //   LOG.debug(String.valueOf(data.getName()+" : "+data.getPieValue()));
		             }
		        });
		}
	}
	
	
	private void tableChartlistener(){
		
		for (final PieChart.Data data : tableChart.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {  
		              //  LOG.debug(String.valueOf(data.getName()+" : "+data.getPieValue()));
		             }
		        });
		}
	}
	
	private void indexChartlistener(){
		
		for (final PieChart.Data data : indexChart.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {  
		             //   LOG.debug(String.valueOf(data.getName()+" : "+data.getPieValue()));
		             }
		        });
		}
	}
	
	
	
	
	private void getuserlist(){
    	
    	Statement stmt = null;
		ResultSet rs = null;	
		//LOG.debug("getuserlist() 시작");
    	try{
    		Connection conn = DBConnection.createConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "oracle");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.getuserlist);
			
			while(rs.next()){
				userList1.add(rs.getString(1));
			}	
		//	LOG.debug("getuserlist() while finish");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DbCommon.close(rs);
			DbCommon.close(stmt);
		}
    	
    	userList2 = FXCollections.observableArrayList(userList1);
    	userList.setItems(userList2);
    	
    	
		userList.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observable,
									String oldValue, String newValue) {
										
						userList.getSelectionModel().select(newValue.toString());
					//	LOG.debug("userId : "+newValue.toString());
						tablePie(newValue.toString());
						indexPie(newValue.toString());
					}
				});
    	
    }
	
	private void tablePie(String user){
		ResultSet rs = null;
		PreparedStatement ps = null;
		//LOG.debug("tablePie() 시작");
//		for (int i = 0; i < tablePieList.size(); i++) {
//			tablePieList.remove(i);
//		}
//		for (int i = 0; i < pieChartDataB.size(); i++) {
//			pieChartDataB.remove(i);
//		}
		
			tablePieList = new ArrayList<PieChart.Data>(); 
			pieChartDataB = FXCollections.observableArrayList();
		
		try {
			Connection conn = DBConnection
					.createConnection("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", "oracle");
			
			ps = conn.prepareStatement(SchemaSQL.tablecomp);
			ps.setString(1, user);
			
			rs = ps.executeQuery();
			
			
			
			
			while (rs.next()) {
				tablePieList.add(new PieChart.Data(rs.getString(1), rs
						.getDouble(2)));
			}
		//	LOG.debug("tablePie() while finish");
			for (int i = 0; i < tablePieList.size(); i++) {
		//		LOG.debug(tablePieList.get(i));
			}
			pieChartDataB = FXCollections.observableArrayList(tablePieList);
			for (int i = 0; i < pieChartDataB.size(); i++) {
		//		LOG.debug(pieChartDataB.get(i));
			}
			
			tableChart.setLabelsVisible(false);
			tableChart.setLegendVisible(false);
			tableChart.setData(pieChartDataB);

		} catch (SQLException e) {
		//	LOG.debug("tablePie() SQLException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		} finally {
		//	LOG.debug("tablePie() finally");
			DbCommon.close(rs);
			DbCommon.close(ps);
		}
		
		tableChartlistener();
		
	}
	
	private void indexPie(String user){
		PreparedStatement ps = null;
		ResultSet rs = null;
		indexPieList = new ArrayList<PieChart.Data>(); 
		pieChartDataC = FXCollections.observableArrayList();

		try {
			Connection conn = DBConnection
					.createConnection("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", "oracle");
			ps = conn.prepareStatement(SchemaSQL.indexcomp);
			ps.setString(1, user);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				indexPieList.add(new PieChart.Data(rs.getString(1), rs
						.getDouble(2)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbCommon.close(rs);
			DbCommon.close(ps);
		}
		pieChartDataC = FXCollections.observableArrayList(indexPieList);
		indexChart.setLabelsVisible(false);
		indexChart.setLegendVisible(false);
		indexChart.setData(pieChartDataC);
		indexChartlistener();
	}
    
	private void CellColor(List list , TableColumn column){
    	column.setCellFactory(new Callback<TableColumn<List,String>,TableCell<List, String>>(){
			@Override
			public TableCell<List, String> call(
				 TableColumn<List, String> column ) {
				// TODO Auto-generated method stub
				return new BackgroundTableCell();
			}
		});
    }
 
	
	
	
	
	
	private void initTable() throws SQLException {

		ArrayList<TablespaceDto> list = new ArrayList<TablespaceDto>();
		ArrayList<String> colname = new ArrayList<String>();

		Connection conn = DBConnection.getConnection();
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SchemaSQL.table_amount_check);
		ResultSetMetaData rsmd = rs.getMetaData();
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

		ControlCommon.getInstance().insertTable(sessionTv, list, TableColumnCommon.getInstance().getTablespaceColumn());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
