package sql;

/**
 * 
* 1. 패키지명 : sql
* 2. 타입명 : SchemaSQL.java
* 3. 작성일 : 2015. 8. 21. 오후 2:25:51
* 4. 작성자 : 조성일
* 5. 설명 : schema monitoring 관련 SQL 목록
 */
public class SchemaSQL {
	/**
	 * 오브젝트 별 사용량 비율 비교
	 * 기존에 생성되어있는 것들은 제외하고 생성된것과 기타값으로 비교
	 */
	public static String objectcomp =
			"WITH N_DEF_USER AS ( "
				  +"SELECT OWNER, "
				         +"COUNT(*) OBJ_CNT "
				  +"FROM DBA_OBJECTS "
				  +"WHERE OWNER NOT IN ('SYS', "
				              +"'OWBSYS_AUDIT', "
				              +"'MDSYS', "
				         //     +"'PUBLIC', "
				          //    +"'OUTLN', "
				              +"'CTXSYS', "
				              +"'OLAPSYS', "
				              +"'FLOWS_FILES', "
				              +"'OWBSYS', "
				              +"'HR', "
				              +"'SYSTEM', "
				        //      +"'ORACLE_OCM', "
				              +"'EXFSYS', "
				              +"'APEX_030200', "
				        //      +"'SCOTT', "
				              +"'SH', "
				              +"'OE', "
				              +"'PM', "
				              +"'DBSNMP', "
				              +"'ORDSYS', "
				              +"'ORDPLUGINS', "
				         //     +"'SYSMAN', "
				              +"'IX', "
				              +"'APPQOSSYS', "
				              +"'XDB', "
				         //     +"'ORDDATA', "
				              +"'BI', "
				         //     +"'WMSYS', "
				              +"'SI_INFORMTN_SCHEMA') "
				  +"GROUP BY OWNER "
				+"), DEF_USER AS ( "
				  +"SELECT ' ETC' OWNER, "
				          +"COUNT(*) OBJ_CNT "
				  +"FROM DBA_OBJECTS "
				  +"WHERE OWNER IN ('SYS', "
				              +"'OWBSYS_AUDIT', "
				              +"'MDSYS', "
				              +"'PUBLIC', "
				              +"'OUTLN', "
				              +"'CTXSYS', "
				              +"'OLAPSYS', "
				              +"'FLOWS_FILES', "
				              +"'OWBSYS', "
				              +"'HR', "
				              +"'SYSTEM', "
				              +"'ORACLE_OCM', "
				              +"'EXFSYS', "
				              +"'APEX_030200', "
				              +"'SCOTT', "
				              +"'SH', "
				              +"'OE', "
				              +"'PM', "
				              +"'DBSNMP', "
				              +"'ORDSYS', "
				              +"'ORDPLUGINS', "
				              +"'SYSMAN', "
				              +"'IX', "
				              +"'APPQOSSYS', "
				              +"'XDB', "
				              +"'ORDDATA', "
				              +"'BI', "
				              +"'WMSYS', "
				              +"'SI_INFORMTN_SCHEMA') "
				+") "
				+"SELECT A.OWNER, "
				     +"A.OBJ_CNT, "
				     +"ROUND((A.OBJ_CNT/B.TOTAL_CNT)*100, 3) OBJ_PER "       
				+"FROM (SELECT OWNER, "
				           +"OBJ_CNT "
				    +"FROM N_DEF_USER) A, (SELECT COUNT(*) TOTAL_CNT "
				                         +"FROM DBA_OBJECTS) B "
				+"UNION ALL "
				+"SELECT A.OWNER, "
				     +"A.OBJ_CNT, "
				     +"ROUND((A.OBJ_CNT/B.TOTAL_CNT)*100, 3) OBJ_PER "       
				+"FROM (SELECT OWNER, "
				           +"OBJ_CNT "
				     +"FROM DEF_USER) A, (SELECT COUNT(*) TOTAL_CNT "
				                       +"FROM DBA_OBJECTS) B ";
	/**
	 * 현재 사용중인 사용자 계정들의 리스트
	 */
	public static String getuserlist = 
			"SELECT * "
					  +"FROM (SELECT DISTINCT OWNER "
					          +"FROM ALL_OBJECTS "
					         +"WHERE OWNER NOT IN ('PUBLIC', "
					                       +"'XDB', "
					                       +"'MDSYS',"
					                       +"'CTXSYS', "
					                       +"'OLAPSYS', "
					                       +"'SYSTEM', "
					                       +"'EXFSYS', "
					                       +"'APEX_030200', "
					                       +"'ORDSYS', "
					                       +"'ORDPLUGINS', "
					                       +"'DBSNMP', "
					                       +"'ORDDATA', "
					                       +"'SYS', "
					                       +"'WMSYS', "
					                       +"'OWBSYS_AUDIT', "
					                       +"'OUTLN', "
					                       +"'FLOWS_FILES', "
					                       +"'OWBSYS', "
					                       +"'HR', "
					                       +"'ORACLE_OCM', "
					                       +"'SCOTT', "
					                       +"'SH', "
					                       +"'OE', "
					                       +"'PM', "
					                       +"'SYSMAN', "
					                       +"'IX', "
					                       +"'APPQOSSYS', "
					                       +"'BI', "
					                       +"'SI_INFORMTN_SCHEMA' )) "
					 +"UNION  "
					        +"(SELECT USERNAME "
					          +"FROM DBA_USERS "
					         +"WHERE USERNAME NOT IN ('MGMT_VIEW', "
					                       +"'SYS', "
					                       +"'SYSTEM', "
					                       +"'DBSNMP', "
					                       +"'SYSMAN', "
					                       +"'OUTLN', "
					                       +"'FLOWS_FILES', "
					                       +"'MDSYS', "
					                       +"'ORDSYS', "
					                       +"'EXFSYS', "
					                       +"'WMSYS', "
					                       +"'APPQOSSYS', "
					                       +"'APEX_030200', "
					                       +"'OWBSYS_AUDIT', "
					                       +"'ORDDATA', "
					                       +"'CTXSYS', "
					                       +"'ANONYMOUS', "
					                       +"'XDB', "
					                       +"'ORDPLUGINS', "
					                       +"'OWBSYS', "
					                       +"'SI_INFORMTN_SCHEMA', "
					                       +"'SCOTT', "
					                       +"'ORACLE_OCM', "
					                       +"'XS$NULL', "
					                       +"'BI', "
					                       +"'PM', "
					                       +"'MDDATA', "
					                       +"'IX', "
					                       +"'SH', "
					                       +"'DIP', "
					                       +"'OE', "
					                       +"'APEX_PUBLIC_USER', "
					                       +"'HR', "
					                       +"'SPATIAL_CSW_ADMIN_USR', "
					                       +"'SPATIAL_WFS_ADMIN_USR', "
					                       +"'OLAPSYS' )) ";
	
