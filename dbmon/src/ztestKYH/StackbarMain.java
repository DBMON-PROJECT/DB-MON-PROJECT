package ztestKYH;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StackbarMain extends Application{
	public static AnchorPane root;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		AnchorPane root = FXMLLoader.load(getClass().getResource("/ztestKYH/stackbar.fxml"));
		
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("메인 화면");
		primaryStage.show();
	}
}
