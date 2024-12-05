package hua.project.Service;

import hua.project.Entities.Owner;
import hua.project.Repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

private OwnerRepository ownerRepository;

public OwnerService(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
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


}