	/**
	 * 선택된 유저의 테이블의 용량 비교
	 */
	public static String tablecomp = 
			"SELECT DBA_TABLES.TABLE_NAME, "
					+"ROUND(SUM(BYTES)/1024/1024, 3)  SIZE_MB "
					+"FROM  DBA_SEGMENTS , DBA_TABLES "
					+"WHERE DBA_SEGMENTS.SEGMENT_NAME  = DBA_TABLES.TABLE_NAME "
					+"AND   DBA_SEGMENTS.SEGMENT_TYPE IN('TABLE') "
					+"AND   DBA_SEGMENTS.OWNER = ? " //PC%대신 ?를 사용
					+"GROUP BY DBA_TABLES.TABLE_NAME ";

	/**
	 * 선택된 유저의 인덱스의 용량 비교
	 */
	public static String indexcomp =
				  "SELECT A.SEGMENT_NAME, "
			            +"ROUND(SUM(A.BYTES)/1024/1024, 3) SIZE_MB "
				 +"FROM DBA_SEGMENTS A, "
				      +"DBA_INDEXES B "
				 +"WHERE A.SEGMENT_NAME = B.INDEX_NAME "
				 +"AND   A.SEGMENT_TYPE IN('INDEX','INDEXPARTITION') "
				 +"AND   A.OWNER = ? "		//PC22대신 ?를 사용
				 +"GROUP BY A.SEGMENT_NAME, A.SEGMENT_TYPE "
				 +"ORDER BY 2 DESC ";
	
