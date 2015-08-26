package controller;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import javax.imageio.ImageIO;

import service.MainThreadService;
import common.ConstantCommon;
import common.ControlCommon;
import common.DialogCommon;
import dao.SessionMonDao;
import db.DBConnection;
import db.DbClose;
import dto.LoginDto;
import dto.LoginLogDto;

public class MainController implements Initializable{
	
	// buttons
	@FXML private Button loginBtn;
    @FXML private Button startBtn;
    @FXML private Button closeBtn;
    @FXML private Button refreshBtn;

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
    @FXML private Tab logTab;

 	@Override
 	public void initialize(URL location, ResourceBundle resources) {	
 		tabPane.getSelectionModel().selectedItemProperty().addListener(
 				new ChangeListener<Tab>() {
 					@Override
 					public void changed(
 							ObservableValue<? extends Tab> observable,
 							Tab oldValue, Tab newValue) {
 						if(newValue.getId().equals("realtimeTab")){
 							if(!(thread == null) && !(thread.getState().toString().equals("TERMINATED"))){
 								thread.resumeThread();
 							}
 							refreshBtn.setVisible(false);
 						}else if(newValue.getId().equals("sessionTab")){
 							SessionController.instance.sessionSearchHandle();
 							if(!(thread == null) && !(thread.getState().toString().equals("TERMINATED"))){
 								thread.suspendThread();
 							}
 							refreshBtn.setVisible(true);
 						}else if(newValue.getId().equals("schemaTab")){
 							SchemaController.instance.schemaSearchHandle();
 							if(!(thread == null) && !(thread.getState().toString().equals("TERMINATED"))){
 								thread.suspendThread();
 							}
 							refreshBtn.setVisible(true);
 						}else if(newValue.getId().equals("logTab")){
 							LogFileController.instance.logfileSearchHandle();
 							if(!(thread == null) && !(thread.getState().toString().equals("TERMINATED"))){
 								thread.suspendThread();
 							}
 							refreshBtn.setVisible(false);
 						}
 					}
 				}
 		);
 	}
 	
 	@Override
 	protected void finalize() throws Throwable {
 		if(DBConnection.isConnection()){
     		DbClose.close(DBConnection.getConnection());
     	}
 	}
    
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
						DbClose.close(DBConnection.getConnection());
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
    	dialog.setTitle("LOGIN");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
    	
    	Parent parent = null;
    	
    	try{
    		parent = loader.load();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	dialog.initStyle(StageStyle.UTILITY);
    	dialog.setResizable(false);
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
    			
    			DbClose.close(DBConnection.getConnection());
    			
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
    * 1. 메소드명 : refreshHandle
    * 2. 작성일 : 2015. 8. 25. 오후 10:30:29
    * 3. 작성자 : 길용현
    * 4. 설명 : Session Tab, Schema Tab refresh 기능
    * @param event
     */
    @FXML
    void refreshHandle(ActionEvent event) {
    	Tab selectionTab = tabPane.getSelectionModel().getSelectedItem();
    	
    	if(selectionTab.equals(sessionTab)){
    		SessionController.instance.sessionSearchHandle();
    	}else if(selectionTab.equals(schemaTab)){
    		SchemaController.instance.schemaSearchHandle();
    	}
    }
    
    /**
     * 
    * 1. 메소드명 : saveMenuHandle
    * 2. 작성일 : 2015. 8. 21. 오후 4:57:01
    * 3. 작성자 : 길용현
    * 4. 설명 : 현재 화면 screenshot 기능
    * @param event
     */
    @FXML
    void saveMenuHandle(ActionEvent event) {
    	int x = (int)DBMonMain.mainStage.getX();
    	int y = (int)DBMonMain.mainStage.getY();
    	int width = (int)DBMonMain.mainStage.getWidth();
    	int height = (int)DBMonMain.mainStage.getHeight();
    	
    	try{   
			BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(x, y, width, height));

			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			String path = MainController.class.getResource(".").getPath();
			path = path.substring(0, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/"));
			
	        File file = new File(path+"/src/screenshot/"+sdf.format(c.getTime())+".jpg");
	        ImageIO.write(screencapture, "jpg", file);            
	    }catch (HeadlessException e){
	    	e.printStackTrace();
	    }catch (AWTException e){
	        e.printStackTrace();
	    }catch (IOException e){
	        e.printStackTrace();
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
    		DbClose.close(DBConnection.getConnection());
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
}
