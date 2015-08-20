package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import service.RealtimeService;

/**
 * 
* 1. 패키지명 : main.service
* 2. 타입명 : MainController.java
* 3. 작성일 : 2015. 8. 12. 오전 9:31:06
* 4. 작성자 : 길용현
* 5. 설명 : 메인 화면 Controller 클래스
 */
public class RealtimeController implements Initializable{
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
    
    private static RealtimeService realtimeService = RealtimeService.getInstance();

    public static RealtimeController realtimeController;
	/**
	 * 
	* 1. 메소드명 : initAllData
	* 2. 작성일 : 2015. 8. 13. 오후 10:03:28
	* 3. 작성자 : 길용현
	* 4. 설명 : Realtime Tab 에서 사용하는 모든 chart 및 label 초기화
	 */
	public void initAllData(){
		realtimeService.chartListSetting();
		setChartList();
	    
	    // Online Users 항목
		setOnlineUserLabel("0");
	}
	
	public Label getOnlineUserLabel(){
		return onlineUsersLabel;
	}
	
	public void setOnlineUserLabel(String value){
		onlineUsersLabel.setText(value);
	}

	/**
	 * 
	* 1. 메소드명 : setChartList
	* 2. 작성일 : 2015. 8. 20. 오후 1:38:13
	* 3. 작성자 : 길용현
	* 4. 설명 : chart 별 list 설정
	 */
	private void setChartList() {
		// perfomence 항목
		bufCacheChart.setData(realtimeService.bufferCacheHitList);
		libCacheChart.setData(realtimeService.libraryCacheHitList);
		dicCacheChart.setData(realtimeService.dictionaryCacheHitList);
		inMemoryChart.setData(realtimeService.inMemoryHitList);

		// wait event 항목
		bufBusyWaitsChart.setData(realtimeService.bufferBusyWaitList);
		logFileSyncChart.setData(realtimeService.logFileSyncList);
		dbSequentialChart.setData(realtimeService.dbFileSequentialList);
		dbScatteredChart.setData(realtimeService.dbFileScatteredList);
		libraryCacheLockChart.setData(realtimeService.libraryCacheLockList);
		logBufferSpaceChart.setData(realtimeService.logBufferSpaceList);

		// TOP3 SQL 항목
		BufferGetsChart.setData(realtimeService.bufferGetsList);
		cpuTimeChart.setData(realtimeService.cpuTimeList);
		elapsedTimeChart.setData(realtimeService.elapsedTimeList);
		executionsChart.setData(realtimeService.executionsList);

		// JDBC Connection 항목
		jdbcConnectionChart.setData(realtimeService.jdbcConnectList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		realtimeController = this;
		initAllData();
	}
}