	/**
	 * 테이블스페이스 용량 조회
	 */
	public static String table_amount_check = 
			//테이블스페이스별 용량 확인 쿼리문(MB 단위)
			"SELECT B.TABLESPACE, "       //테이블스페이스 이름 
			    +"B.TOTALMB, "            //전체용량 MB
			    +"B.USEDMB, "             //사용중인 용량 MB
			    +"B.FREEMB, "             //사용가능 용량 MB
			    +"B.USED_PER, "           //사용중인 용량 퍼센트
			    +"B.FREE_PER, "           //사용가능 용량 퍼센트
			    +"B.MAX_DBF_MB, "         //각 테이블 스페이스 데이터파일의 가장 큰 용량
			    +"B.MIN_DBF_MB, "         //각 테이블 스페이스 데이터파일의 가장 작은 용량
			    +"B.AVG_DBF_MB, "         //각 테이블 스페이스 데이터파일의 평균 용량
			    +"B.DISTRIBUTION DISTRIBUTION_PER, "   // 평균용량+최소용량/평균용량*100 
			     
			    +"CASE WHEN B.DISTRIBUTION > 90 THEN 'DANGER' "  //위험
			         +"WHEN B.DISTRIBUTION > 70 THEN 'WARNING' " //경고
			         +"WHEN B.DISTRIBUTION > 50 THEN 'PURPOSE' " //적합
			         +"WHEN B.DISTRIBUTION = 0  THEN 'PERFECT' " //완벽
			    +"END DIST_STATUS, "   //분포 상태
			    +"B.FILE_CNT "
			+"FROM (SELECT   SUBSTR(A.TABLESPACE_NAME,1,30) TABLESPACE, "
			       +"ROUND(SUM(A.TOTAL1)/1024/1024,1) TOTALMB, "
			       +"ROUND(SUM(A.TOTAL1)/1024/1024,1)-ROUND(SUM(A.SUM1)/1024/1024,1) USEDMB, "
			       +"ROUND(SUM(A.SUM1)/1024/1024,1) FREEMB, "
			       +"ROUND((ROUND(SUM(A.TOTAL1)/1024/1024,1)-ROUND(SUM(A.SUM1)/1024/1024,1))/ROUND(SUM(A.TOTAL1)/1024/1024,1)*100,2) USED_PER, "
			       +"ROUND(ROUND(SUM(A.SUM1)/1024/1024,1)/ROUND(SUM(A.TOTAL1)/1024/1024,1)*100,2) FREE_PER, "
			       +"ROUND(SUM(A.MAXB)/1024/1024,3) MAX_DBF_MB, "
			       +"ROUND(SUM(A.MINB)/1024/1024,3) MIN_DBF_MB, "
			       +"ROUND(SUM(A.AVGB)/1024/1024,3) AVG_DBF_MB, "
			         
			       // ((평균용량-최소용량/평균용량*100)+(최대용량+평균용량/최대용량*100)/2)  파일용량들의 분포도 측정을 위해 사용
			       +"(ROUND((ROUND(SUM(A.AVGB)/1024/1024,3)-ROUND(SUM(A.MINB)/1024/1024,3))/ROUND(SUM(A.AVGB)/1024/1024,3)*100,2) "
			       +"+ROUND((ROUND(SUM(A.MAXB)/1024/1024,2)-ROUND(SUM(A.AVGB)/1024/1024,3))/ROUND(SUM(A.MAXB)/1024/1024,2)*100,2))/2 DISTRIBUTION, "
			       +"MAX(A.DFCNT) FILE_CNT "  //각 테이블 스페이스 데이터파일의 개수
			+"FROM (SELECT   TABLESPACE_NAME, "  
			               +"0                    TOTAL1, "
			               +"SUM(BYTES)           SUM1, "    // 사용가능 용량(BYTES)
			               +"0                    MAXB, "
			               +"0                    MINB, "
			               +"0                    AVGB, "
			               +"0                    DFCNT "
			        +"FROM     DBA_FREE_SPACE "       //나머지 공간(빈공간)
			        +"GROUP BY TABLESPACE_NAME "
			        +"UNION "
			        +"SELECT   TABLESPACE_NAME, "
			                 +"SUM(BYTES), "      //전체 용량(BYTES)
			                 +"0, "
			                 +"MAX(BYTES), "      //데이터파일 중 가장 큰 값
			                 +"MIN(BYTES), "      //데이터파일 중 가장 작은 값
			                 +"AVG(BYTES), "      //데이터파일들의 평균 값
			                 +"COUNT(TABLESPACE_NAME) "  //데이터파일의 개수
			        +"FROM     DBA_DATA_FILES "   //테이터 파일의 값들
			        +"GROUP BY TABLESPACE_NAME) A "
			+"GROUP BY A.TABLESPACE_NAME "
			+"ORDER BY TABLESPACE) B ";
}
