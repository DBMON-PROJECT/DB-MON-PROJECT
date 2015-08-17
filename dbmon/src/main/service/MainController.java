package main.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.dto.LoginDto;
import login.dto.LoginLogDto;
import login.service.LoginController;
import main.dao.RealtimeMonDao;
import main.dto.PerformenceDto;
import main.dto.TopSqlDto;
import main.dto.WaitEventDto;
import common.ControlCommon;
import common.DialogCommon;
import db.DBConnection;
import db.DbCommon;

/**
 * 
* 1. 패키지명 : main.service
* 2. 타입명 : MainController.java
* 3. 작성일 : 2015. 8. 12. 오전 9:31:06
* 4. 작성자 : 길용현
* 5. 설명 : 메인 화면 Controller 클래스
 */
public class MainController implements Initializable{

	// performence chart
	@FXML private LineChart<String, Number> bufCacheChart;
	@FXML private LineChart<String, Number> libCacheChart;
	@FXML private LineChart<String, Number> dicCacheChart;
    @FXML private LineChart<String, Number> inMemoryChart;
    // wait event chart
    @FXML private LineChart<String, Number> bufBusyWaitsChart;
    @FXML private LineChart<String, Number> logFileSyncChart;
    @FXML private LineChart<String, Number> dbSequentialChart;
    @FXML private LineChart<String, Number> dbScatteredChart;
    @FXML private LineChart<String, Number> libraryCacheLockChart;
    @FXML private LineChart<String, Number> logBufferSpaceChart;
    // top3 sql chart
    @FXML private BarChart<String, Number> BufferGetsChart;
    @FXML private BarChart<String, Number> cpuTimeChart;
    @FXML private BarChart<String, Number> elapsedTimeChart;
    @FXML private BarChart<String, Number> executionsChart;
    // jdbc connection chart
    @FXML private BarChart<?, ?> jdbcConnectionChart;    
    // online users label
    @FXML private Label onlineUsersLabel;
    
    @FXML private Button loginBtn;
    @FXML private Button startBtn;
    @FXML private Button closeBtn;
    
    @FXML private ComboBox<String> loginHisCombo;
    
    // 현재 접속중인 정보를 담는 Map 객체
    public static HashMap<LoginLogDto, LoginDto> onlineUserList = new HashMap<LoginLogDto, LoginDto>();
    // chart 데이터를 넣기 위해 사용하는 thread
    public static MainThread thread;
    public static int cnt;
    
    // performance 항목
    private XYChart.Series<String, Number> bufferCacheHitSeries;
    private ObservableList bufferCacheHitList;
    private XYChart.Series<String, Number> libraryCacheHitSeries;
    private ObservableList libraryCacheHitList;
    private XYChart.Series<String, Number> dictionaryCacheHitSeries;
    private ObservableList dictionaryCacheHitList;
    private XYChart.Series<String, Number> inMemoryHitSeries;
    private ObservableList inMemoryHitList;
    
    // wait event 항목
    private XYChart.Series<String, Number> bufferBusyWaitSeries;
    private ObservableList bufferBusyWaitList;
    private XYChart.Series<String, Number> logFileSyncSeries;
    private ObservableList logFileSyncList;
    private XYChart.Series<String, Number> dbFileSequentialSeries;
    private ObservableList dbFileSequentialList;
    private XYChart.Series<String, Number> dbFileScatteredSeries;
    private ObservableList dbFileScatteredList;
    private XYChart.Series<String, Number> libraryCacheLockSeries;
    private ObservableList libraryCacheLockList;
    private XYChart.Series<String, Number> logBufferSpaceSeries;
    private ObservableList logBufferSpaceList;
    
    // TOP3 SQL 항목 
    private XYChart.Series<String, Number> bufferGetstSeries;
    private ObservableList bufferGetsList;
    private XYChart.Series<String, Number> cpuTimeSeries;
    private ObservableList cpuTimeList;
    private XYChart.Series<String, Number> elapsedTimelSeries;
    private ObservableList elapsedTimeList;
    private XYChart.Series<String, Number> executionsSeries;
    private ObservableList executionsList;
    
