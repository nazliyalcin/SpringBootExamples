package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;



public class PetClinicRestControllerTest {

	private RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}
	
	
	public void testGetOwnerbyId() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(),Matchers.equalTo("kenan"));
	}
	
	
	public void testGetOwnerByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=sevindik", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String,String>> body = response.getBody();
        List<String> firstnames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstnames, Matchers.containsInAnyOrder("kenan"));
	}
	
	
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String,String>> body = response.getBody();
        List<String> firstnames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstnames, Matchers.containsInAnyOrder("kenan","nazli","dogancan","emre"));

	}

	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("nida");
		owner.setLastName("yalcin");
		
		URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);
		Owner owner2 = restTemplate.getForObject(location, Owner.class);
        MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
	
	}
	
	
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("emre"));
       
	
		owner.setFirstName("ayse");
		restTemplate.put("http://localhost:8080/rest/owner/4", owner);
		
	     owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
		
	    MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("ayse"));

	
	}
	
	
	@Test
	public void testdeleteOwner() {
		try {
			restTemplate.delete("http://localhost:8080/rest/owner/4", Owner.class);
			
		} catch (HttpClientErrorException e) {
			// TODO Auto-generated catch block
			//Assert.fail("Bu kayıt yok");
		    MatcherAssert.assertThat(e.getStatusCode().value(), Matchers.equalTo(404));

			
			e.printStackTrace();
		}
		
	
	}
	
}
