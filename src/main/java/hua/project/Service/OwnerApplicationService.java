package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Repository.OwnerApplicationRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static hua.project.Entities.Status.PENDING_APPROVAL;

@Service
public class OwnerApplicationService {

    private OwnerApplicationRepository ownerApplicationRepository;

    public OwnerApplicationService(OwnerApplicationRepository ownerApplicationRepository) {
        this.ownerApplicationRepository = ownerApplicationRepository;
    }


    public boolean isPropertyOwnedByOwner(Property property, Owner owner) {
        // Επιβεβαίωση ότι το Property ανήκει στον Owner
        return Objects.equals(property.getOwner().getId(), owner.getId());
    }

    @Transactional
 public List<OwnerApplication> getOwnerApplications() {
     return ownerApplicationRepository.findAll();
 }

 @Transactional
 public void saveOwnerApplication(OwnerApplication ownerApplication) {
     ownerApplication.setStatus(PENDING_APPROVAL);
     ownerApplicationRepository.save(ownerApplication);
    }

}
