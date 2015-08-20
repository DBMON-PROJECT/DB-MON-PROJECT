package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
* 1. 패키지명 : main.service
* 2. 타입명 : DBMonMain.java
* 3. 작성일 : 2015. 8. 12. 오전 9:22:31
* 4. 작성자 : 길용현
* 5. 설명 : DBMon Project Main 클래스
 */
public class DBMonMain extends Application{
	public static Stage mainStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("메인 화면");
		primaryStage.show();
	}
}
