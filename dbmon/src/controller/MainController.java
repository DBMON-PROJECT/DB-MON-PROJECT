package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.MainThreadService;
import common.ConstantCommon;
import common.ControlCommon;
import common.DialogCommon;
import db.DBConnection;
import db.DbCommon;
import dto.LoginDto;
import dto.LoginLogDto;

public class MainController implements Initializable{
	
	// buttons
	@FXML private Button loginBtn;
    @FXML private Button startBtn;
    @FXML private Button closeBtn;

    // login combo box
    @FXML private ComboBox<String> loginHisCombo;
    public static String currLoginInfo;
    
    // chart 데이터를 넣기 위해 사용하는 thread
    public static MainThreadService thread;
   
    // online users label
    private Label onlineUsersLabel;

    // 현재 접속중인 정보를 담는 Map 객체
    public static HashMap<LoginLogDto, LoginDto> onlineUserList = new HashMap<LoginLogDto, LoginDto>();
    
    @FXML private TabPane tabPane;
    @FXML private Tab realtimeTab;
    @FXML private Tab sessionTab;
    @FXML private Tab schemaTab;
    
    /**
	 * 
	* 1. 메소드명 : loginComboHandle
	* 2. 작성일 : 2015. 8. 17. 오후 11:22:56
	* 3. 작성자 : 길용현
	* 4. 설명 : login combo box 이벤트 처리
	* @param event
	 */
    @FXML
    void loginComboHandle(ActionEvent event) {
    	String selectItem = loginHisCombo.getSelectionModel().getSelectedItem();
		
		if(selectItem != null){
			if(currLoginInfo == null){
				currLoginInfo = selectItem;
			
				if(!DBConnection.isConnection()){
					createConnection();
				}
			}else{
				if(!currLoginInfo.equals(selectItem)){
					if(thread != null && (thread.getState().toString().equals("TIMED_WAITING"))){
						thread.stopThread();
						thread.interrupt();
					}
				
					if(DBConnection.isConnection()){
						DbCommon.close(DBConnection.getConnection());
					}
					currLoginInfo = selectItem;
				
					createConnection();
				}
			}
		}
    }
    
    /**
	 * 
	* 1. 메소드명 : createConnection
	* 2. 작성일 : 2015. 8. 18. 오후 2:39:37
	* 3. 작성자 : 길용현
	* 4. 설명 : 현재 접속 유저로 Connection 객체 생성
	 */
	private void createConnection(){
		String tnsName = currLoginInfo.substring(currLoginInfo.indexOf("@")+1);
		String userId = currLoginInfo.substring(0, currLoginInfo.indexOf("@"));
		String userPwd = "";
		String url = "";
		
		for(LoginDto dto : LoginController.tnsInfoList){
			if(dto.getUserName().equals(tnsName)){
				url = "jdbc:oracle:thin:@"+dto.getHost()+":"+dto.getPort()+":"+dto.getServiceName();
				break;
			}
		}
				
		for(LoginLogDto logDto : onlineUserList.keySet()){
			if(logDto.getUserName().equals(userId)){
				userPwd = logDto.getUserPwd();
				break;
			}
		}

		DBConnection.createConnection(url, userId, userPwd);
	}
	
	/**
     * 
    * 1. 메소드명 : createLoginStage
    * 2. 작성일 : 2015. 8. 12. 오전 9:42:43
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 버튼 이벤트 처리 메서드 
     */
    @FXML
    void loginHandle(ActionEvent event) {
    	Stage dialog = new Stage(StageStyle.DECORATED);
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	dialog.setTitle("로그인 화면");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
    	
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
    	if(startBtn.getText().equals("start")){
    		if(!DBConnection.isConnection()){
        		DialogCommon.alert("데이터베이스 연결이 되어있지 않습니다.");
        		return;
        	}
        	
    		if((thread == null) || (thread.getState().toString().equals("TERMINATED"))){
    			thread = new MainThreadService();
            	thread.setDaemon(true);
            	thread.start();
    		}else{
    			thread.resumeThread();
    		}
        
        	startBtn.setText("stop");
    	}else{
    		if(thread != null && !(thread.getState().toString().equals("TERMINATED"))){
    			thread.suspendThread();
    			startBtn.setText("start");
    		}
    	}
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
    			thread.interrupt();

    			String selectedItem = loginHisCombo.getSelectionModel().getSelectedItem();
    			ControlCommon.getInstance().deleteCombo(loginHisCombo, selectedItem);
    			
    			DbCommon.close(DBConnection.getConnection());
    			
    			currLoginInfo = null;
    			ConstantCommon.cnt = 0;
    			startBtn.setText("start");
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		/*tabPane.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Tab>() {
					@Override
					public void changed(
							ObservableValue<? extends Tab> observable,
							Tab oldValue, Tab newValue) {
						if(newValue == realtimeTab){
							
						}
					}
				});*/
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(DBConnection.isConnection()){
    		DbCommon.close(DBConnection.getConnection());
    	}
	}
	
}
