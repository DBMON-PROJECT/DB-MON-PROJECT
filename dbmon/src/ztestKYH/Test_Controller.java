package ztestKYH;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class Test_Controller implements Initializable{
	@FXML
    private Button changeBtn;
	@FXML
    private CategoryAxis categoryAxis;
    @FXML
    private LineChart<?, ?> lineChart;
    
    private static XYChart.Series<String, Number> Series = new XYChart.Series<String, Number>(); 
    private static ObservableList list = FXCollections.observableArrayList(Series);
    
    @FXML
    void changeHandle(ActionEvent event) {
    	/*Series = new XYChart.Series<String, Number>();
    	list = FXCollections.observableArrayList(Series);
    	lineChart.setData(list);
		Series.getData().add(new XYChart.Data<String,Number>("1", (Number)1));
		Series.getData().add(new XYChart.Data<String,Number>("2", (Number)2));
		
		System.out.println(Series.getNode().getStyle());*/
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lineChart.setData(list);
		
		
		
		Node node = lineChart.lookup(".default-color0.chart-series-line");
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-stroke: #9CC4E4, 1;");
		node.setStyle(sb.toString());
	
		/*Set<Node> node1 = lineChart.lookupAll(".default-color0.chart-line-symbol.series0");
		//sb.append("-fx-background-color: #9CC4E4;");
		//sb.append("-fx-symbol-color: #9CC4E4;");
		System.out.println(0);
		for(Node n : node1){
			System.out.println(1);
			n.setStyle(sb.toString());
		}*/
		System.out.println(2);
		Series.getData().add(new XYChart.Data<String,Number>("1", (Number)1));
		Series.getData().add(new XYChart.Data<String,Number>("2", (Number)2));
		Series.getData().add(new XYChart.Data<String,Number>("3", (Number)3));
		Series.getData().add(new XYChart.Data<String,Number>("4", (Number)4));
		
		 /*Set<Node> lookupAll = lineChart.lookupAll(".series" + i);
	      for (Node n : lookupAll) {
	         n.setStyle("-fx-stroke-width: 3px;");
	     }*/
	}
    
    
}
