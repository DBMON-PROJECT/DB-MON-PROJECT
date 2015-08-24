package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import org.apache.log4j.Logger;

import common.DialogCommon;

import db.DBConnection;

/**
 * 
* 1. 패키지명 : controller
* 2. 타입명 : LogFileController.java
* 3. 작성일 : 2015. 8. 23. 오후 12:28:55
* 4. 작성자 : 정석준
* 5. 설명 : 로그 파일 프로파일링 클래스
 */
public class LogFileController implements Initializable{
	
	private static final Logger LOG = Logger.getLogger(LogFileController.class);

    @FXML private ListView<String> logListView;

    @FXML private TextArea logText;

    private static String logFileName ;
    
    public static LogFileController instance;
    
    /*
     * LOGFILE 이 저장된 log 파일 의 개수와 데이터 저장
     */
    
    private void getLogFiledata(){
    	logFileName = System.getProperty("user.dir")+"/log/";
		
		 File file = new File(logFileName);
	        String[] list = file.list(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.toString() != null;
				}
	        }
	        );

	        ObservableList<String> list1 = FXCollections.observableArrayList();
	        list1.addAll(list);
	        
	        logListView.setItems(list1);
    }
    
    /**
     * 
    * 1. 메소드명 : logfileSearchHandle
    * 2. 작성일 : 2015. 8. 23. 오후 12:31:51
    * 3. 작성자 : 정석준
    * 4. 설명 : 로그 파일 프로파일링 메서드
     */
    public void logfileSearchHandle(){
    	getLogFiledata();

		logListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {
					String selectfilename = logListView.getSelectionModel().getSelectedItem();
					String filename = logFileName + selectfilename;
					try {
						FileReader fr = new FileReader(filename);

						BufferedReader br = new BufferedReader(fr);
						StringBuffer sb = new StringBuffer();

						while (br.ready()) {
							sb.append(br.readLine() + "\r\n");
						}
						
						logText.setText(sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}
