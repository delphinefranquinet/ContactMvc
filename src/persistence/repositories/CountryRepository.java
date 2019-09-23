package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import persistence.entities.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer>{
	
	CountryEntity findOneByAbreviation (String abreviation);
	CountryEntity findOneByName (String name);
	CountryEntity findOneById(int id);

}
