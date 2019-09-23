package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import persistence.entities.TagEntity;


public interface TagRepository extends JpaRepository<TagEntity, Integer>{
	
	TagEntity findOneById(int id);
	TagEntity findOneByTagName(String tagName);

}
