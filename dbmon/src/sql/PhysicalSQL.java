package sql;

public class PhysicalSQL {
	public static String tablespaceSql = " SELECT B.TABLESPACE     \"tablespace\"   "+
		 	   " ,B.TOTALMB        \"totalMb\"     "+
		 	   " ,B.USEDMB         \"usedMb\"     "+
		 	   " ,B.FREEMB         \"freeMb\"     "+
		 	   " ,B.USED_PER       \"usedPer\"     "+
		 	   " ,B.FREE_PER       \"freePer\"    "+
		 	   " ,B.MAX_DBF_MB     \"maxMb\"     "+
		 	   " ,B.MIN_DBF_MB     \"minMb\"     "+
		 	   " ,B.AVG_DBF_MB     \"avgMb\"     "+
		 	   " ,B.DISTRIBUTION   \"distributePer\"    "+
		 	   " ,CASE WHEN B.DISTRIBUTION > 90 THEN 'DANGER'  "+
		 	   " WHEN B.DISTRIBUTION > 70 THEN 'WARNING'  "+
		 	   " WHEN B.DISTRIBUTION > 50 THEN 'PURPOSE'  "+
		 	   " WHEN B.DISTRIBUTION = 0  THEN 'PERFECT' "+
		 	   " END \"distState\"    "+
		 	   " ,B.FILE_CNT \"fileCnt\""+
		 " FROM (SELECT   SUBSTR(A.TABLESPACE_NAME,1,30) TABLESPACE, "+
		 " ROUND(SUM(A.TOTAL1)/1024/1024,1) TOTALMB, "+
		 " ROUND(SUM(A.TOTAL1)/1024/1024,1)-ROUND(SUM(A.SUM1)/1024/1024,1) USEDMB, "+
		 " ROUND(SUM(A.SUM1)/1024/1024,1) FREEMB, "+
		 " ROUND((ROUND(SUM(A.TOTAL1)/1024/1024,1)-ROUND(SUM(A.SUM1)/1024/1024,1))/ROUND(SUM(A.TOTAL1)/1024/1024,1)*100,2) USED_PER, "+
		 " ROUND(ROUND(SUM(A.SUM1)/1024/1024,1)/ROUND(SUM(A.TOTAL1)/1024/1024,1)*100,2)  FREE_PER, "+
		 " ROUND(SUM(A.MAXB)/1024/1024,3) MAX_DBF_MB, "+
		 " ROUND(SUM(A.MINB)/1024/1024,3) MIN_DBF_MB, "+
		 " ROUND(SUM(A.AVGB)/1024/1024,3) AVG_DBF_MB, "+
		 " (ROUND((ROUND(SUM(A.AVGB)/1024/1024,3)-ROUND(SUM(A.MINB)/1024/1024,3))/ROUND(SUM(A.AVGB)/1024/1024,3)*100,2) "+
		 " +ROUND((ROUND(SUM(A.MAXB)/1024/1024,2)-ROUND(SUM(A.AVGB)/1024/1024,3))/ROUND(SUM(A.MAXB)/1024/1024,2)*100,2))/2 DISTRIBUTION, "+
		 " MAX(A.DFCNT) FILE_CNT   "+
		 " FROM     (SELECT   TABLESPACE_NAME      "+
		 "         ,0                    TOTAL1 "+
		 "         ,SUM(BYTES)           SUM1   "+
		 "         ,0                    MAXB "+
		 "         ,0                    MINB "+
		 "         ,0                    AVGB "+
		 "         ,0                    DFCNT "+
		 " FROM     DBA_FREE_SPACE        "+
		 " GROUP BY TABLESPACE_NAME "+
		 " UNION "+
		 " SELECT   TABLESPACE_NAME "+
		 "         ,SUM(BYTES)       "+
		 "         ,0 "+
		 "         ,MAX(BYTES)       "+
		 "         ,MIN(BYTES)       "+
		 "         ,AVG(BYTES)       "+
		 "         ,COUNT(TABLESPACE_NAME)  "+
		 " FROM     DBA_DATA_FILES    "+
		 " GROUP BY TABLESPACE_NAME) A "+
		 " GROUP BY A.TABLESPACE_NAME "+
		 " ORDER BY TABLESPACE) B ";
}
