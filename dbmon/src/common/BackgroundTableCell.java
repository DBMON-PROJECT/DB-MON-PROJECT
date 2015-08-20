package common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TableCell;

public class BackgroundTableCell extends TableCell<List, String> {
	
	
	
//	private static final Logger LOG = Logger.getLogger(BackgroundTableCell.class);
	//css에서 가져올수 있게
	private  String CSS_FP_ORIGINAL = "cell-freePer-origin";
	private  String CSS_FP_SAFE = "cell-freePer-safe";
	private  String CSS_FP_OVER = "cell-freePer-error";
	private  String CSS_DS_PER = "cell-distState-perfect";
	private  String CSS_DS_WAR = "cell-distState-warning";
	private  String CSS_DS_DAN = "cell-distState-danger";
	private  String CSS_SC_DAN = "cell-session-danger";
	
	protected void updateItem(String item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
			
		setText(empty ?"":item);
		
			if(!(item==null)){			
				getStyleClass().removeAll(CSS_FP_ORIGINAL, CSS_FP_OVER, CSS_FP_SAFE,CSS_DS_PER,CSS_DS_WAR,CSS_DS_DAN,CSS_SC_DAN);			
		        updateStyles(empty ? null : item);	
			}
		
	}

	private void updateStyles(String item) {

		String par="(19[7-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).(0)";
		Pattern pattern = Pattern.compile(par);
		Matcher matc = pattern.matcher(item);
		
		
		if(item == null){
			return;
		}else if(matc.matches()){
			try {
				Calendar today = Calendar.getInstance();
				today.add(today.HOUR,-2);
				Calendar itemTime = Calendar.getInstance();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date itemTo = transFormat.parse(item);
				itemTime.setTime(itemTo);
				//itemTime은 today보다 이전인가?
				if(itemTime.before(today)){
					
					getStyleClass().add(CSS_SC_DAN);
				}else if(itemTime.before(today)){//itemTime은 today보다 이후인가?
					getStyleClass().add(CSS_DS_PER);
					System.out.println(today.getTime()+"after"+itemTime.getTime());
				}else{
					System.out.println("today");
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{	
			
			if('0'<=item.charAt(0)&&item.charAt(0)<='9'){
				double item1=Double.parseDouble(item);
				if(90<item1){
					getStyleClass().add(CSS_FP_SAFE);
				}
				else if(item1<20){
					getStyleClass().add(CSS_FP_OVER);
				}else{
//					LOG.debug("else"+item1);
				}
			}else if('A'<=item.charAt(0)&&item.charAt(0)<='Z'){
				if(item.endsWith("T")){
					getStyleClass().add(CSS_DS_PER);
				}else if(item.startsWith("P")){
						
				}else if(item.startsWith("W")){
					getStyleClass().add(CSS_DS_WAR);
				}else if(item.startsWith("D")){
					getStyleClass().add(CSS_DS_DAN);
				}
			}
		}
	}
}

	
	

