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

    public Optional<Owner> findByUsername(String username) {
        return ownerRepository.findByUser_Username(username);
    }

    public Owner findByUser(User user) {
        return ownerRepository.findByUser(user);
    }


@Transactional
public void saveOwner(Owner owner,User user,String email) {
    owner.setUser(user);
    owner.setEmail(user.getEmail());
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
    public Owner getOwnerByUserId(int userId) {
        return ownerRepository.findByUserId(userId);
    }

    public void deleteOwnerById(int ownerId) {
        ownerRepository.deleteById(ownerId);
    }

}
