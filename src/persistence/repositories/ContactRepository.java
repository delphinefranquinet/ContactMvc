package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import persistence.entities.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Integer>{
	
	//List<ContactDto> findAllContactDtoWithMapping();
	
	ContactEntity findOneByFirstnameAndLastname(String firstname, String lastname);
	ContactEntity findOneByFirstnameIgnoreCaseAndLastnameIgnoreCase (String firstname, String lastname);
	ContactEntity findOneByEmail (String email);
	ContactEntity findOneById(int id);

}
