package common;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * 
* 1. 패키지명 : common
* 2. 타입명 : DialogCommon.java
* 3. 작성일 : 2015. 8. 12. 오후 12:24:53
* 4. 작성자 : 길용현
* 5. 설명 : Dialog 생성하는 공통 클래스
 */
public class DialogCommon {

	/**
	 * 
	* 1. 메소드명 : alert
	* 2. 작성일 : 2015. 8. 12. 오후 12:25:21
	* 3. 작성자 : 길용현
	* 4. 설명 : alert 창 생성
	* @param msg
	 */
	public static void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("연결");
		alert.setContentText(msg);

		if (alert.showAndWait().get() == ButtonType.OK) {

		}
	}

	/**
	 * 
	* 1. 메소드명 : confirm
	* 2. 작성일 : 2015. 8. 12. 오후 12:25:30
	* 3. 작성자 : 길용현
	* 4. 설명 : confirm 창 생성
	* @param msg
	* @return
	 */
	public static boolean confirm(String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle("확인");
		alert.setContentText(msg);

		if (alert.showAndWait().get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
