package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Repository.OwnerApplicationRepository;
import hua.project.Repository.OwnerRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static hua.project.Entities.Status.PENDING_APPROVAL;

@Service
public class OwnerApplicationService {

    private final OwnerRepository ownerRepository;
    private OwnerApplicationRepository ownerApplicationRepository;
    private PropertyService propertyService;
    private UserService userService;

    public OwnerApplicationService(OwnerApplicationRepository ownerApplicationRepository, PropertyService propertyService, UserService userService,OwnerRepository ownerRepository) {
        this.ownerApplicationRepository = ownerApplicationRepository;
        this.propertyService = propertyService;
        this.ownerRepository = ownerRepository;
        this.userService = userService;
    }


    @Transactional
 public List<OwnerApplication> getOwnerApplications() {
     return ownerApplicationRepository.findAll();
 }


    @Transactional
    public List<OwnerApplication> getOwnerApplicationsById(Integer id) {
        return ownerApplicationRepository.findByOwnerId(id);
    }


    @Transactional
    public OwnerApplication getOwnerApplicationById(Integer id) {
        return ownerApplicationRepository.findById(id).get();
    }

    @Transactional
    public void deleteAllApplicationsByOwnerId(int ownerId) {
        List<OwnerApplication> ownerApplications = ownerApplicationRepository.findAllByOwnerId(ownerId);
        ownerApplicationRepository.deleteAll(ownerApplications);
    }

    @Transactional
    public List<OwnerApplication> getOwnerApplicationsByOwnerId(Integer userId) {
        Owner owner=ownerRepository.findByUser(userService.getUserById(userId));
        System.out.println("hello  !!"+owner);
        return ownerApplicationRepository.findByOwnerId(owner.getId());
    }

 @Transactional
 public void saveOwnerApplication(OwnerApplication ownerApplication,Property property) {
     ownerApplication.setStatus(PENDING_APPROVAL);
     property.setStatus("waiting");
     ownerApplicationRepository.save(ownerApplication);
    }

    @Transactional
    public void changeOwnerApplication(OwnerApplication ownerApplication) {
        ownerApplication.setStatus(Status.APPROVED);
        ownerApplicationRepository.save(ownerApplication);
    }

    @Transactional
    public void processApplicationAction(int appId, String action) {
        OwnerApplication ownerApplication = getOwnerApplicationById(appId);
        if (ownerApplication == null) {
            throw new IllegalArgumentException("Application not found for ID: " + appId);
        }
        Property property = ownerApplication.getProperty();
        if ("confirm".equalsIgnoreCase(action)) {
            propertyService.saveStatusProperty(property);
            ownerApplication.setStatus(Status.APPROVED);
            ownerApplicationRepository.save(ownerApplication);
        } else if ("reject".equalsIgnoreCase(action)) {
         property.setStatus("not on eye");
         propertyService.saveProperty(property);
            ownerApplication.setStatus(Status.REJECTED);
            ownerApplicationRepository.save(ownerApplication);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

}
