package dto;

/**
 * 
* 1. 패키지명 : login.dto
* 2. 타입명 : LoginLogInfo.java
* 3. 작성일 : 2015. 8. 11. 오후 2:41:48
* 4. 작성자 : 길용현
* 5. 설명 : Login Log 관련 정보를 가지는 Bean 클래스
 */
public class LoginLogDto {
	private String tnsName;
	private String userName;
	private String userPwd;
	private String connectTime;
	
	public String getTnsName() {
		return tnsName;
	}
	public void setTnsName(String tnsName) {
		this.tnsName = tnsName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(String connectTime) {
		this.connectTime = connectTime;
	}
	
	@Override
	public String toString() {
		return "LoginLogInfo [tnsName=" + tnsName + ", userName=" + userName
				+ ", userPwd=" + userPwd + ", connectTime=" + connectTime + "]";
	}
}
