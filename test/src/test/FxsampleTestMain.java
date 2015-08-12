package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FxsampleTestMain extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/test/fxsampleTest.fxml"));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setTitle("기본 컨트롤 사용법");
		stage.show();
	
		
		

	}
	public static void main(String[] args) {
		launch(args);
	}
}
