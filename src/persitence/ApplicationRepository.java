package persitence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.ContactDto;
import dto.CountryDto;
import dto.TagDto;
import persistence.entities.ContactEntity;
import persistence.entities.CountryEntity;
import persistence.entities.TagEntity;
import persistence.exceptions.ContactNotFoundException;
import persistence.exceptions.CountryNotFoundException;
import persistence.exceptions.DuplicatedAbreviationCountryException;
import persistence.exceptions.DuplicatedContactException;
import persistence.exceptions.DuplicatedEmailException;
import persistence.exceptions.DuplicatedNameCountryException;
import persistence.exceptions.DuplicatedTagException;
import persistence.exceptions.InvalidCreateContactParametersException;
import persistence.exceptions.InvalidCreateCountrytParametersException;
import persistence.exceptions.InvalidCreateTagtParametersException;
import persistence.exceptions.TagNotFoundException;
import persistence.parameters.ContactParameter;
import persistence.repositories.ContactRepository;
import persistence.repositories.CountryRepository;
import persistence.repositories.TagRepository;

@Component
public class ApplicationRepository {

	@Autowired ContactRepository contactRepository;
	@Autowired CountryRepository countryRepository;
	@Autowired TagRepository tagRepository;
	
	public List<ContactDto> findAllContactDtoWithMapping() {
		List<ContactEntity> contacts = contactRepository.findAll();
		return createListContactDto(contacts);
		
	}
	
	private List<ContactDto> createListContactDto(List<ContactEntity> entities){
		List<ContactDto> contacts = new ArrayList<ContactDto>(); 
		
		for (int i = 0; i < entities.size(); i++) {
			ContactEntity contactEntity = entities.get(i);
			ContactDto contact = createContactDto(contactEntity);
			contacts.add(contact);
		}
				
		
		return contacts;
	}
	
	private ContactDto createContactDto(ContactEntity entity) {
		ContactDto contact = null;
		List<TagDto> tags = createListTagDto(entity.getTags());
		if (entity.getCountry() != null) {
		 contact = new ContactDto(
				entity.getFirstname(),
				entity.getLastname(),
				entity.getEmail(),
				entity.getCountry().getId(),
				tags
				);
		}
		 return contact;
	}
	
	public List<CountryDto> findAllCountryDtoWithMapping() {
		List<CountryEntity> countries = countryRepository.findAll();
		return createListCountryDto(countries);
		
	}
	
	private List<CountryDto> createListCountryDto(List<CountryEntity> entities){
		List<CountryDto> countries = new ArrayList<CountryDto>(); 
		
		for (int i = 0; i < entities.size(); i++) {
			CountryEntity countryEntity = entities.get(i);
			CountryDto country = createCountryDto(countryEntity);
			countries.add(country);
		}
				
		
		return countries;
	}
	
	private CountryDto createCountryDto(CountryEntity entity) {
		
		return new CountryDto(
				entity.getId(),
				entity.getAbreviation(),
				entity.getName());
		}

	public List<TagDto> findAllTagsDtoWithMapping() {
		List<TagEntity> tags = tagRepository.findAll();
		return createListTagDto(tags);
		
	}
	
	private List<TagDto> createListTagDto(List<TagEntity> entities){
		List<TagDto> tags = new ArrayList<TagDto>(); 
		
		for (int i = 0; i < entities.size(); i++) {
			TagEntity tagEntity = entities.get(i);
			TagDto tag = createTagDto(tagEntity);
			tags.add(tag);
		}
		return tags;
	}
	
	private TagDto createTagDto(TagEntity entity) {
		
		return new TagDto(
				entity.getId(),
				entity.getTagName());
		}

	public ContactDto findOneContactByFirstnameAndLastname(String firstname, String lastname) {
		ContactEntity contact = contactRepository.findOneByFirstnameAndLastname(firstname, lastname);
		return createContactDto(contact);
	}
	
	public CountryDto findOneCountryByAbreviation(String abreviation) {
		CountryEntity country = countryRepository.findOneByAbreviation (abreviation);
		return createCountryDto(country);
	}
	
	public CountryDto findOneCountryByName(String name) {
		CountryEntity country = countryRepository.findOneByName (name);
		return createCountryDto(country);
	}
	
