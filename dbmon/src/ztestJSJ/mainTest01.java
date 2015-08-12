package ztestJSJ;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.webkit.ThemeClientImpl;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainTest01 extends Application implements Initializable,Runnable{
	
	 	@FXML
	    private AreaChart<String, Number> acChart;

	    @FXML
	    private CategoryAxis acxAxis;

	    @FXML
	    private BarChart<String, Number> barChart;

	    @FXML
	    private Tab maintab;

	    @FXML
	    private CategoryAxis barxAxis;

	    @FXML
	    private LineChart<?, ?> lcChart;

	    @FXML
	    private NumberAxis baryAxis;

	    @FXML
	    private CategoryAxis lcxAxis;

	    @FXML
	    private NumberAxis lcyAxis;

	    @FXML
	    private Tab tab2;

	    @FXML
	    private NumberAxis acyAxis;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		List<String> list= new ArrayList<String>();
		
		for(int i =0;i<23;i++){
			list.add( String.valueOf(i));
		}
		List<Number> list1= new ArrayList<Number>();
		for(int i =0;i<23;i++){
			list1.add(i);
		}
	
	
		setBarChart(list,list1);
		setAcChart(list, list1);
		
		
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setBarChart(List list,List list1) {
		ObservableList<String> xaxis= FXCollections.observableArrayList();   //observableList X축 리스트 선언
		ObservableList<Number> yaxis= FXCollections.observableArrayList();	 //observableList Y축 리스트 선언
		xaxis.addAll(list);			//가지고온 x축 에 들어갈 list 값을 obserbleList x축 리스트에  대입
	
		yaxis.addAll(list1);		//가지고온 x축 에 들어갈 list 값을 obserbleList x축 리스트에  대입
		
		//barxAxis 			
		//baryAxis.set
		
//		barChart.setTitle("Bar");//barchart 의 이름
		barChart.setBarGap(0); 		    //바의 굵기 0이 굵어진다  클수로 얇아진다.
		baryAxis.setAutoRanging(false); //자동 설정 변경 
		baryAxis.setUpperBound(40);     //Y축 최대값
		baryAxis.setLowerBound(0);		//Y축 최소값
//		barxAxis.setTickLabelRotation(-90);  //회전

		XYChart.Series se1 = new XYChart.Series();			//XYChart 를 선언
		for(int i=0;i<xaxis.size();i++){
			se1.getData().add(new XYChart.Data(xaxis.get(i),yaxis.get(i)));
		}
		barChart.getData().add(se1);
	}
	
	public void setAcChart(List list,List list1) {
		ObservableList<String> xaxis= FXCollections.observableArrayList();   //observableList X축 리스트 선언
		ObservableList<Number> yaxis= FXCollections.observableArrayList();	 //observableList Y축 리스트 선언
		xaxis.addAll(list);			//가지고온 x축 에 들어갈 list 값을 obserbleList x축 리스트에  대입
		yaxis.addAll(list1);		//가지고온 x축 에 들어갈 list 값을 obserbleList x축 리스트에  대입

		acyAxis.setAutoRanging(false); //자동 설정 변경 
		acyAxis.setUpperBound(40);     //Y축 최대값
		acyAxis.setLowerBound(0);		//Y축 최소값
//		barxAxis.setTickLabelRotation(-90);  //회전

		XYChart.Series se1 = new XYChart.Series();			//XYChart 를 선언
		for(int i=0;i<xaxis.size();i++){
			se1.getData().add(new XYChart.Data(xaxis.get(i),yaxis.get(i)));
		}
		acChart.getData().add(se1);
		
		
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/uiMain.fxml"));
		
		
		VBox root = loader.load();
		
		
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	
	public static void main(String[] args) {
		launch(args);
		
		
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		List<String> list= new ArrayList<String>();
		
		for(int i =0;i<23;i++){
			list.add( String.valueOf(i));
		}
		List<Number> list1= new ArrayList<Number>();
		for(int i =0;i<23;i++){
			list1.add(i);
		}
	
	
		setBarChart(list,list1);
		setAcChart(list, list1);
		
		
		
	}
	
}
