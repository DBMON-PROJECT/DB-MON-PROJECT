package dto;

import java.util.Date;

/**
 * 
* 1. 패키지명 : dto
* 2. 타입명 : SessionCheckDto.java
* 3. 작성일 : 2015. 8. 21. 오후 2:22:18
* 4. 작성자 : 정석준
* 5. 설명 : session 관련 Dto 클래스
 */
public class SessionCheckDto {
	private String sqlId;
	private String osUser;
	private String logonTime;
	private String paddr;
	private String username;
	private String type;
	private String command;
	private String status;
	private String program;
	
	public String getSqlId() {
		return sqlId;
	}
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	public String getOsUser() {
		return osUser;
	}
	public void setOsUser(String osUser) {
		this.osUser = osUser;
	}
	public String getLogonTime() {
		return logonTime;
	}
	public void setLogonTime(String logonTime) {
		this.logonTime = logonTime;
	}
	public String getPaddr() {
		return paddr;
	}
	public void setPaddr(String paddr) {
		this.paddr = paddr;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	
	@Override
	public String toString() {
		return "SessionCheckDto [sqlId=" + sqlId + ", osUser=" + osUser
				+ ", logonTime=" + logonTime + ", paddr=" + paddr
				+ ", username=" + username + ", type=" + type + ", command="
				+ command + ", status=" + status + ", program=" + program + "]";
	}
	
}
