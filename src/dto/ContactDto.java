package dto;

import java.util.List;

public class ContactDto {

	private String firstname;
	private String lastname;
	private String email;
	private int country;
	private List<TagDto> tags;
	
	public ContactDto(){}

	public ContactDto(String firstname, String lastname, String email, int country, List<TagDto> tags) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.country = country;
		this.tags = tags;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public List<TagDto> getTags() {
		return tags;
	}

	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "ContactDto [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", country="
				+ country + ", tags=" + tags + "]";
	}

	
	

	
	
	
}
