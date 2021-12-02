package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;

@Service // İlgili sınıftan tek bir bean yaratacak
@Transactional
public class PetClinicServiceImpl implements PetClinicService {
    
	private OwnerRepository ownerRepository;
	
	
	@Autowired //ownerrepository tipindeki bir beani setter metot vasıtasıyla petclinic servise enjecte edecek.
	public void setOwnerRepository(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public List<Owner> findOwners() {
		// TODO Auto-generated method stub
		return ownerRepository.findAll();
	}

	@Override
	public List<Owner> findOwners(String lastName) {
		// TODO Auto-generated method stub
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		// TODO Auto-generated method stub
		Owner owner = ownerRepository.findById(id);
		if(owner == null) throw new OwnerNotFoundException("Owner not found with id:" + id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		// TODO Auto-generated method stub
		ownerRepository.Create(owner);
	}

	@Override
	public void update(Owner owner) {
		ownerRepository.update(owner);

	}

	@Override
	public void deleteOwner(Long id) {
		// TODO Auto-generated method stub
		ownerRepository.delete(id);

	}

}
