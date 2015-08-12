package common;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
* 1. 패키지명 : common
* 2. 타입명 : ControlCommon.java
* 3. 작성일 : 2015. 8. 11. 오후 12:28:26
* 4. 작성자 : 길용현
* 5. 설명 : FX Object 에 관한 공통 기능 클래스
 */
public class ControlCommon {
	private static ControlCommon instance;
	
	private ControlCommon(){
		
	}
	
	public static ControlCommon getInstance(){
		if(instance == null){
			instance = new ControlCommon();
		}
		
		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : insertTable
	* 2. 작성일 : 2015. 8. 11. 오후 1:39:19
	* 3. 작성자 : 길용현
	* 4. 설명 : TableView 데이터 삽입
	* @param tableView
	* @param list
	* @param columnName
	 */
	public void insertTable(TableView tableView, List list, String[] columnName){
		ObservableList<TableColumn> colList = tableView.getColumns();
		ObservableList dataList = FXCollections.observableArrayList(list);
		int i = 0;
		
		for(TableColumn col : colList ){
			col.setCellValueFactory(
				new PropertyValueFactory<Object, Object>(columnName[i])
				);
			i++;
		}
		
		tableView.setItems(dataList);
	}
	
	/**
	 * 
	* 1. 메소드명 : insertListview
	* 2. 작성일 : 2015. 8. 11. 오후 1:39:39
	* 3. 작성자 : 길용현
	* 4. 설명 : ListView 데이터 삽입
	* @param listView
	* @param list
	 */
	public void insertListview(ListView listView, List list){
		ObservableList dataList = FXCollections.observableArrayList(list);
		listView.setItems(dataList);
	}
	
	/**
	 * 
	* 1. 메소드명 : insertCombo
	* 2. 작성일 : 2015. 8. 11. 오후 1:39:51
	* 3. 작성자 : 길용현
	* 4. 설명 : ComboBox 전체 데이터 삽입
	* @param comboBox
	* @param list
	 */
	public void insertCombo(ComboBox comboBox, List list){
		ObservableList dataList = FXCollections.observableArrayList(list);
		comboBox.setItems(dataList);
		comboBox.setValue(list.get(0));
	}
	
	/**
	 * 
	* 1. 메소드명 : addCombo
	* 2. 작성일 : 2015. 8. 12. 오후 8:06:49
	* 3. 작성자 : 길용현
	* 4. 설명 : 기존 ComboBox 뒤에 값 추가
	* @param comboBox
	* @param obj
	 */
	public void addCombo(ComboBox comboBox, Object obj){
		comboBox.getItems().add(obj);
		comboBox.setValue(obj);
	}
}
