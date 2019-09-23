package dto;

public class TagDto {
	
	private int id;
	private String tagName;
	
	public TagDto() {}

	public TagDto(int id, String tagName) {
		super();
		this.id = id;
		this.tagName = tagName;
	}

	public int getId() {
		return id;
	}

	public String getTagName() {
		return tagName;
	}

	@Override
	public String toString() {
		return "TagDto [id=" + id + ", tagName=" + tagName + "]";
	}
	
	

}
