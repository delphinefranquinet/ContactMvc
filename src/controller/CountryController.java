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

import dto.CountryDto;
import dto.ResponseDto;
import dto.ResponseDtoStatus;
import persistence.exceptions.CountryNotFoundException;
import persistence.exceptions.DuplicatedAbreviationCountryException;
import persistence.exceptions.DuplicatedNameCountryException;
import persistence.exceptions.InvalidCreateCountrytParametersException;
import persitence.ApplicationRepository;


@Controller
@RequestMapping(value="country")
public class CountryController {
	
	@Autowired ApplicationRepository repository;
	
	@GetMapping(value="all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findAll(){
		ResponseDto dto = null;
		try {
			List<CountryDto> countries = repository.findAllCountryDtoWithMapping();
			if(countries == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "countries not found");
				
			}else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(countries);
			}
		}catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		return ResponseEntity.ok(dto);
		
	}
	
	@GetMapping(value="abreviation/{abreviation}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findOneByAbreviation(
			@PathVariable("abreviation") String abreviation){
		
		ResponseDto dto = null;
		try {
			CountryDto country = repository.findOneCountryByAbreviation(abreviation);
			if (country == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "country not found");
			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(country);
			}
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value="name/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> findOneByName(
			@PathVariable("name") String name){
		
		ResponseDto dto = null;
		try {
			CountryDto country = repository.findOneCountryByName(name);
			if (country == null) {
				dto = new ResponseDto(ResponseDtoStatus.FAILURE, "country not found");
			} else {
				dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "ok");
				dto.setPayload(country);
			}
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto>  createCountry(@RequestBody CountryDto countryDto) {
		ResponseDto dto = null;
		
		try {
			repository.createCountry(countryDto);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "country created");
		} catch(InvalidCreateCountrytParametersException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "duplicated abreviation");
		} catch(DuplicatedAbreviationCountryException e) {		
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "duplicated name");
		} catch(DuplicatedNameCountryException e) {		
		} catch(Exception e) {			
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
		}
		
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value="{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deleteCountry(
			@PathVariable("id") int	id){

		ResponseDto dto = null;
		
		try {
			repository.deleteCountryt(id);
			dto = new ResponseDto(ResponseDtoStatus.SUCCESS, "country deleted");
		} catch(CountryNotFoundException e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "country not found");
		} catch(Exception e) {
			dto = new ResponseDto(ResponseDtoStatus.FAILURE, "unexpected exception");
		}
		
		return ResponseEntity.ok(dto);
	}

}
