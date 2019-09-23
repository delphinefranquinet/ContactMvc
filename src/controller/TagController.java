package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import dto.ResponseDto;
import dto.ResponseDtoStatus;
import dto.TagDto;
import persistence.exceptions.DuplicatedTagException;
import persistence.exceptions.InvalidCreateTagtParametersException;
import persistence.exceptions.TagNotFoundException;
import persitence.ApplicationRepository;

@Controller
@RequestMapping(value = "tag")
public class TagController {

	@Autowired
	ApplicationRepository repository;

	@GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findAll() {
		ResponseDto dto = null;
		try {
			List<TagDto> tags = repository.findAllTagsDtoWithMapping();
			if (tags == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "tags not found");

			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(tags);
			}
		} catch (Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		return ResponseEntity.ok(dto);

	}

	@GetMapping(value = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findOne(@PathVariable("id") int id) {

		ResponseDto dto = null;
		try {
			TagDto tag = repository.findOneTagById(id);
			if (tag == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "tag not found");
			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(tag);
			}
		} catch (Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}

		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "value/{tagName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findOneByTagName(@PathVariable("tagName") String tagName) {

		ResponseDto dto = null;
		try {
			TagDto tag = repository.findOneTagByTagName(tagName);
			if (tag == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "tag not found");
			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(tag);
			}
		} catch (Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}

		return ResponseEntity.ok(dto);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> createTag(@RequestBody TagDto tagDto) {
		ResponseDto dto = null;
		try {
			repository.createTag(tagDto);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "tag created");
		} catch (InvalidCreateTagtParametersException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "invalid create parameters");
		} catch (DuplicatedTagException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "duplicated tag");
		}
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value="{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deleteTag(
			@PathVariable("id") int	id){

		ResponseDto dto = null;
		
		try {
			repository.deleteTag(id);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "tag deleted");
		} catch(TagNotFoundException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "tag not found");
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
		}
		
		return ResponseEntity.ok(dto);
	}
}
