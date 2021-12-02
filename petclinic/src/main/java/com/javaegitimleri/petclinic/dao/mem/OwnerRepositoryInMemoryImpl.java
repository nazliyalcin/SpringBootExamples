package com.javaegitimleri.petclinic.dao.mem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

@Repository //Çalışma zamanında bu sınıftan bir bean yaratır
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
    
	private Map<Long,Owner> ownersMap = new HashMap<>();
	
	public OwnerRepositoryInMemoryImpl(){
		
		Owner o1 = new Owner();
		o1.setId(1L);
		o1.setFirstName("kenan");
		o1.setLastName("sevindik");
		
		Owner o2 = new Owner();
		o2.setId(2L);
		o2.setFirstName("nazli");
		o2.setLastName("yalcin");
		
		
		Owner o3 = new Owner();
		o3.setId(3L);
		o3.setFirstName("dogancan");
		o3.setLastName("colak");
		
		Owner o4 = new Owner();
		o4.setId(4L);
		o4.setFirstName("emre");
		o4.setLastName("sahip");
		
		ownersMap.put(o1.getId(), o1);
		ownersMap.put(o2.getId(), o2);
		ownersMap.put(o3.getId(), o3);
		ownersMap.put(o4.getId(), o4);
	}
	
	
	@Override
	public List<Owner> findAll() {
		
		return new ArrayList<>(ownersMap.values());
	}

	@Override
	public Owner findById(Long id) {
		// TODO Auto-generated method stub
		return ownersMap.get(id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		// TODO Auto-generated method stub
		return ownersMap.values().stream().filter(o->o.getLastName().equals(lastName)).collect(Collectors.toList());
	}

	@Override
	public void Create(Owner owner) {
		// TODO Auto-generated method stub
		owner.setId(new Date().getTime());
		ownersMap.put(owner.getId(), owner);
		
	}

	@Override
	public Owner update(Owner owner) {
		ownersMap.replace(owner.getId(), owner);
		return owner;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ownersMap.remove(id);
		
	}

}
