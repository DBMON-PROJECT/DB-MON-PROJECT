package ztestKYH;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.sun.javafx.application.LauncherImpl;

public class Test_Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		VBox root = FXMLLoader.load(getClass().getResource("/ztestKYH/test.fxml"));
		
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("메인 화면");
		primaryStage.show();
	}
}
