package main.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.dto.LoginDto;
import login.dto.LoginLogDto;
import login.service.LoginController;

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
    @FXML private LineChart<?, ?> inMemoryChart;
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
    
    @FXML private ComboBox<String> loginHisCombo;
    
    // 현재 접속중인 정보를 담는 Map 객체
    public static HashMap<LoginLogDto, LoginDto> onlineUserList = new HashMap<LoginLogDto, LoginDto>();
    
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
    * 4. 설명 : 모니터링 수행 기능
    * @param event
     */
    @FXML
    void startHandle(ActionEvent event) {
    	
    }
    
    public void addLoginHistory(){
    	ArrayList<String> loginHisList = new ArrayList<String>();
    	
    	
    	//loginHisCombo.setItems(arg0);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
