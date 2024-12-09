package hua.project.Service;

import hua.project.Entities.OwnerApplication;
import hua.project.Repository.OwnerApplicationRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

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

}
