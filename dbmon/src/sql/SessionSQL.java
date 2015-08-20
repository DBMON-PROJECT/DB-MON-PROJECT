package sql;

public class SessionSQL {
		public static String sessionCheck=  "SELECT B.SQL_ID \"sqlId\" , "
		         +"A.OSUSER \"osuser\", "
		         +"A.LOGON_TIME \"logonTime\", "
		         +"A.PADDR \"paddr\", "
		         +"A.USERNAME \"userName\", "
		         +"A.TYPE \"type\" "                      
		         +"FROM V$SESSION A , "
		         +"(SELECT DISTINCT NVL(SQL_ID, PREV_SQL_ID) SQL_ID "
		                    +"FROM V$SESSION "
		                    +"WHERE USERNAME IS NOT NULL "
		                    +"AND USERNAME NOT IN ('SYSMAN','DBSNMP'))B " 
		         +"WHERE A.SQL_ID = B.SQL_ID ";
	
	
	
	
	public static String bindCheck ="SELECT A.SNAP_ID  \"sanpId\" , "                  
		       +"A.SQL_ID \"sqlId\" , "                   
		       +"A.NAME \"name\" , "                     
		       +"A.POSITION \"position\" , "                 
		       +"A.DATATYPE_STRING \"datatypeString\" , "          
		       +"A.LAST_CAPTURED \"lastCaptured\" "             
		  +"FROM DBA_HIST_SQLBIND A, "         
		       +"(SELECT SQL_ID, "
		               +"MAX(LAST_CAPTURED) AS MAX_CAPTURED "
		          +"FROM DBA_HIST_SQLBIND "
		          +"WHERE SQL_ID= ? "        
		          +"GROUP BY SQL_ID) B "         
		 +"WHERE A.SQL_ID = B.SQL_ID "
		 +"AND A.LAST_CAPTURED=B.MAX_CAPTURED "
		 +"ORDER BY A.POSITION ";
	/**
	 * 선택된 세션에 해당되는 텍스트 출력
	 */
	public static String textcheck = 
			 " SELECT SQL_FULLTEXT "
			+" FROM V$SQL "
			+" WHERE SQL_ID = ? ";//SQL_ID에 ?값 넣어줘야할듯
	
	
	public static String plancheck = " WITH PLAN_TEXT AS (SELECT RN, "+
						            " PLAN_TABLE_OUTPUT  "+
						            " FROM (SELECT rownum RN, "+
						            " PLAN_TABLE_OUTPUT "+
						            " FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR( ? , NULL, 'ALLSTATS LAST')) ) ), "+
						            " START_NUM AS (SELECT MIN(RN) RN"+
						            " FROM (SELECT RN "+
						            " FROM PLAN_TEXT "+
						            " WHERE PLAN_TABLE_OUTPUT LIKE 'Plan hash value%' ) ), "+
						            " END_NUM AS(SELECT MIN(RN) RN "+
						            " FROM (SELECT RN "+
						            " FROM PLAN_TEXT "+
						            " WHERE RN > (SELECT RN "+
						            " FROM START_NUM) "+
						            " AND PLAN_TABLE_OUTPUT LIKE 'Predicate Information%') ) "+
						            " SELECT PLAN_TABLE_OUTPUT  \"planTableOutPut\" "+
						            " FROM PLAN_TEXT "+
						            " WHERE RN BETWEEN (SELECT RN "+
						            " FROM START_NUM)+3 AND (SELECT RN "+
						            " FROM END_NUM)-3 ";
}
