package dto;

/**
 * 
* 1. 패키지명 : main.dto
* 2. 타입명 : TopSqlDto.java
* 3. 작성일 : 2015. 8. 14. 오전 3:03:16
* 4. 작성자 : 길용현
* 5. 설명 : TOP3 SQL 관련 Dto 클래스
 */
public class TopSqlDto {
	private String type;
	private String sqlId1;
	private double top1;
	private String sqlId2;
	private double top2;
	private String sqlId3;
	private double top3;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSqlId1(){
		return sqlId1;
	}
	public void setSqlId1(String sqlId1){
		this.sqlId1 = sqlId1;
	}
	public double getTop1() {
		return top1;
	}
	public void setTop1(double top1) {
		this.top1 = top1;
	}
	public String getSqlId2(){
		return sqlId2;
	}
	public void setSqlId2(String sqlId2){
		this.sqlId2 = sqlId2;
	}
	public double getTop2() {
		return top2;
	}
	public void setTop2(double top2) {
		this.top2 = top2;
	}
	public String getSqlId3(){
		return sqlId3;
	}
	public void setSqlId3(String sqlId3){
		this.sqlId3 = sqlId3;
	}
	public double getTop3() {
		return top3;
	}
	public void setTop3(double top3) {
		this.top3 = top3;
	}
	@Override
	public String toString() {
		return "TopSqlDto [type=" + type + ", sqlId1=" + sqlId1 + ", top1="
				+ top1 + ", sqlId2=" + sqlId2 + ", top2=" + top2 + ", sqlId3="
				+ sqlId3 + ", top3=" + top3 + "]";
	}
}
