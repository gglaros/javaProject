package hua.project.Service;

import hua.project.Entities.Property;
import hua.project.Repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Property> getPropertiesByOwnerId(int ownerId) {
        return getAllProperty().stream()
                .filter(property -> property.getOwner() != null && property.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
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
