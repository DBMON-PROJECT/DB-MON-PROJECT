package sql;

/**
 * 
* 1. 패키지명 : sql
* 2. 타입명 : SessionSQL.java
* 3. 작성일 : 2015. 8. 21. 오후 2:25:06
* 4. 작성자 : 정석준
* 5. 설명 : Session Monitoring 관련 SQL 목록
 */
public class SessionSQL {
	public static String sessionCheck = "SELECT NVL(SQL_ID, PREV_SQL_ID) SQL_ID, "+
		       "OSUSER, "+
		       "LOGON_TIME, "+
		       "PADDR, "+
		       "USERNAME, "+
		       "TYPE, "+
		       "NVL(DECODE(COMMAND, 1, 'CREATE TABLE', "+
				  "2, 'INSERT', "+
				  "3, 'SELECT', "+
				  "4, 'CREATE CLUSTER', "+
				  "5, 'ALTER CLUSTER', "+
				  "6, 'UPDATE', "+
				  "7, 'DELETE', "+
				  "8, 'DROP CLUSTER', "+
				  "9, 'CREATE INDEX', "+
				  "10, 'DROP INDEX', "+
				  "11, 'ALTER INDEX', "+
				  "12, 'DROP TABLE', "+
				  "13, 'CREATE SEQUENCE', "+
				  "14, 'ALTER SEQUENCE', "+
				  "15, 'ALTER TABLE', "+
				  "16, 'DROP SEQUENCE', "+
				  "17, 'GRANT OBJECT', "+
				  "18, 'REVOKE OBJECT', "+
				  "19, 'CREATE SYNONYM', "+
				  "20, 'DROP SYNONYM', "+
				  "21, 'CREATE VIEW', "+
				  "22, 'DROP VIEW', "+
				  "23, 'VALIDATE INDEX', "+
				  "24, 'CREATE PROCEDURE', "+
				  "25, 'ALTER PROCEDURE', "+
				  "26, 'LOCK', "+
				  "27, 'NO-OP', "+
				  "28, 'RENAME', "+
				  "29, 'COMMENT', "+
				  "30, 'AUDIT OBJECT', "+
				  "31, 'NOAUDIT OBJECT', "+
				  "32, 'CREATE DATABASE LINK', "+
				  "33, 'DROP DATABASE LINK', "+
				  "34, 'CREATE DATABASE', "+
				  "35, 'ALTER DATABASE', "+
				  "36, 'CREATE ROLLBACK SEG', "+
				  "37, 'ALTER ROLLBACK SEG', "+
				  "38, 'DROP ROLLBACK SEG', "+
				  "39, 'CREATE TABLESPACE', "+
				  "40, 'ALTER TABLESPACE', "+
				  "41, 'DROP TABLESPACE', "+
				  "42, 'ALTER SESSION', "+
				  "43, 'ALTER USER', "+
				  "44, 'COMMIT', "+
				  "45, 'ROLLBACK', "+
				  "46, 'SAVEPOINT', "+
				  "47, 'PL/SQL EXECUTE', "+
				  "48, 'SET TRANSACTION', "+
				  "49, 'ALTER SYSTEM', "+
				  "50, 'EXPLAIN', "+
				  "51, 'CREATE USER', "+
				  "52, 'CREATE ROLE', "+
				  "53, 'DROP USER', "+
				  "54, 'DROP ROLE', "+
				  "55, 'SET ROLE', "+
				  "56, 'CREATE SCHEMA', "+
				  "57, 'CREATE CONTROL FILE', "+
				  "59, 'CREATE TRIGGER', "+
				  "60, 'ALTER TRIGGER', "+
				  "61, 'DROP TRIGGER', "+
				  "62, 'ANALYZE TABLE', "+
				  "63, 'ANALYZE INDEX', "+
				  "64, 'ANALYZE CLUSTER', "+
				  "65, 'CREATE PROFILE', "+
				  "66, 'DROP PROFILE', "+
				  "67, 'ALTER PROFILE', "+
				  "68, 'DROP PROCEDURE', "+
				  "70, 'ALTER RESOURCE COST', "+
				  "71, 'CREATE MATERIALIZED VIEW LOG', "+
				  "72, 'ALTER MATERIALIZED VIEW LOG', "+
				  "73, 'DROP MATERIALIZED VIEW LOG', "+
				  "74, 'CREATE MATERIALIZED VIEW', "+
				  "75, 'ALTER MATERIALIZED VIEW', "+
				  "76, 'DROP MATERIALIZED VIEW', "+
				  "77, 'CREATE TYPE', "+
				  "78, 'DROP TYPE', "+
				  "79, 'ALTER ROLE', "+
				  "80, 'ALTER TYPE', "+
				  "81, 'CREATE TYPE BODY', "+
				  "82, 'ALTER TYPE BODY', "+
				  "83, 'DROP TYPE BODY', "+
				  "84, 'DROP LIBRARY', "+
				  "85, 'TRUNCATE TABLE', "+
				  "86, 'TRUNCATE CLUSTER', "+
				  "91, 'CREATE FUNCTION', "+
				  "92, 'ALTER FUNCTION', "+
				  "93, 'DROP FUNCTION', "+
				  "94, 'CREATE PACKAGE', "+
				  "95, 'ALTER PACKAGE', "+
				  "96, 'DROP PACKAGE', "+
				  "97, 'CREATE PACKAGE BODY', "+
				  "98, 'ALTER PACKAGE BODY', "+
				  "99, 'DROP PACKAGE BODY', "+
				  "100, 'LOGON', "+	
				  "101, 'LOGOFF', "+
				  "102, 'LOGOFF BY CLEANUP', "+
				  "103, 'SESSION REC', "+
				  "104, 'SYSTEM AUDIT', "+
				  "105, 'SYSTEM NOAUDIT', "+
				  "106, 'AUDIT DEFAULT', "+
				  "107, 'NOAUDIT DEFAULT', "+
				  "108, 'SYSTEM GRANT', "+
				  "109, 'SYSTEM REVOKE', "+
				  "110, 'CREATE PUBLIC SYNONYM', "+
				  "111, 'DROP PUBLIC SYNONYM', "+
				  "112, 'CREATE PUBLIC DATABASE LINK', "+
				  "113, 'DROP PUBLIC DATABASE LINK', "+
				  "114, 'GRANT ROLE', "+
				  "115, 'REVOKE ROLE', "+
				  "116, 'EXECUTE PROCEDURE', "+
				  "117, 'USER COMMENT', "+
				  "118, 'ENABLE TRIGGER', "+
				  "119, 'DISABLE TRIGGER', "+
				  "120, 'ENABLE ALL TRIGGERS', "+
				  "121, 'DISABLE ALL TRIGGERS', "+
				  "122, 'NETWORK ERROR', "+
				  "123, 'EXECUTE TYPE', "+
				  "157, 'CREATE DIRECTORY', "+
				  "158, 'DROP DIRECTORY', "+
				  "159, 'CREATE LIBRARY', "+
				  "160, 'CREATE JAVA', "+
				  "161, 'ALTER JAVA', "+
				  "162, 'DROP JAVA', "+
				  "163, 'CREATE OPERATOR', "+
				  "164, 'CREATE INDEXTYPE', "+
				  "165, 'DROP INDEXTYPE', "+
				  "167, 'DROP OPERATOR', "+
				  "168, 'ASSOCIATE STATISTICS', "+
				  "169, 'DISASSOCIATE STATISTICS', "+
				  "170, 'CALL METHOD', "+
				  "171, 'CREATE SUMMARY', "+
				  "172, 'ALTER SUMMARY', "+
				  "173, 'DROP SUMMARY', "+
				  "174, 'CREATE DIMENSION', "+
				  "175, 'ALTER DIMENSION', "+
				  "176, 'DROP DIMENSION', "+
				  "177, 'CREATE CONTEXT', "+
				  "178, 'DROP CONTEXT', "+
				  "179, 'ALTER OUTLINE', "+
				  "180, 'CREATE OUTLINE', "+
				  "181, 'DROP OUTLINE', "+
				  "182, 'UPDATE INDEXES', "+
				  "183, 'ALTER OPERATOR'), 'UNKNOWN') COMMAND, "+
				  "STATUS, "+
				  "PROGRAM "+
		"FROM V$SESSION "+
		"WHERE USERNAME IS NOT NULL "+
		"AND USERNAME NOT IN ('SYSMAN', "+
		                     "'DBSNMP') ";

	
	public static String bindCheck = "SELECT A.SNAP_ID  \"sanpId\" , "
			+ "A.SQL_ID \"sqlId\" , "                   
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
