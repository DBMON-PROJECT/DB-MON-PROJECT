package login.dto;

/**
 * 
* 1. 패키지명 : login.dto
* 2. 타입명 : LoginInfo.java
* 3. 작성일 : 2015. 8. 11. 오전 10:49:25
* 4. 작성자 : 길용현
* 5. 설명 : 로그인 관련 정보를 가지는 Bean 클래스
 */
public class LoginDto {
	private String userName;
	private String host;
	private String port;
	private String userContent;
	private String serviceName;
	
	
	public LoginDto(String userName, String host, String port,
			String userContent,String serviceName) {
		this.userName = userName;
		this.host = host;
		this.port = port;
		this.userContent = userContent;
		this.serviceName = serviceName;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUserContent() {
		return userContent;
	}
	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}
	
	@Override
	public String toString() {
		return "LogingUserInfo [userName=" + userName + ", host=" + host
				+ ", port=" + port + ", userContent=" + userContent + "]";
	}
}
