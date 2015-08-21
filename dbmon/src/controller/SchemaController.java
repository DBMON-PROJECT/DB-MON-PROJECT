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




import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sql.SchemaSQL;
import common.BackgroundTableCell;
import common.ControlCommon;
import common.DialogCommon;
import common.TableColumnCommon;
import db.DBConnection;
import db.DbCommon;
import dto.TablespaceDto;

public class SchemaController implements Initializable {
	private static final Logger LOG = Logger.getLogger(SchemaController.class);
	private MainController main;

	@FXML
	private TableColumn<?, ?> distributePer;
	@FXML
	private TableColumn<?, ?> fileCnt;
	@FXML
	private TableColumn<?, ?> freeMb;
	@FXML
	private TableColumn<?, ?> avgMb;
	@FXML
	private TableColumn<?, ?> tablespace;
	@FXML
	private TableColumn<?, ?> freePer;
	@FXML
	private TableColumn<?, ?> totalMb;
	@FXML
	private TableColumn<?, ?> usedPer;
	@FXML
	private TableColumn<?, ?> maxMb;
	@FXML
	private TableColumn<?, ?> minMb;
	@FXML
	private TableColumn<?, ?> distState;
	@FXML
	private TableColumn<?, ?> usedMb;

	@FXML
	private PieChart objectChart;
	@FXML
	private PieChart tableChart;
	@FXML
	private PieChart indexChart;
	@FXML
	private ListView<String> userList;
	@FXML
	private TableView<TablespaceDto> sessionTv;
	@FXML
	private TextArea userSqlText;

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

