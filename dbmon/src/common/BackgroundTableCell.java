package common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TableCell;

/**
 * 
* 1. 패키지명 : common
* 2. 타입명 : BackgroundTableCell.java
* 3. 작성일 : 2015. 8. 21. 오후 12:48:51
* 4. 작성자 : 정석준
* 5. 설명 : 지정한cell의 css 적용을 위한 클래스
* 		   tableview 에 대한 cell factory를 생성
 */
public class BackgroundTableCell extends TableCell<List, String> {
	
//	private static final Logger LOG = Logger.getLogger(BackgroundTableCell.class);
	
	//css에서 가져올수 있게
	private String CSS_RED ="cell-red";
	private String CSS_YELLOW="cell-yellow";
	private String CSS_GREEN ="cell-green";
	
	/**
	 * 
	 * @Override
	* 1. 메소드명 : updateItem
	* 2. 작성일 : 2015. 8. 11. 오후 1:39:51
	* 3. 작성자 : 정석준
	* 4. 설명 : cell이 가지는 컨텐트를 재정의함
	* @param String
	* @param boolean
	 */
	protected void updateItem(String item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
			
		setText(empty ?"":item);
		
			if(!(item==null)){			
				getStyleClass().removeAll(CSS_RED,CSS_YELLOW,CSS_GREEN);			
		        updateStyles(empty ? null : item);	
			}
	}

	/**
	 * 
	* 1. 메소드명 : updateStyles
	* 2. 작성일 : 2015. 8. 21. 오후 12:49:53
	* 3. 작성자 : 정석준
	* 4. 설명 : 값(item)을 가져와서 getStyleClass로 css 를 불러온다.
	* @param item
	 */
	private void updateStyles(String item) {

		String par="(19[7-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):.*";
		Pattern pattern = Pattern.compile(par);
		Matcher matc = pattern.matcher(item);
		
		if(item == null){
			return;
		}else if(matc.matches()){
			Calendar today = Calendar.getInstance();
			today.add(today.HOUR,-2);
			Calendar itemTime = Calendar.getInstance();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
			Date itemTo;
			try {
				itemTo = transFormat.parse(item);
				itemTime.setTime(itemTo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//itemTime은 today보다 이전인가?
			if(itemTime.before(today)){	
				getStyleClass().add(CSS_RED);
			}
		}else{			
			if('0'<=item.charAt(0)&&item.charAt(0)<='9'){
				double item1=Double.parseDouble(item);
				if(90<item1){
					getStyleClass().add(CSS_GREEN);
				}else if(item1<20){
					getStyleClass().add(CSS_RED);
				}else{
					
				}
			}else if('A'<=item.charAt(0)&&item.charAt(0)<='Z'){
				if(item.endsWith("T")){
					getStyleClass().add(CSS_GREEN);
				}else if(item.startsWith("W")){
					getStyleClass().add(CSS_YELLOW);
				}else if(item.startsWith("D")){
					getStyleClass().add(CSS_RED);
				}else {
					
				}
			}
		}
	}
}

	
	

