package hua.project.Service;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Repository.OwnerRepository;
import hua.project.Repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {


private final OwnerRepository ownerRepository;

private final PropertyRepository propertyRepository;

public OwnerService(OwnerRepository ownerRepository,PropertyRepository propertyRepository ) {
    this.ownerRepository = ownerRepository;
    this.propertyRepository = propertyRepository;
}

@Transactional
public List<Owner> getAllOwners() {
    return ownerRepository.findAll();
}


@Transactional
public void saveOwner(Owner owner) {
    ownerRepository.save(owner);
}

@Transactional
public Owner getOwnerById(Integer id) {
    return ownerRepository.findById(id).get();
}

@Transactional
public void savePropertyToOwner(Owner owner, Property property) {
    property.setOwner(owner);
   // propertyService.saveProperty(property);
    propertyRepository.save(property);

}

}
