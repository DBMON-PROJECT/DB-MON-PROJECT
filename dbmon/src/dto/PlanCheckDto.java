package dto;

public class PlanCheckDto {
	private String planTableOutPut;

	@Override
	public String toString() {
		return "PlanCheckDto [planTableOutPut=" + planTableOutPut + "]";
	}

	public String getPlanTableOutPut() {
		return planTableOutPut;
	}

	public void setPlanTableOutPut(String planTableOutPut) {
		this.planTableOutPut = planTableOutPut;
	}
}
