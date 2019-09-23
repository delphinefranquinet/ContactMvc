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


import dto.ContactDto;
import dto.ResponseDto;
import dto.ResponseDtoStatus;
import persistence.exceptions.ContactNotFoundException;
import persistence.exceptions.DuplicatedContactException;
import persistence.exceptions.DuplicatedEmailException;
import persistence.exceptions.InvalidCreateContactParametersException;
import persistence.parameters.ContactParameter;
import persitence.ApplicationRepository;

@Controller
@RequestMapping(value="contact")
public class ContactController {
	
	@Autowired ApplicationRepository repository;
	
	@GetMapping(value="all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findAll(){
		ResponseDto dto = null;
		try {
			List<ContactDto> contacts = repository.findAllContactDtoWithMapping();
			if(contacts == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "customers not found");
				
			}else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(contacts);
			}
		}catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		return ResponseEntity.ok(dto);
		
	}
	
	@GetMapping(value="{firstname}/{lastname}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findOne(
			@PathVariable("firstname") String firstname, 
			@PathVariable("lastname") String lastname) {
		
		ResponseDto dto = null;
		try {
			ContactDto contact = repository.findOneContactByFirstnameAndLastname(firstname, lastname);
			if (contact == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "contact not found");
			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(contact);
			}
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto>  createCustomer(@RequestBody ContactParameter customerDto) {
		ResponseDto dto = null;
		System.out.println("CustomerController.createCustomer() => " + customerDto);
		
		try {
			repository.createContact(customerDto);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "customer created");
		} catch(InvalidCreateContactParametersException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "invalid create parameters");
		} catch(DuplicatedContactException e) {		
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "duplicated customer");
		} catch(DuplicatedEmailException e) {		
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "duplicated email");
		
		} catch(Exception e) {			
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
		}
		
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value="{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deleteContact(
			@PathVariable("id") int	id){

		ResponseDto dto = null;
		
		try {
			repository.deleteContact(id);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "contact deleted");
		} catch(ContactNotFoundException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "contact not found");
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
		}
		
		return ResponseEntity.ok(dto);
	}
}
