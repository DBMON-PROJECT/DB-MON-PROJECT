package dto;



public class TablespaceDto {
	private String tablespace;
	private String totalMb;
	private double usedMb;
	private double freeMb;
	private double usedPer;
	private String freePer;
	private double maxMb;
	private double minMb;
	private double avgMb;
	private Integer distributePer;
	private String distState;
	private Integer fileCnt;
	@Override
	public String toString() {
		return "SesionDto [tablespace=" + tablespace + ", totalMb=" + totalMb
				+ ", usedMb=" + usedMb + ", freeMb=" + freeMb + ", usedPer="
				+ usedPer + ", freePer=" + freePer + ", maxMb=" + maxMb
				+ ", minMb=" + minMb + ", avgMb=" + avgMb + ", distributePer="
				+ distributePer + ", distState=" + distState + ", fileCnt="
				+ fileCnt + "]";
	}
	
	public String getTablespace() {
		return tablespace;
	}
	public void setTablespace(String tablespace) {
		this.tablespace = tablespace;
	}
	
	public String getTotalMb() {
		return totalMb;
	}
	public void setTotalMb(String totalMb) {
		this.totalMb = totalMb;
	}
	
	public double getUsedMb() {
		return usedMb;
	}
	public void setUsedMb(double usedMb) {
		this.usedMb = usedMb;
	}
	
	
	public double getFreeMb() {
		return freeMb;
	}
	public void setFreeMb(double freeMb) {
		this.freeMb = freeMb;
	}
	
	
	public double getUsedPer() {
		return usedPer;
	}
	public void setUsedPer(double usedPer) {
		this.usedPer = usedPer;
	}
	
	
	public String getFreePer() {
		return freePer;
	}
	public void setFreePer(String freePer) {
		this.freePer = freePer;
	}
	
	
	public double getMaxMb() {
		return maxMb;
	}
	public void setMaxMb(double maxMb) {
		this.maxMb = maxMb;
	}
	
	
	public double getMinMb() {
		return minMb;
	}
	public void setMinMb(double minMb) {
		this.minMb = minMb;
	}
	
	
	public double getAvgMb() {
		return avgMb;
	}
	public void setAvgMb(double avgMb) {
		this.avgMb = avgMb;
	}
	
	
	public Integer getDistributePer() {
		return distributePer;
	}
	public void setDistributePer(Integer distributePer) {
		this.distributePer = distributePer;
	}
	
	
	public String getDistState() {
		return distState;
	}
	public void setDistState(String distState) {
		this.distState = distState;
	}
	
	public Integer getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(Integer fileCnt) {
		this.fileCnt = fileCnt;
	}
	
	
	
	
}