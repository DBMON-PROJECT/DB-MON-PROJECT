package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import common.BackgroundTableCell;
import common.ControlCommon;
import common.DialogCommon;
import common.TableColumnCommon;
import dao.SessionMonDao;
import db.DBConnection;
import dto.BindCheckDto;
import dto.SessionCheckDto;


public class SessionController implements Initializable{
	//Tap 2 에 쓰이는 기능
    @FXML
    private TextArea fulltext;

    @FXML
    private TextArea plantext;

    @FXML
    private Button sessionButton;
    
    //SessionCheckTableView
    @FXML
    private TableView sessionTable;
    @FXML
    private TableColumn logonCol;
     
    //BindCheckTableView
    @FXML
    private TableView bindTable;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// session테이블 select 시 select 한 sqlId 를 값을 받는다.
		sessionTable.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<SessionCheckDto>() {
					@Override
					public void changed(
							ObservableValue<? extends SessionCheckDto> observable,
							SessionCheckDto oldValue, SessionCheckDto newValue) {

						// String id="bn4b3vjw2mj3u"; //임시 id 의 값
						ControlCommon common = ControlCommon.getInstance();
						String id = newValue.getSqlId(); // 원래의 select 값
						try {
							ArrayList<BindCheckDto> bindcheck = SessionMonDao
									.getInstance().getBindCheckSqlData(id);
							if (bindcheck.size() != 0) {
								common.insertTable(bindTable, bindcheck,
										TableColumnCommon.getInstance()
												.getBindCheckColumn());
							} else {
								// 테이블의 사이즈 가 0 일경우 NULL 을 표시해서 집어넣는다.
								bindcheck.add(new BindCheckDto("N", "U", "L",
										"L", " "));
								common.insertTable(bindTable, bindcheck,
										TableColumnCommon.getInstance()
												.getBindCheckColumn());
							}

							String textcheck = SessionMonDao.getInstance()
									.getTextCheckSqlData(id);
							common.insertText(fulltext, textcheck);

							StringBuffer plancheck = SessionMonDao
									.getInstance().getPlanCheckSqlData(id);
							common.insertText(plantext, plancheck);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	
	/*
	 * 
	 * 
	 * 
	 * 기능 : CellColor 는 테이블 의 리스트와 column 이름을 가지고 와서 Cell을 선택하고 
	 * 		 new BackgroundTableCell() 새로 불러온다.
	 */ 
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
	
	@FXML
    void sessionSearchHandle(ActionEvent event) {
		if(!DBConnection.isConnection()){
			DialogCommon.alert("데이터베이스에 연결되어있지 않습니다.");
			return;
		}
		
		try {
			ControlCommon common = ControlCommon.getInstance();
			ArrayList<SessionCheckDto> sessioncheck = SessionMonDao.getInstance().getSessionCheckSqlData();
			if(sessioncheck.size() != 0){
				common.insertTable(sessionTable, sessioncheck, TableColumnCommon.getInstance().getSessionCheckColumn());
				CellColor(SessionMonDao.getInstance().getSessionCheckSqlData() , logonCol);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}