	/**
	 * 
	 * 1. 메소드명 : getobjectPie 2. 작성일 : 2015. 8. 21. 오후 1:57:09 3. 작성자 : 조성일 4.
	 * 설명 : 데이터베이스 전체 오브젝트정보를 파이차트에 넣어주는 메소드
	 */
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
			LOG.debug("getobjectPie() while finish");
		} catch (SQLException e) {
			// LOG.debug("getobjectPie()  SQL Exception");
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

	/**
	 * 
	 * 1. 메소드명 : schemaSearchHandle 2. 작성일 : 2015. 8. 21. 오후 1:56:23 3. 작성자 :
	 * 조성일 4. 설명 : 서치버튼눌렀을때 실행되는 메소드 : 스키마컨트롤러의 이니셜라이즈와 같음
	 * 
	 * @param event
	 */
	@FXML
	void schemaSearchHandle(ActionEvent event) {
		if (!DBConnection.isConnection()) {
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
		pieChartListenerforobject();
	}

	
	/**
	 * 
	 * 1. 메소드명 : pieChartListenerforobject 2. 작성일 : 2015. 8. 21. 오후 12:43:11 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트리스너
	 * 
	 * @param piechart
	 */
	private PieChart.Data selectedDataforobject;
	private Tooltip tooltipforobject;
	private void pieChartListenerforobject() {

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		tooltipforobject = new Tooltip("");

		tooltipforobject.setStyle("-fx-font: 14 arial;  -fx-font-smoothing-type: lcd;");// -fx-text-fill:black;
																				// -fx-background-color:
																				// linear-gradient(#e2ecfe,
																				// #99bcfd);");

		for (final PieChart.Data data : objectChart.getData()) {
			Tooltip.install(data.getNode(), tooltipforobject);
			applyMouseEventsforobject(data);
		}
	}

	/**
	 * 
	 * 1. 메소드명 : applyMouseEventsforobject 2. 작성일 : 2015. 8. 21. 오후 12:28:57 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트내부 메소드 파이차트위에 마우스올렸을때 선택효과주는 기능
	 * 
	 * @param data
	 */
	private void applyMouseEventsforobject(final PieChart.Data data) {

		final Node node = data.getNode();

		node.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(new Glow());
				String styleString = "-fx-border-color: white; -fx-border-width: 3;"; // -fx-border-style:
																						// dashed;";
				node.setStyle(styleString);
				tooltipforobject.setText(String.valueOf("ObjectName : "+data.getName() + "\n"
						+"Value : "+(int) data.getPieValue()));
			}
		});

		node.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(null);
				node.setStyle("");
			}
		});

		node.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				selectedDataforobject = data;
			//LOG.debug("Selected data " + selectedData.toString());
			}
		});
	}
	
	
	/**
	 * 
	 * 1. 메소드명 : pieChartListenerfortable 2. 작성일 : 2015. 8. 21. 오후 12:43:11 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트리스너
	 * 
	 * @param piechart
	 */
	private PieChart.Data selectedDatafortable;
	private Tooltip tooltipfortable;
	private void pieChartListenerfortable() {

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		tooltipfortable = new Tooltip("");

		tooltipfortable.setStyle("-fx-font: 14 arial;  -fx-font-smoothing-type: lcd;");// -fx-text-fill:black;
																				// -fx-background-color:
																				// linear-gradient(#e2ecfe,
																				// #99bcfd);");

		for (final PieChart.Data data : tableChart.getData()) {
			Tooltip.install(data.getNode(), tooltipfortable);
			applyMouseEventsfortable(data);
		}
	}

	/**
	 * 
	 * 1. 메소드명 : applyMouseEventsfortable 2. 작성일 : 2015. 8. 21. 오후 12:28:57 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트내부 메소드 파이차트위에 마우스올렸을때 선택효과주는 기능
	 * 
	 * @param data
	 */
	private void applyMouseEventsfortable(final PieChart.Data data) {

		final Node node = data.getNode();

		node.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(new Glow());
				String styleString = "-fx-border-color: white; -fx-border-width: 3;"; // -fx-border-style:
																						// dashed;";
				node.setStyle(styleString);
				tooltipfortable.setText(String.valueOf("tableName : "+data.getName() + "\n"
						+"Value : "+ data.getPieValue()) + " MB");
			}
		});

		node.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(null);
				node.setStyle("");
			}
		});

		node.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				selectedDatafortable = data;
			//LOG.debug("Selected data " + selectedData.toString());
			}
		});
	}
	
	
	/**
	 * 
	 * 1. 메소드명 : pieChartListenerforindex 2. 작성일 : 2015. 8. 21. 오후 12:43:11 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트리스너
	 * 
	 * @param piechart
	 */
	private PieChart.Data selectedDataforindex;
	private Tooltip tooltipforindex;
	private void pieChartListenerforindex() {

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		tooltipforindex = new Tooltip("");

		tooltipforindex.setStyle("-fx-font: 14 arial;  -fx-font-smoothing-type: lcd;");// -fx-text-fill:black;
																				// -fx-background-color:
																				// linear-gradient(#e2ecfe,
																				// #99bcfd);");

		for (final PieChart.Data data : indexChart.getData()) {
			Tooltip.install(data.getNode(), tooltipforindex);
			applyMouseEventsforindex(data);
		}
	}

	/**
	 * 
	 * 1. 메소드명 : applyMouseEventsforindex 2. 작성일 : 2015. 8. 21. 오후 12:28:57 3. 작성자 : 조성일
	 * 4. 설명 : 파이차트내부 메소드 파이차트위에 마우스올렸을때 선택효과주는 기능
	 * 
	 * @param data
	 */
	private void applyMouseEventsforindex(final PieChart.Data data) {

		final Node node = data.getNode();

		node.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(new Glow());
				String styleString = "-fx-border-color: white; -fx-border-width: 3;"; // -fx-border-style:
																						// dashed;";
				node.setStyle(styleString);
				tooltipforindex.setText("indexName : "+String.valueOf(data.getName() + "\n"
						+"Values : "+ data.getPieValue()) + " MB");
			}
		});

		node.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				node.setEffect(null);
				node.setStyle("");
			}
		});

		node.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				selectedDataforindex = data;
			//LOG.debug("Selected data " + selectedData.toString());
			}
		});
	}
	
	

	/**
	 * 
	 * 1. 메소드명 : getuserlist 2. 작성일 : 2015. 8. 21. 오후 1:51:25 3. 작성자 : 조성일 4. 설명
	 * : 리스트뷰에 데이터베이스의 유저리스트를 넣어주는 메소드 (접속, 쿼리전송, 데이터입력기능)
	 */
	private void getuserlist() {

		Statement stmt = null;
		ResultSet rs = null;
		// LOG.debug("getuserlist() 시작");
		try {
			Connection conn = DBConnection
					.createConnection("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", "oracle");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SchemaSQL.getuserlist);

			while (rs.next()) {
				userList1.add(rs.getString(1));
			}
			// LOG.debug("getuserlist() while finish");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

						userList.getSelectionModel()
								.select(newValue.toString());
						// LOG.debug("userId : "+newValue.toString());
						tablePie(newValue.toString());
						indexPie(newValue.toString());
					}
				});

	}

	/**
	 * 
	 * 1. 메소드명 : tablePie 2. 작성일 : 2015. 8. 21. 오후 1:52:45 3. 작성자 : 조성일 4. 설명 :
	 * 리스트뷰의 유저리스트에선 선택된 유저의 테이블들의 용량을 파이차트에 출력하는 메소드
	 * 
	 * @param user
	 */
	private void tablePie(String user) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		// LOG.debug("tablePie() 시작");
		// for (int i = 0; i < tablePieList.size(); i++) {
		// tablePieList.remove(i);
		// }
		// for (int i = 0; i < pieChartDataB.size(); i++) {
		// pieChartDataB.remove(i);
		// }

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
			// LOG.debug("tablePie() while finish");
			for (int i = 0; i < tablePieList.size(); i++) {
				// LOG.debug(tablePieList.get(i));
			}
			pieChartDataB = FXCollections.observableArrayList(tablePieList);
			for (int i = 0; i < pieChartDataB.size(); i++) {
				// LOG.debug(pieChartDataB.get(i));
			}

			tableChart.setLabelsVisible(false);
			tableChart.setLegendVisible(false);
			tableChart.setData(pieChartDataB);

		} catch (SQLException e) {
			// LOG.debug("tablePie() SQLException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			// LOG.debug("tablePie() finally");
			DbCommon.close(rs);
			DbCommon.close(ps);
		}

		pieChartListenerfortable();

	}

	/**
	 * 
	 * 1. 메소드명 : indexPie 2. 작성일 : 2015. 8. 21. 오후 1:54:08 3. 작성자 : 조성일 4. 설명 :
	 * 리스트뷰의 유저리스트에선 선택된 유저의 인덱스들의 용량을 파이차트에 출력하는 메소드
	 * 
	 * @param user
	 */
	private void indexPie(String user) {
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
		pieChartListenerforindex();
	}

	/**
	 * 
	 * 1. 메소드명 : CellColor 2. 작성일 : 2015. 8. 21. 오후 1:55:30 3. 작성자 : 조성일 4. 설명 :
	 * 테이블스페이스테이블콜럼의 특정데이터셀 배경색 변경 메소드
	 * 
	 * @param list
	 * @param column
	 */
	private void CellColor(List list, TableColumn column) {
		column.setCellFactory(new Callback<TableColumn<List, String>, TableCell<List, String>>() {
			@Override
			public TableCell<List, String> call(TableColumn<List, String> column) {
				// TODO Auto-generated method stub
				return new BackgroundTableCell();
			}
		});
	}

	/**
	 * 
	 * 1. 메소드명 : initTable 2. 작성일 : 2015. 8. 21. 오후 1:54:43 3. 작성자 : 조성일 4. 설명 :
	 * 테이블스페이스를 나타내는 테이블에 데이터넣기
	 * 
	 * @throws SQLException
	 */
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

		ControlCommon.getInstance().insertTable(sessionTv, list,
				TableColumnCommon.getInstance().getTablespaceColumn());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
