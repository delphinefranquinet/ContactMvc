package dto;

public class CountryDto {
	
	private int id;
	private String abreviation;
	private String name;
	
	public CountryDto() {}

	public CountryDto(int id, String abreviation, String name) {
		super();
		this.id = id;
		this.abreviation = abreviation;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "CountryDto [id=" + id + ", abreviation=" + abreviation + ", name=" + name + "]";
	}
	
	

}
