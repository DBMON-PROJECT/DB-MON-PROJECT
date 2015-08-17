package ztestKYH;

import java.util.ArrayList;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class test {
	public static void main(String[] args) {
		XYChart.Series<String, Number> Series = new XYChart.Series<String, Number>(); 
		ObservableList list = FXCollections.observableArrayList(Series);
		
		//System.out.println(list.toString());
		System.out.println(Series.getData().toString());
		System.out.println(Series.getData().size());
		System.out.println();
		//System.out.println(list.size());
		
		Series.getData().add(new XYChart.Data<String,Number>("1", (Number)1));
		Series.getData().add(new XYChart.Data<String,Number>("2", (Number)2));
		Series.getData().add(new XYChart.Data<String,Number>("3", (Number)3));
		
		//System.out.println(list.toString());
		System.out.println(Series.getData().toString());
		System.out.println(Series.getData().size());
		System.out.println();
		//System.out.println(list.size());
		
		Series.getData().clear();
		
		//System.out.println(list.toString());
		System.out.println(Series.getData().toString());
		System.out.println(Series.getData().size());
		System.out.println();
//		System.out.println(list.size());
		
		Series.getData().add(new XYChart.Data<String,Number>("4", (Number)1));
		Series.getData().add(new XYChart.Data<String,Number>("5", (Number)2));
//		Series.getData().add(new XYChart.Data<String,Number>("6", (Number)3));
		
		//System.out.println(list.toString());
		System.out.println(Series.getData().toString());
		System.out.println(Series.getData().size());
		System.out.println(Series.getData().get(Series.getData().size()-1));
		System.out.println();
//		System.out.println(list.size());
	}
}