    // JDBC Connection 항목
    private XYChart.Series<String, Number> jdbcConnectSeries;
    private ObservableList jdbcConnectList;
    
    @Override
	protected void finalize() throws Throwable {
		if(DBConnection.isConnection()){
    		DbCommon.close(DBConnection.getConnection());
    	}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initChartData();
		setChartList();
	}
	
	/**
     * 
    * 1. 메소드명 : loginHandle
    * 2. 작성일 : 2015. 8. 12. 오전 9:30:13
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 버튼 이벤트 처리 메서드 
    * @param event
     */
    @FXML
    void loginHandle(ActionEvent event) {
    	createLoginStage();
    }
    
    /**
     * 
    * 1. 메소드명 : createLoginStage
    * 2. 작성일 : 2015. 8. 12. 오전 9:42:43
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 화면 생성 메서드
     */
    private void createLoginStage(){
    	Stage dialog = new Stage(StageStyle.DECORATED);
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	dialog.setTitle("로그인 화면");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
    	
    	Parent parent = null;
    	
    	try{
    		parent = loader.load();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	dialog.setScene(new Scene(parent));
    	dialog.show();
    	
    	LoginController formController = (LoginController)loader.getController();
		formController.currentStage = dialog;
		formController.mainController = this;
    }

    /**
     * 
    * 1. 메소드명 : startHandle
    * 2. 작성일 : 2015. 8. 12. 오전 9:30:40
    * 3. 작성자 : 길용현
    * 4. 설명 : 모니터링 시작 기능
    * @param event
     */
    @FXML
    void startHandle(ActionEvent event) {
    	if(!DBConnection.isConnection()){
    		DialogCommon.alert("데이터베이스 연결이 되어있지 않습니다.");
    		return;
    	}
    	
    	thread = new MainThread();
    	thread.setDaemon(true);
    	thread.start();
    }

    /**
     * 
    * 1. 메소드명 : closeHandle
    * 2. 작성일 : 2015. 8. 12. 오후 8:24:11
    * 3. 작성자 : 길용현
    * 4. 설명 : 모니터링 중단 기능
    * @param event
     */
    @FXML
    void closeHandle(ActionEvent event) {
    	if(DBConnection.isConnection()){
    		if(DialogCommon.confirm(loginHisCombo.getSelectionModel().getSelectedItem() + " 의 연결을 해제하시겠습니까?")){
    			thread.stopThread();
    		}
    	}else{
    		DialogCommon.alert("데이터베이스 연결이 되어있지 않습니다.");
    	}
    }
    
    /**
     * 
    * 1. 메소드명 : closeMenuHandle
    * 2. 작성일 : 2015. 8. 12. 오후 8:27:59
    * 3. 작성자 : 길용현
    * 4. 설명 : close 메뉴 버튼을 눌렀을 때 프로그램 종료하는 기능 
    * @param event
     */
    @FXML
    void closeMenuHandle(ActionEvent event) {
    	if(DBConnection.isConnection()){
    		DbCommon.close(DBConnection.getConnection());
    	}

    	DBMonMain.mainStage.close();
    }
    
    /**
     * 
    * 1. 메소드명 : addLoginHistory
    * 2. 작성일 : 2015. 8. 12. 오후 5:59:27
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 기록 콤보박스에 저장
     */
    public void addLoginHistory(LoginLogDto logInfo, LoginDto loginDto){
    	String item = logInfo.getUserName()+"@"+loginDto.getUserName();
    	
    	ControlCommon.getInstance().addCombo(loginHisCombo, item);
    }
	
	/**
	 * 
	* 1. 메소드명 : removeAllData
	* 2. 작성일 : 2015. 8. 13. 오후 10:03:28
	* 3. 작성자 : 길용현
	* 4. 설명 : 실시간 모니터링 탭의 전체 데이터 삭제
	 */
	public void removeAllData(){
		initChartData();
		setChartList();
	    
	    // Online Users 항목
	    onlineUsersLabel.setText("0");
	}
    
    /**
     * 
    * 1. 메소드명 : initChartData
    * 2. 작성일 : 2015. 8. 15. 오후 5:11:50
    * 3. 작성자 : 길용현
    * 4. 설명 : chart 에서 사용하는 Series, list 초기화
     */
	public void initChartData() {
		// performence 항목
		bufferCacheHitSeries = new XYChart.Series<String, Number>();
		bufferCacheHitList = FXCollections
				.observableArrayList(bufferCacheHitSeries);
		libraryCacheHitSeries = new XYChart.Series<String, Number>();
		libraryCacheHitList = FXCollections
				.observableArrayList(libraryCacheHitSeries);
		dictionaryCacheHitSeries = new XYChart.Series<String, Number>();
		dictionaryCacheHitList = FXCollections
				.observableArrayList(dictionaryCacheHitSeries);
		inMemoryHitSeries = new XYChart.Series<String, Number>();
		inMemoryHitList = FXCollections.observableArrayList(inMemoryHitSeries);

		// wait event 항목
		bufferBusyWaitSeries = new XYChart.Series<String, Number>();
		bufferBusyWaitList = FXCollections
				.observableArrayList(bufferBusyWaitSeries);
		logFileSyncSeries = new XYChart.Series<String, Number>();
		logFileSyncList = FXCollections.observableArrayList(logFileSyncSeries);
		dbFileSequentialSeries = new XYChart.Series<String, Number>();
		dbFileSequentialList = FXCollections
				.observableArrayList(dbFileSequentialSeries);
		dbFileScatteredSeries = new XYChart.Series<String, Number>();
		dbFileScatteredList = FXCollections
				.observableArrayList(dbFileScatteredSeries);
		libraryCacheLockSeries = new XYChart.Series<String, Number>();
		libraryCacheLockList = FXCollections
				.observableArrayList(libraryCacheLockSeries);
		logBufferSpaceSeries = new XYChart.Series<String, Number>();
		logBufferSpaceList = FXCollections
				.observableArrayList(logBufferSpaceSeries);

		// TOP3 SQL 항목
		bufferGetstSeries = new XYChart.Series<String, Number>();
		bufferGetsList = FXCollections.observableArrayList(bufferGetstSeries);
		cpuTimeSeries = new XYChart.Series<String, Number>();
		cpuTimeList = FXCollections.observableArrayList(cpuTimeSeries);
		elapsedTimelSeries = new XYChart.Series<String, Number>();
		elapsedTimeList = FXCollections.observableArrayList(elapsedTimelSeries);
		executionsSeries = new XYChart.Series<String, Number>();
		executionsList = FXCollections.observableArrayList(executionsSeries);
		
		// JDBC Connection 항목
		jdbcConnectSeries = new XYChart.Series<String, Number>();
		jdbcConnectList = FXCollections.observableArrayList(jdbcConnectSeries);
		RealtimeMonDao.getInstance().initJdbcMap();
	}
	
	/**
	 * 
	* 1. 메소드명 : initChartList
	* 2. 작성일 : 2015. 8. 15. 오후 5:12:30
	* 3. 작성자 : 길용현
	* 4. 설명 : chart 에서 사용하는 list 설정
	 */
	public void setChartList(){
		// perfomence 항목
		bufCacheChart.setData(bufferCacheHitList);
		libCacheChart.setData(libraryCacheHitList);
		dicCacheChart.setData(dictionaryCacheHitList);
		inMemoryChart.setData(inMemoryHitList);
		
		// wait event 항목
		bufBusyWaitsChart.setData(bufferBusyWaitList);
		logFileSyncChart.setData(logFileSyncList);
		dbSequentialChart.setData(dbFileSequentialList);
		dbScatteredChart.setData(dbFileScatteredList);
		libraryCacheLockChart.setData(libraryCacheLockList);
		logBufferSpaceChart.setData(logBufferSpaceList);
		
		// TOP3 SQL 항목
		BufferGetsChart.setData(bufferGetsList);
	    cpuTimeChart.setData(cpuTimeList);
	    elapsedTimeChart.setData(elapsedTimeList);
	    executionsChart.setData(executionsList);
	    
	    // JDBC Connection 항목
	    jdbcConnectionChart.setData(jdbcConnectList);
	    
	    nodeStrokeSetting();
	}
	
	/**
	 * 
	* 1. 메소드명 : nodeStrokeSetting
	* 2. 작성일 : 2015. 8. 16. 오전 11:03:36
	* 3. 작성자 : 길용현
	* 4. 설명 : 각 차트별 설정
	 */
	private void nodeStrokeSetting(){
		// line chart
		lineChartColorSetting(bufCacheChart, "#9CC4E4");
		lineChartColorSetting(libCacheChart, "#9CC4E4");
		lineChartColorSetting(dicCacheChart, "#9CC4E4");
		lineChartColorSetting(inMemoryChart, "#9CC4E4");
		
		lineChartColorSetting(bufBusyWaitsChart, "#F0C74D");
		lineChartColorSetting(logFileSyncChart, "#F0C74D");
		lineChartColorSetting(dbSequentialChart, "#F0C74D");
		lineChartColorSetting(dbScatteredChart, "#F0C74D");
		lineChartColorSetting(libraryCacheLockChart, "#F0C74D");
		lineChartColorSetting(logBufferSpaceChart, "#F0C74D");
	}
	
	/**
	 * 
	* 1. 메소드명 : lineChartColorSetting
	* 2. 작성일 : 2015. 8. 16. 오전 11:03:14
	* 3. 작성자 : 길용현
	* 4. 설명 : line chart color 설정
	* @param lineChart
	* @param color
	 */
	private void lineChartColorSetting(LineChart<?, ?> lineChart, String color){
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-stroke: "+color+", 1;");
		Node node = lineChart.lookup(".default-color0.chart-series-line");
		node.setStyle(sb.toString());
		
		/*Set<Node> symbols = lineChart.lookupAll(".default-color0.chart-line-symbol.series0");
		
		for(Node n : symbols){
			n.setStyle(sb.toString());
		}*/
	}
	
	private void barChartColorSetting(XYChart.Data<String,Number> data, String color){
		data.getNode().setStyle("-fx-bar-fill: "+color+";");
	}
	
	/**
	 * 
	* 1. 메소드명 : addData
	* 2. 작성일 : 2015. 8. 13. 오후 10:10:44
	* 3. 작성자 : 길용현
	* 4. 설명 : 각 차트 별 데이터 추가
	 */
	public void addData(int cnt){
		// performence 항목 chart data 추가
		ArrayList<PerformenceDto> performenceList = RealtimeMonDao.getInstance().getPerformenceData();
		double bufferCacheValue = ((PerformenceDto)performenceList.get(0)).getValue();
		double libraryCacheValue = ((PerformenceDto)performenceList.get(1)).getValue();
		double dictionaryCacheValue = ((PerformenceDto)performenceList.get(2)).getValue();
		double inMemoryValue = ((PerformenceDto)performenceList.get(3)).getValue();
		
		bufferCacheHitSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)bufferCacheValue));
		libraryCacheHitSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)libraryCacheValue));
		dictionaryCacheHitSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)dictionaryCacheValue));
		inMemoryHitSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)inMemoryValue));
		
		// wait event 항목 chart data 추가
		ArrayList<WaitEventDto> waitEventList = RealtimeMonDao.getInstance().getWaitEventData();
		double bufferBusyWaitsValue = ((WaitEventDto)waitEventList.get(0)).getAverageWait();
		double logFileSyncValue = ((WaitEventDto)waitEventList.get(1)).getAverageWait();
		double dbFileSequentialReadValue = ((WaitEventDto)waitEventList.get(2)).getAverageWait();
		double dbFileScatteredReadValue = ((WaitEventDto)waitEventList.get(3)).getAverageWait();
		double libraryCacheLockValue = ((WaitEventDto)waitEventList.get(4)).getAverageWait();
		double logBufferSpaceValue = ((WaitEventDto)waitEventList.get(5)).getAverageWait();
		
		bufferBusyWaitSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)bufferBusyWaitsValue));
	    logFileSyncSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)logFileSyncValue));	    
	    dbFileSequentialSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)dbFileSequentialReadValue));	    
	    dbFileScatteredSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)dbFileScatteredReadValue));	    
	    libraryCacheLockSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)libraryCacheLockValue));	    
	    logBufferSpaceSeries.getData().add(new XYChart.Data<String,Number>(cnt+"", (Number)logBufferSpaceValue));
	    
	    // TOP3 SQL 항목 chart data 추가
	    ArrayList<TopSqlDto> topSqlList = RealtimeMonDao.getInstance().getTopSqlData();
	    double bufferGetsValue1 = 0;
	    double bufferGetsValue2 = 0;
	    double bufferGetsValue3 = 0;
	    if(topSqlList.size() != 0){
	    	bufferGetsValue1 = ((TopSqlDto)topSqlList.get(0)).getTop1();
	    	bufferGetsValue2 = ((TopSqlDto)topSqlList.get(0)).getTop2();
	    	bufferGetsValue3 = ((TopSqlDto)topSqlList.get(0)).getTop3();
	    }
	    
	    if(cnt==0){
	    	XYChart.Data<String,Number> data1 = new XYChart.Data<String,Number>("1", (Number)bufferGetsValue1);
	    	XYChart.Data<String,Number> data2 = new XYChart.Data<String,Number>("2", (Number)bufferGetsValue2);
	    	XYChart.Data<String,Number> data3 = new XYChart.Data<String,Number>("3", (Number)bufferGetsValue3);
	    	bufferGetstSeries.getData().add(data1);
	    	bufferGetstSeries.getData().add(data2);
	    	bufferGetstSeries.getData().add(data3);
	    	barChartColorSetting(data1, "#DF1E3A");
	    	barChartColorSetting(data2, "#F0C74D");
	    	barChartColorSetting(data3, "#9CC4E4");
	    }else{
	    	bufferGetstSeries.getData().get(0).setYValue((Number)bufferGetsValue1);
	    	bufferGetstSeries.getData().get(1).setYValue((Number)bufferGetsValue2);
	    	bufferGetstSeries.getData().get(2).setYValue((Number)bufferGetsValue3);
	    }

    	double cpuTimeValue1 = 0;
    	double cpuTimeValue2 = 0;
    	double cpuTimeValue3 = 0;
    	if(topSqlList.size() != 0){
    		cpuTimeValue1 = ((TopSqlDto)topSqlList.get(1)).getTop1();
    		cpuTimeValue2 = ((TopSqlDto)topSqlList.get(1)).getTop2();
    		cpuTimeValue3 = ((TopSqlDto)topSqlList.get(1)).getTop3();
    	}
    	
    	if(cnt==0){
    		XYChart.Data<String,Number> data1 = new XYChart.Data<String,Number>("1", (Number)cpuTimeValue1);
	    	XYChart.Data<String,Number> data2 = new XYChart.Data<String,Number>("2", (Number)cpuTimeValue2);
	    	XYChart.Data<String,Number> data3 = new XYChart.Data<String,Number>("3", (Number)cpuTimeValue3);
    		cpuTimeSeries.getData().add(data1);
    	    cpuTimeSeries.getData().add(data2);
    	    cpuTimeSeries.getData().add(data3);
    	    barChartColorSetting(data1, "#DF1E3A");
	    	barChartColorSetting(data2, "#F0C74D");
	    	barChartColorSetting(data3, "#9CC4E4");
    	}else{
    		cpuTimeSeries.getData().get(0).setYValue((Number)cpuTimeValue1);
    		cpuTimeSeries.getData().get(1).setYValue((Number)cpuTimeValue2);
    		cpuTimeSeries.getData().get(2).setYValue((Number)cpuTimeValue3);
    	}
	    
	    double elapsedTimeValue1 = 0;
	    double elapsedTimeValue2 = 0;
	    double elapsedTimeValue3 = 0;
	    if(topSqlList.size() != 0){
	    	elapsedTimeValue1 = ((TopSqlDto)topSqlList.get(2)).getTop1();
	    	elapsedTimeValue2 = ((TopSqlDto)topSqlList.get(2)).getTop2();
	    	elapsedTimeValue3 = ((TopSqlDto)topSqlList.get(2)).getTop3();
	    }
	    
	    if(cnt==0){
	    	XYChart.Data<String,Number> data1 = new XYChart.Data<String,Number>("1", (Number)elapsedTimeValue1);
	    	XYChart.Data<String,Number> data2 = new XYChart.Data<String,Number>("2", (Number)elapsedTimeValue2);
	    	XYChart.Data<String,Number> data3 = new XYChart.Data<String,Number>("3", (Number)elapsedTimeValue3);
	    	elapsedTimelSeries.getData().add(data1);
	    	elapsedTimelSeries.getData().add(data2);
	    	elapsedTimelSeries.getData().add(data3);
    	    barChartColorSetting(data1, "#DF1E3A");
	    	barChartColorSetting(data2, "#F0C74D");
	    	barChartColorSetting(data3, "#9CC4E4");
	    }else{
	    	elapsedTimelSeries.getData().get(0).setYValue((Number)elapsedTimeValue1);
	    	elapsedTimelSeries.getData().get(1).setYValue((Number)elapsedTimeValue2);
	    	elapsedTimelSeries.getData().get(2).setYValue((Number)elapsedTimeValue3);
	    }
	    	    
	    double executionsValue1 = 0;
	    double executionsValue2 = 0;
	    double executionsValue3 = 0;
	    if(topSqlList.size() != 0){
	    	executionsValue1 = ((TopSqlDto)topSqlList.get(3)).getTop1();
	    	executionsValue2 = ((TopSqlDto)topSqlList.get(3)).getTop2();
	    	executionsValue3 = ((TopSqlDto)topSqlList.get(3)).getTop3();
	    }
	    
	    if(cnt==0){
	    	XYChart.Data<String,Number> data1 = new XYChart.Data<String,Number>("1", (Number)executionsValue1);
	    	XYChart.Data<String,Number> data2 = new XYChart.Data<String,Number>("2", (Number)executionsValue2);
	    	XYChart.Data<String,Number> data3 = new XYChart.Data<String,Number>("3", (Number)executionsValue3);
	    	executionsSeries.getData().add(data1);
	    	executionsSeries.getData().add(data2);
	    	executionsSeries.getData().add(data3);
    	    barChartColorSetting(data1, "#DF1E3A");
	    	barChartColorSetting(data2, "#F0C74D");
	    	barChartColorSetting(data3, "#9CC4E4");
	    }else{
	    	executionsSeries.getData().get(0).setYValue((Number)executionsValue1);
	    	executionsSeries.getData().get(1).setYValue((Number)executionsValue2);
	    	executionsSeries.getData().get(2).setYValue((Number)executionsValue3);
	    }
	    
	    // JDBC Connection data 추가
	    HashMap<Integer, HashSet<String>> jdbcMap = RealtimeMonDao.getInstance().getJdbcConnectData();
	    Set<Integer> set = jdbcMap.keySet();
		
		for(Object obj : set){
			int num = (int)obj;
			
			if(cnt==0){
				XYChart.Data<String,Number> data = new XYChart.Data<String,Number>(obj +"", (Number)jdbcMap.get(obj).size());
		    	jdbcConnectSeries.getData().add(data);
		    	barChartColorSetting(data, "#5EEBC9");
		    }else{
		    	jdbcConnectSeries.getData().get(num).setYValue((Number)jdbcMap.get(obj).size());
		    }
		}
	   
	    // Online Users data 추가
	    int onlineUsersCnt = RealtimeMonDao.getInstance().getOnlineUsersData();
	    int currentUsersCnt = Integer.parseInt(onlineUsersLabel.getText());
	    
	    if(currentUsersCnt != onlineUsersCnt){
	    	onlineUsersLabel.setText(onlineUsersCnt+"");
	    }
	}
	
	/**
	 * 
	* 1. 패키지명 : main.service
	* 2. 타입명 : MainController.java
	* 3. 작성일 : 2015. 8. 15. 오후 3:37:21
	* 4. 작성자 : 길용현
	* 5. 설명 : chart 구현을 위한 Thread 클래스
	 */
	class MainThread extends Thread {
		private boolean state = true;
		
		public void run() {
			cnt = 0;
			while(state){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// chart initialize
						if(cnt==0){
							removeAllData();
						}
						
						// chart data add
						addData(cnt);
					}
				});
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex) {
					break;
				}
				
				cnt++;
			}
		}
		
		public void stopThread(){
			state = false;
		}
	}
}
