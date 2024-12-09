package hua.project.Service;

import hua.project.Entities.Property;
import hua.project.Repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

private PropertyRepository propertyRepository;

public PropertyService(PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
}

@Transactional
public List<Property> getAllProperty() {
    return propertyRepository.findAll();
}

@Transactional
public void saveProperty(Property property) {
    propertyRepository.save(property);
}

@Transactional
public Property getPropertyById(Integer id) {
    return propertyRepository.findById(id).get();
}



}
