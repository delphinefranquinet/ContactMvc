package persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name="Contact")
@Table(name="contacts")
public class ContactEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id") 
	private Integer id;
	
	@Column(name="prenom")
	private String firstname;
	
	@Column(name="nom")
	private String lastname;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="pays")
	private CountryEntity country;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="contacts_tags",joinColumns=@JoinColumn(name="contact"),
            inverseJoinColumns=@JoinColumn(name="tag"))
	private List<TagEntity> tags;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public List<TagEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}
	

	
	

}
