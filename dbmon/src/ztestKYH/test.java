package ztestKYH;

import java.util.ArrayList;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class test {
	
	private static XYChart.Series<String, Number> Series; 
	private static ObservableList list;
	
	public static void main(String[] args) {
		ArrayList<XYChart.Series<String, Number>> arraylist;
		arraylist = new ArrayList<XYChart.Series<String,Number>>();
		
		arraylist.add(Series);
		
		setting(arraylist);
		
		System.out.println(Series);
		System.out.println(arraylist.get(0));
	}
	
	private static void setting(ArrayList<XYChart.Series<String, Number>> arraylist){
		XYChart.Series<String, Number> t = arraylist.get(0);
		t = new XYChart.Series<String, Number>();
		arraylist.set(0, t);
	}
}