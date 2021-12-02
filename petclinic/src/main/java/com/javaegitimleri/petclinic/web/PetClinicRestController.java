package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaegitimleri.petclinic.exception.InternalServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	private PetClinicService petClinicService;

	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		List<Owner> owners = petClinicService.findOwners();
		return ResponseEntity.ok(owners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String ln) {
		List<Owner> owners = petClinicService.findOwners(ln);
		return ResponseEntity.ok(owners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
		try {
			Owner owner = petClinicService.findOwner(id);
			return ResponseEntity.ok(owner);
		} catch (OwnerNotFoundException o) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner)
	{
		try {
			petClinicService.createOwner(owner);
			Long id = owner.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/owner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerReq)
	{
		try {
			Owner owner = petClinicService.findOwner(id);
			owner.setFirstName(ownerReq.getFirstName());
			owner.setLastName(ownerReq.getLastName());
			petClinicService.update(owner);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id)
	{
		try {
			Owner owner = petClinicService.findOwner(id);
			petClinicService.deleteOwner(id);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception ex) {
			throw new InternalServerException(ex);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/owner/{id}",produces="application/json")
	public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id)
	{
		try {
			Owner owner = petClinicService.findOwner(id);
			Link self = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner"+id).withSelfRel();
			Link create = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner").withRel("create");
			Link update = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner" + id).withRel("update");
			Link delete = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner" + id).withRel("delete");
            
			Resource<Owner> resource = new Resource<Owner>(owner,self,create,update,delete);
			
			return ResponseEntity.ok(resource);
		} catch (OwnerNotFoundException o) {
			return ResponseEntity.notFound().build();
		}
	}
	

}
