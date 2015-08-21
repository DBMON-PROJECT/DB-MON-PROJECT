package sql;

/**
 * 
* 1. 패키지명 : sql
* 2. 타입명 : RealtimeSQL.java
* 3. 작성일 : 2015. 8. 21. 오후 2:25:30
* 4. 작성자 : 길용현
* 5. 설명 : Realtime Monitoring 관련 SQL 목록
 */
public class RealtimeSQL {
	public static String performenceSql = "SELECT 'BufferCacheHitRatio' TYPE, "+
		       "TRUNC((P1.VALUE+P2.VALUE-P3.VALUE)/(P1.VALUE+P2.VALUE)*100, 2) RATIO "+
		       "FROM V$SYSSTAT P1, "+
		            "V$SYSSTAT P2, "+
		            "V$SYSSTAT P3 "+
		       "WHERE P1.NAME = 'db block gets' "+ 
		       "AND P2.NAME = 'consistent gets' "+
		       "AND P3.NAME = 'physical reads' "+
		       "UNION ALL "+
		       "SELECT 'LibraryCacheHitRatio' TYPE, "+    
		              "ROUND((1-SUM (RELOADS)/SUM(PINS)),3)*100 RATIO "+
		       "FROM V$LIBRARYCACHE "+
		       "UNION ALL "+
		       "SELECT 'DictionaryHitRatio' TYPE, "+
		              "ROUND((1-(SUM(GETMISSES)/SUM(GETS))),3)*100 RATIO "+
		       "FROM V$ROWCACHE "+
		       "WHERE GETS>0 "+
		       "UNION ALL "+
		       "SELECT 'InMemorySortRatio' TYPE, "+
		              "(MEMORY_SORT/(MEMORY_SORT+DISK_SORT))*100 RATIO "+
		       "FROM (SELECT VALUE MEMORY_SORT, "+
		                    "LEAD(VALUE) OVER (ORDER BY STATISTIC#) DISK_SORT "+
		             "FROM V$SYSSTAT "+
		             "WHERE NAME IN ('sorts (memory)','sorts (disk)')) "+
		       "WHERE ROWNUM <= 1";
	
	public static String waitEventSql = "SELECT B.EVENT_NAME, "+
				"NVL(A.AVERAGE_WAIT, 0) AVERAGE_WAIT "+
				"FROM V$SYSTEM_EVENT A, (SELECT EVENT_NAME "+
                "FROM DBA_HIST_EVENT_NAME "+
                "WHERE EVENT_NAME IN ('db file scattered read', "+
                                             "'db file sequential read', "+
                                             "'log file sync', "+
                                             "'buffer busy waits', "+
                                             "'log buffer space', "+
                                             "'library cache lock')) B "+
                                             "WHERE A.EVENT(+) = B.EVENT_NAME";	
	
