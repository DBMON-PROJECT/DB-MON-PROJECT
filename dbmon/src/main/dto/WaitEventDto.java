package main.dto;
/**
 * 
* 1. 패키지명 : main.dto
* 2. 타입명 : WaitEventDto.java
* 3. 작성일 : 2015. 8. 13. 오후 9:40:36
* 4. 작성자 : 길용현
* 5. 설명 : Wait Event 관련 Dto 클래스
 */
public class WaitEventDto {
	private String eventName;
	private double averageWait;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public double getAverageWait() {
		return averageWait;
	}
	public void setAverageWait(double avgWait) {
		this.averageWait = avgWait;
	}
	
	@Override
	public String toString() {
		return "WaitEventDto [eventName=" + eventName + ", avgWait=" + averageWait
				+ "]";
	}
}
