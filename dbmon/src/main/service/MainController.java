package main.service;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.dto.LoginDto;
import login.dto.LoginLogDto;
import login.service.LoginController;
import main.dao.RealtimeMonDao;

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

	@FXML private LineChart<?, ?> dicCacheChart;
    @FXML private LineChart<Integer, Integer> inMemoryChart;
    @FXML private LineChart<?, ?> logFileSyncChart;
    @FXML private LineChart<?, ?> dbSequentialChart;
    @FXML private LineChart<?, ?> libCacheChart;
    @FXML private LineChart<?, ?> bufBusyWaitsChart;
    @FXML private LineChart<?, ?> logBufferSpaceChart;
    @FXML private LineChart<?, ?> libraryCacheLockChart;
    @FXML private LineChart<?, ?> bufCacheChart;
    @FXML private LineChart<?, ?> dbScatteredChart;

    @FXML private BarChart<?, ?> onlineUsersChart;
    @FXML private BarChart<?, ?> jdbcConnChart;
    @FXML private BarChart<?, ?> topSqlChart;
    
    @FXML private Button loginBtn;
    @FXML private Button startBtn;
    @FXML private Button closeBtn;
    
    @FXML private ComboBox<String> loginHisCombo;
    
    // 현재 접속중인 정보를 담는 Map 객체
    public static HashMap<LoginLogDto, LoginDto> onlineUserList = new HashMap<LoginLogDto, LoginDto>();
    
    public static MainThread thread;
    
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
    			thread = null;
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
    	if(thread != null){
    		thread.stopThread();
    		thread = null;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	class MainThread extends Thread {
		int cnt;
		private boolean state = true;
		
		public void run(){
			try{
				while(state){
					int data = RealtimeMonDao.getInstance().getInMemorySortRatio();
					
					XYChart.Series<Integer, Integer> se1 = new XYChart.Series<Integer, Integer>();
					se1.getData().add(new XYChart.Data<Integer, Integer>(cnt,data));
					System.out.println(1);
					Platform.runLater(new Runnable() {
		                @Override 
		                public void run() {
		                	System.out.println(2);
		                	inMemoryChart.getData().add(se1);
		                	System.out.println(3);
		                }
		            });
				
					cnt++;
					
					sleep(2000);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		public void stopThread(){
			state = false;
		}
	}
}
