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
	
	/**
	 * 
	* 1. 메소드명 : getSessionCheckColumn
	* 2. 작성일 : 2015. 8. 21. 오후 1:45:02
	* 3. 작성자 : 정석준
	* 4. 설명 : sessionTable 관련 컬럼 정보
	* @return
	 */
	public String[] getSessionCheckColumn(){
		String[] type = new String[9];
		type[0] = "paddr";
		type[1] = "osUser";
		type[2] = "sqlId";
		type[3] = "command";
		type[4] = "username";
		type[5] = "type";
		type[6] = "status";
		type[7] = "program";
		type[8] = "logonTime";
		
		return type;		
	}
	
	/**
	 * 
	* 1. 메소드명 : getBindCheckColumn
	* 2. 작성일 : 2015. 8. 21. 오후 1:45:27
	* 3. 작성자 : 정석준
	* 4. 설명 : bindTable 관련 컴럼 정보
	* @return
	 */
	public String[] getBindCheckColumn(){
		String[] type = new String[6];
		type[0]="sanpId";
		type[1]="sqlId";
		type[2]="name";
		type[3]="position";
		type[4]="datatypeString";
		type[5]="lastCaptured";

		return type;
	}
	
	/**
	 * 
	* 1. 메소드명 : getTablespaceColumn
	* 2. 작성일 : 2015. 8. 21. 오후 1:45:56
	* 3. 작성자 : 
	* 4. 설명 : tablespaceTable 관련 컬럼 정보
	* @return
	 */
	public String[] getTablespaceColumn(){
		String[] type = new String[12];
		type[0]="tablespace";
		type[1]="totalMb";
		type[2]="usedMb";
		type[3]="freeMb";
		type[4]="usedPer";
		type[5]="freePer";
		type[6]="maxMb";
		type[7]="minMb";
		type[8]="avgMb";
		type[9]="distributePer";
		type[10]="distState";
		type[11]="fileCnt";
		
		return type;
	}
}
