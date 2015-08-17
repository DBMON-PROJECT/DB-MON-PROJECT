package login.service;

import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.dto.LoginDto;
import login.dto.LoginLogDto;
import main.service.MainController;

import common.ControlCommon;
import common.TableColumnCommon;

import db.DBConnection;

/**
 * 
* 1. 패키지명 : login
* 2. 타입명 : LoginController.java
* 3. 작성일 : 2015. 8. 11. 오전 10:13:32
* 4. 작성자 : 길용현
* 5. 설명 : Login 관련 Controller 클래스
 */
public class LoginController implements Initializable{

    @FXML private ComboBox<String> tnsCombo;
    @FXML private TextField nameTxt;
    @FXML private PasswordField pwdTxt;
    @FXML private CheckBox CheckCase;
    
    @FXML private TableView<?> loginTable;
    @FXML private TextArea tnsArea;
    
    @FXML private Button deleteBtn;
    @FXML private Button connectBtn;
    @FXML private Button cancelBtn;
    
    public Stage currentStage;
    public MainController mainController;
   
    public ArrayList<LoginDto> tnsInfoList; // tnsNames 파일 안의 정보를 가지는 리스트
    
    /**
     * 
    * 1. 메소드명 : Delete
    * 2. 작성일 : 2015. 8. 11. 오전 10:14:04
    * 3. 작성자 : 길용현
    * 4. 설명 : 접속 내역을 지우는 기능
    * @param event
     */
    @FXML
    void Delete(ActionEvent event) {
    	LoginLogDto logDto = (LoginLogDto)loginTable.getSelectionModel().getSelectedItem();
    	ArrayList<LoginLogDto> list = LoginService.getInstance().getLoginLogFile();

    	for(Object obj : list){
    		LoginLogDto logTemp = (LoginLogDto)obj;
    		if(logDto.getTnsName().equals(logTemp.getTnsName()) &&
    				logDto.getUserName().equals(logTemp.getUserName())){
    			list.remove(logTemp);
    			break;
    		}
    	}
    	
    	// 해당 로그 삭제 후 파일에 출력
    	LoginService loginService = LoginService.getInstance();
    	loginService.writeLoginLogFile(list);
    	
    	// 테이블에도 데이터 삭제
    	ControlCommon controlCommon = ControlCommon.getInstance();
    	controlCommon.insertTable(loginTable, list, TableColumnCommon.getInstance().getLoginLogColumn());
    }
  
    /**
     * 
    * 1. 메소드명 : Connect
    * 2. 작성일 : 2015. 8. 11. 오전 10:14:33
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 연결 기능
    * @param event
     */
    @FXML
    void Connect(ActionEvent event) {
    	String userId = nameTxt.getText();
    	String userPwd = pwdTxt.getText();
    	String tnsName =tnsCombo.getValue();
    	String url = "";
    	
    	for(LoginDto info : tnsInfoList){
    		if(tnsName.equals(info.getUserName())){
    			url = "jdbc:oracle:thin:@"+info.getHost()+":"+info.getPort()+":"+info.getServiceName();
    		}
    	}
    	
    	Connection conn = DBConnection.createConnection(url, userId, userPwd);
    	
    	if(conn != null){
    		Calendar cal = Calendar.getInstance();
        	Date date = cal.getTime();    	
        	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        	
        	LoginLogDto logInfo = new LoginLogDto();
        	logInfo.setTnsName(tnsName);
        	logInfo.setUserName(userId);
        	logInfo.setUserPwd(userPwd);
        	logInfo.setConnectTime(sdf.format(date));
        	
        	LoginService loginService = LoginService.getInstance();
        	loginService.addLoginLogFile(logInfo);
        	
        	LoginDto loginDto = null;
        	
        	for(Object obj : tnsInfoList){
        		LoginDto dto = (LoginDto)obj;
        		if(tnsName.equals(dto.getUserName())){
        			loginDto = dto;
        			break;
        		}
        	}
        	
        	mainController.onlineUserList.put(logInfo, loginDto);
        	mainController.addLoginHistory(logInfo, loginDto);
        	
        	currentStage.close();
    	}
    }
    
    /**
     * 
    * 1. 메소드명 : Cancel
    * 2. 작성일 : 2015. 8. 11. 오전 10:14:48
    * 3. 작성자 : 길용현
    * 4. 설명 : 로그인 창 닫는 기능
    * @param event
     */
    @FXML
    void Cancel(ActionEvent event) {
    	currentStage.close();
    }
    
    /**
     * 로그인 화면 초기화 작업
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoginService loginService = LoginService.getInstance();
		tnsInfoList = loginService.getUserInfo(loginService.readRegistry());
		ControlCommon common = ControlCommon.getInstance();
		
		// tnsname 정보를 콤보박스에 넣기
		ArrayList<String> tnsList = new ArrayList<String>();
		
		for(Object obj : tnsInfoList){
			LoginDto info = (LoginDto)obj;
			tnsList.add(info.getUserName());
		}
		common.insertCombo(tnsCombo, tnsList);
		
		tnsCombo.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(
					ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				
				for (LoginDto ch : tnsInfoList) {
					if(ch.getUserName()==newValue){
						tnsArea.setText(ch.getUserContent());
					}
				}					
			}			
		});
		
		// 로그인 로그 정보를 테이블에 넣기
		ArrayList<LoginLogDto> logInfo = loginService.getLoginLogFile();
		
		if(logInfo.size() != 0){
			common.insertTable(loginTable, logInfo, TableColumnCommon.getInstance().getLoginLogColumn());
		}
		
		// 테이블 row 더블클릭 이벤트 처리
		loginTable.setRowFactory(tv -> {
			TableRow<?> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					LoginLogDto logDto = (LoginLogDto)loginTable.getSelectionModel().getSelectedItem();
					String tnsName = logDto.getTnsName();
					String userName = logDto.getUserName();
					
					tnsCombo.setValue(tnsName);
					nameTxt.setText(userName);
					
					if(CheckCase.isSelected()){
						LoginService loginTemp = LoginService.getInstance();
						pwdTxt.setText(loginTemp.getUserPassword(tnsName, userName));
					}
					
					pwdTxt.requestFocus();
				}
			});
			return row;
		});
	}
	
}
