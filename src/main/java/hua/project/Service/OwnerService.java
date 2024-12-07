package hua.project.Service;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

private OwnerRepository ownerRepository;

private PropertyService propertyService;

public OwnerService(OwnerRepository ownerRepository, PropertyService propertyService) {
    this.ownerRepository = ownerRepository;
    this.propertyService = propertyService;
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


public void savePropertyToOwner(Owner owner, Property property) {
    property.setOwner(owner);
    propertyService.saveProperty(property);

}


}
