package dto;

import java.sql.Date;

public class BindCheckDto {
	private String sanpId;
	private String sqlId;
	private String name;
	private String position;
	private String datatypeString;
	private Date lastCaptured;
	
	public BindCheckDto(){
		
	}
	
	public BindCheckDto(String sanpId, String sqlId, String name,
			String position, String datatypeString) {
		this.sanpId = sanpId;
		this.sqlId = sqlId;
		this.name = name;
		this.position = position;
		this.datatypeString = datatypeString;
	}

	@Override
	public String toString() {
		return "BindCheckDto [sanpId=" + sanpId + ", sqlId=" + sqlId
				+ ", name=" + name + ", position=" + position
				+ ", datatypeString=" + datatypeString + ", lastCaptured="
				+ lastCaptured + "]";
	}
	
	public String getSanpId() {
		return sanpId;
	}
	public void setSanpId(String sanpId) {
		this.sanpId = sanpId;
	}
	public String getSqlId() {
		return sqlId;
	}
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDatatypeString() {
		return datatypeString;
	}
	public void setDatatypeString(String datatypeString) {
		this.datatypeString = datatypeString;
	}
	public Date getLastCaptured() {
		return lastCaptured;
	}
	public void setLastCaptured(Date lastCaptured) {
		this.lastCaptured = lastCaptured;
	}
}
