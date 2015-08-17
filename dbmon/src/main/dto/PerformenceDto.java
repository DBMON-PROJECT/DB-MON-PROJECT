package main.dto;

/**
 * 
* 1. 패키지명 : main.dto
* 2. 타입명 : PerformenceDto.java
* 3. 작성일 : 2015. 8. 13. 오후 9:40:20
* 4. 작성자 : 길용현
* 5. 설명 : performence 관련 Dto 클래스
 */
public class PerformenceDto {
	private String type;
	private double value;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "PerformenceDto [type=" + type + ", value=" + value + "]";
	}
}