	public static String topSql = "WITH SQL_LIST AS " + "(SELECT B.SQL_ID, "
			+ "B.EXECUTIONS, " + "B.BUFFER_GETS, " + "B.CPU_TIME, "
			+ "B.ELAPSED_TIME "
			+ "FROM (SELECT NVL(SQL_ID, PREV_SQL_ID) SQL_ID "
			+ "FROM V$SESSION " + "WHERE USERNAME IS NOT NULL "
			+ "AND USERNAME NOT IN ('SYSMAN', " + "'DBSNMP')) A, "
			+ "(SELECT SQL_ID , " + "SORTS , " + "EXECUTIONS , "
			+ "PARSE_CALLS , " + "DISK_READS , " + "USER_IO_WAIT_TIME , "
			+ "BUFFER_GETS , " + "ROUND(CPU_TIME/1000000,3) CPU_TIME, "
			+ "ROUND(ELAPSED_TIME/1000000, 3) ELAPSED_TIME, "
			+ "PHYSICAL_READ_REQUESTS , " + "PHYSICAL_READ_BYTES "
			+ "FROM V$SQLAREA " + "WHERE SQL_FULLTEXT NOT LIKE '%V$%' "
			+ "AND SQL_FULLTEXT NOT LIKE '%DBA%' "
			+ "AND SQL_FULLTEXT NOT LIKE '%ALL%' "
			+ "AND SQL_FULLTEXT NOT LIKE '%USER%') B "
			+ "WHERE A.SQL_ID = B.SQL_ID) "
			+ "SELECT 'Buffer Gets Top3' TYPE, " + "NVL(TOP1, 0) TOP1, " + "NVL(TOP2, 0) TOP2, "
			+ "NVL(TOP3, 0) TOP3 " + "FROM (SELECT BUFFER_GETS TOP1, "
			+ "LEAD(BUFFER_GETS,1) OVER (ORDER BY BUFFER_GETS DESC) TOP2, "
			+ "LEAD(BUFFER_GETS,2) OVER (ORDER BY BUFFER_GETS DESC) TOP3 "
			+ "FROM (SELECT SQL_ID, " + "BUFFER_GETS "
			+ "FROM (SELECT SQL_ID, " + "BUFFER_GETS " + "FROM SQL_LIST "
			+ "ORDER BY BUFFER_GETS DESC) " + "WHERE ROWNUM <= 3) " + ") "
			+ "WHERE ROWNUM <=1 " + "UNION ALL "
			+ "SELECT 'CPU Time Top3' TYPE, " + "NVL(TOP1, 0) TOP1, " + "NVL(TOP2, 0) TOP2, " + "NVL(TOP3, 0) TOP3 "
			+ "FROM (SELECT CPU_TIME TOP1, "
			+ "LEAD(CPU_TIME,1) OVER (ORDER BY CPU_TIME DESC) TOP2, "
			+ "LEAD(CPU_TIME,2) OVER (ORDER BY CPU_TIME DESC) TOP3 "
			+ "FROM (SELECT SQL_ID, " + "CPU_TIME " + "FROM (SELECT SQL_ID, "
			+ "CPU_TIME " + "FROM SQL_LIST " + "ORDER BY CPU_TIME DESC) "
			+ "WHERE ROWNUM <= 3) " + ") " + "WHERE ROWNUM <=1 " + "UNION ALL "
			+ "SELECT 'Elapsed Time Top3' TYPE, " + "NVL(TOP1, 0) TOP1, " + "NVL(TOP2, 0) TOP2, "
			+ "NVL(TOP3, 0) TOP3 " + "FROM (SELECT ELAPSED_TIME TOP1, "
			+ "LEAD(ELAPSED_TIME,1) OVER (ORDER BY ELAPSED_TIME DESC) TOP2, "
			+ "LEAD(ELAPSED_TIME,2) OVER (ORDER BY ELAPSED_TIME DESC) TOP3 "
			+ "FROM (SELECT SQL_ID, " + "ELAPSED_TIME "
			+ "FROM (SELECT SQL_ID, " + "ELAPSED_TIME " + "FROM SQL_LIST "
			+ "ORDER BY ELAPSED_TIME DESC) " + "WHERE ROWNUM <= 3) " + ") "
			+ "WHERE ROWNUM <=1 " + "UNION ALL "
			+ "SELECT 'Executions Top3' TYPE, " + "NVL(TOP1, 0) TOP1, " + "NVL(TOP2, 0) TOP2, " + "NVL(TOP3, 0) TOP3 "
			+ "FROM (SELECT EXECUTIONS TOP1, "
			+ "LEAD(EXECUTIONS,1) OVER (ORDER BY EXECUTIONS DESC) TOP2, "
			+ "LEAD(EXECUTIONS,2) OVER (ORDER BY EXECUTIONS DESC) TOP3 "
			+ "FROM (SELECT SQL_ID, " + "EXECUTIONS " + "FROM (SELECT SQL_ID, "
			+ "EXECUTIONS " + "FROM SQL_LIST " + "ORDER BY EXECUTIONS DESC) "
			+ "WHERE ROWNUM <= 3) " + ") " + "WHERE ROWNUM <=1 ORDER BY TYPE";

	public static String onlineUsersSql = "SELECT COUNT(*) CNT "+
                                          "FROM V$SESSION "+
                                          "WHERE USERNAME IS NOT NULL "+
                                          "AND USERNAME NOT IN ('SYSMAN', "+
                                                               "'DBSNMP')";
	
	public static String jdbcConnectSql = "SELECT PADDR "+
                                          "FROM V$SESSION "+
                                          "WHERE MODULE LIKE 'JDBC%'";
}
