package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Repository.OwnerApplicationRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

import static hua.project.Entities.Status.PENDING_APPROVAL;

@Service
public class OwnerApplicationService {

    private OwnerApplicationRepository ownerApplicationRepository;

    public OwnerApplicationService(OwnerApplicationRepository ownerApplicationRepository) {
        this.ownerApplicationRepository = ownerApplicationRepository;
    }

    @Transactional
 public List<OwnerApplication> getOwnerApplications() {
     return ownerApplicationRepository.findAll();
 }

 @Transactional
 public void saveOwnerApplication(OwnerApplication ownerApplication,Owner owner,Property property) {
     ownerApplication.setOwner(owner);
     ownerApplication.setProperty(property);
     ownerApplication.setStatus(PENDING_APPROVAL);
     ownerApplicationRepository.save(ownerApplication);
    }

}
