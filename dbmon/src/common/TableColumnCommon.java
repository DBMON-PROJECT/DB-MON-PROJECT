package common;

/**
 * 
* 1. 패키지명 : common
* 2. 타입명 : TableColumnCommon.java
* 3. 작성일 : 2015. 8. 12. 오후 12:24:06
* 4. 작성자 : 길용현
* 5. 설명 : 테이블에 대한 컬럼 정보를 가지는 클래스
 */
public class TableColumnCommon {
	private static TableColumnCommon instance;
	
	private TableColumnCommon(){
		
	}
	
	public static TableColumnCommon getInstance(){
		if(instance == null){
			instance = new TableColumnCommon();
		}
		
		return instance;
	}
	
	/**
	 * 
	* 1. 메소드명 : getLoginLogColumn
	* 2. 작성일 : 2015. 8. 12. 오후 12:24:29
	* 3. 작성자 : 길용현
	* 4. 설명 : 로그인 로그 관련 컬럼 정보
	* @return
	 */
	public String[] getLoginLogColumn(){
		String[] type = new String[11];
		type[0] = "tnsName";
		type[1] = "userName";
		type[2] = "connectTime";
		
		return type;
	}
}