	public TagDto findOneTagById(int id) {
		TagEntity tag = tagRepository.findOneById (id);
		return createTagDto(tag);
	}
	
	public TagDto findOneTagByTagName(String tagName) {
		TagEntity tag = tagRepository.findOneByTagName (tagName);
		return createTagDto(tag);
	}

	public void createContact(ContactParameter dto) {
		if (!validateCreateContactParameters(dto)) {
			throw new InvalidCreateContactParametersException();
		}
		if (contactRepository.findOneByFirstnameIgnoreCaseAndLastnameIgnoreCase(dto.getFirstname(), dto.getLastname()) != null){
			throw new DuplicatedContactException();
		}
		if (contactRepository.findOneByEmail(dto.getEmail()) != null) {
			throw new DuplicatedEmailException();
		}
		CountryEntity country = countryRepository.findOneByName(dto.getCountry());
		List<TagEntity> tags = new ArrayList<TagEntity>();
		tags = createListTagEntity(dto.getTags());
		
		
		ContactEntity contact = new ContactEntity();
		contact.setFirstname(dto.getFirstname());
		contact.setLastname(dto.getLastname());
		contact.setEmail(dto.getEmail());
		contact.setCountry(country);
		contact.setTags(tags);
		contactRepository.save(contact);

	}
	
	private boolean validateCreateContactParameters(ContactParameter dto) {
		String firstname = dto.getFirstname();
		String lastname = dto.getLastname();
		String email = dto.getEmail();
		String country = dto.getCountry();
		List<TagEntity> tags = null;
		return firstname != null && !firstname.isBlank()
				&& lastname != null && !lastname.isBlank()
				&& email != null && !email.isBlank()
				&& country != null && !country.isBlank()
				&& tags != null && !tags.isEmpty();
				
	}

	private TagEntity createTagEntity(int id) {

		TagEntity tag = tagRepository.findOneById(id);
		
		return tag;
	}

	private List<TagEntity> createListTagEntity(List<Integer> entities){
		List<TagEntity> tags = new ArrayList<TagEntity>(); 
		
		for (int i = 0; i < entities.size(); i++) {
			int id = entities.get(i);
			TagEntity tag = createTagEntity(id);
			tags.add(tag);
		}
		return tags;
	}

	public void createCountry (CountryDto dto) {
		
		if(!validateCreateCountryParameters(dto)) {
			throw new InvalidCreateCountrytParametersException();
		}
		if (countryRepository.findOneByAbreviation(dto.getAbreviation()) != null) {
			throw new DuplicatedAbreviationCountryException();
		}
		if(countryRepository.findOneByName(dto.getName()) != null) {
			throw new DuplicatedNameCountryException();
		}
		CountryEntity country = new CountryEntity();
		country.setAbreviation(dto.getAbreviation());
		country.setName(dto.getName());
		countryRepository.save(country);
		
		
	}

	private boolean validateCreateCountryParameters(CountryDto dto) {
		String abreviation = dto.getAbreviation();
		String name = dto.getName();	
		return abreviation != null && !abreviation.isBlank()
				&& name != null && !name.isBlank();
	}

	public void createTag (TagDto dto) {
		
		if(!validateCreateTagParameters(dto)) {
			throw new InvalidCreateTagtParametersException();
		}
		if(tagRepository.findOneByTagName(dto.getTagName()) != null){
			throw new DuplicatedTagException();
		}
		TagEntity tag = new TagEntity();
		tag.setTagName(dto.getTagName());
		tagRepository.save(tag);
	}

	private boolean validateCreateTagParameters(TagDto dto) {
		
		String tagName = dto.getTagName();	
		return tagName != null && !tagName.isBlank();
	}

	public void deleteContact(int id) {
		ContactEntity contact = contactRepository.findOneById(id);
		if(contact == null) {
			throw new ContactNotFoundException();
		}
		contactRepository.delete(contact);
	}
	
	public void deleteCountryt(int id) {
		CountryEntity country = countryRepository.findOneById(id);
		if(country == null) {
			throw new CountryNotFoundException();
		}
		countryRepository.delete(country);
	}
	
	public void deleteTag(int id) {
		TagEntity tag = tagRepository.findOneById(id);
		if(tag == null) {
			throw new TagNotFoundException();
		}
		tagRepository.delete(tag);
	}

}
