package dto;

public class TextCheckDto {
	private String sqlFullText;

	@Override
	public String toString() {
		return "TextCheckDto [sqlFullText=" + sqlFullText + "]";
	}

	public String getSqlFullText() {
		return sqlFullText;
	}

	public void setSqlFullText(String sqlFullText) {
		this.sqlFullText = sqlFullText;
	}
}
