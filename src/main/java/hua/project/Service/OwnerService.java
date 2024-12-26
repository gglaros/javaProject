package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Entities.Property;
import hua.project.Repository.OwnerRepository;
import hua.project.Repository.PropertyRepository;
import hua.project.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {


private final OwnerRepository ownerRepository;
private final UserRepository userRepository;
private final PropertyRepository propertyRepository;

public OwnerService(OwnerRepository ownerRepository,PropertyRepository propertyRepository ,UserRepository userRepository) {
    this.ownerRepository = ownerRepository;
    this.propertyRepository = propertyRepository;
    this.userRepository = userRepository;
}

@Transactional
public List<Owner> getAllOwners() {
    return ownerRepository.findAll();
}

//    public boolean hasProfile(String username) {
//        User user = userRepository.findByUsername(username) .orElseThrow(() -> new RuntimeException("User not found"));
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//        return ownerRepository.existsByUser(Optional.of(user));
//    }
//
//    public void saveOwnere(Owner owner, String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        owner.setUser(user);
//        ownerRepository.save(owner);
//    }

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
    propertyRepository.save(property);

}

}
