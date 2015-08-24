package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import service.LoginService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import common.BackgroundTableCell;
import common.ConstantCommon;
import common.ControlCommon;
import common.DialogCommon;
import common.TableColumnCommon;
import dao.SessionMonDao;
import db.DBConnection;
import dto.BindCheckDto;
import dto.LoginLogDto;
import dto.SessionCheckDto;

/**
 * 
* 1. 패키지명 : controller
* 2. 타입명 : SessionController.java
* 3. 작성일 : 2015. 8. 21. 오후 1:46:30
* 4. 작성자 : 정석준
* 5. 설명 : Session Monitoring Tab Controller Class
 */
public class SessionController implements Initializable{
	//Tap 2 에 쓰이는 기능
    @FXML private TextArea fulltext;
    @FXML private TextArea plantext;
    
    //SessionCheckTableView
    @FXML private TableView sessionTable;
    @FXML private TableColumn logonCol;
     
    //BindCheckTableView
    @FXML private TableView bindTable;
    
    public static SessionController instance;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sessionTable.setRowFactory(tv -> {
			TableRow<?> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					SessionCheckDto sessionDto = (SessionCheckDto)sessionTable.getSelectionModel().getSelectedItem();
					ControlCommon common = ControlCommon.getInstance();
					String sqlId = sessionDto.getSqlId(); // 원래의 select 값

					ArrayList<BindCheckDto> bindcheck = SessionMonDao
							.getInstance().getBindCheckSqlData(sqlId);
					if (bindcheck.size() != 0) {
						common.insertTable(bindTable, bindcheck,
								TableColumnCommon.getInstance()
										.getBindCheckColumn());
					} else {
						// 테이블의 사이즈 가 0 일경우 NULL 을 표시해서 집어넣는다.
						bindcheck.add(new BindCheckDto("N", "U", "L", "L",
								" "));
						common.insertTable(bindTable, bindcheck,
								TableColumnCommon.getInstance()
										.getBindCheckColumn());
					}
					
					SessionMonDao dao = SessionMonDao.getInstance();
					dao.getTextCheckSqlData(sqlId);
					dao.getPlanCheckSqlData(sqlId);
					common.insertText(fulltext, ConstantCommon.sqlFullText);
					common.insertText(plantext, ConstantCommon.planText);
				}
			});
			return row;
		});
		
		instance = this;
	}
	
	/**
	 * 
	* 1. 메소드명 : CellColor
	* 2. 작성일 : 2015. 8. 21. 오후 1:48:46
	* 3. 작성자 : 정석준
	* 4. 설명 : Table Column Background Color 설정
	* @param list
	* @param column
	 */
	private void CellColor(List list , TableColumn column){
    	column.setCellFactory(new Callback<TableColumn<List,String>,TableCell<List, String>>(){
			@Override
			public TableCell<List, String> call(
				 TableColumn<List, String> column ) {
				return new BackgroundTableCell();
			}
		});
    }
	
	/**
	 * 
	* 1. 메소드명 : sessionSearchHandle
	* 2. 작성일 : 2015. 8. 21. 오후 1:49:22
	* 3. 작성자 : 길용현
	* 4. 설명 : Session Monitoring 쪽 Search 버튼 이벤트 처리
	* @param event
	 */
    public void sessionSearchHandle() {
		if(!DBConnection.isConnection()){
			DialogCommon.alert("데이터베이스에 연결되어있지 않습니다.");
			return;
		}
		
		ControlCommon common = ControlCommon.getInstance();
		ArrayList<SessionCheckDto> sessioncheck = SessionMonDao.getInstance().getSessionCheckSqlData();
		
		if (sessioncheck.size() != 0) {
			System.out.println(sessionTable);
			common.insertTable(sessionTable, sessioncheck, TableColumnCommon.getInstance().getSessionCheckColumn());
			CellColor(SessionMonDao.getInstance().getSessionCheckSqlData(),logonCol);
		}
    }
}
