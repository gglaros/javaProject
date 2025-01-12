package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Repository.PropertyRepository;
import hua.project.Repository.TenantApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantApplicationService {

    TenantApplicationRepository tenantApplicationRepository;
    private final PropertyService propertyService;


    public TenantApplicationService(TenantApplicationRepository tenantApplicationRepository, PropertyService propertyService) {
        this.tenantApplicationRepository = tenantApplicationRepository;
        this.propertyService = propertyService;
    }

    public void save(TenantApplication tenantApplication, Property property, Owner owner) {
        tenantApplication.setStatus(Status.PENDING_APPROVAL);
        tenantApplication.setProperty(property);
        tenantApplication.setOwner(owner);
        tenantApplicationRepository.save(tenantApplication);
    }

    public List<TenantApplication> findAll() {
        return tenantApplicationRepository.findAll();
    }

    @Transactional
    public TenantApplication getTenantApplicationById(Integer id) {
        return tenantApplicationRepository.findById(id).get();
    }


    @Transactional
    public List<TenantApplication> ApplicationsByTenantId(int tenantId) {
        return tenantApplicationRepository.findAllByTenantId(tenantId);
    }

    @Transactional
    public List<TenantApplication> ApplicationsByOwnerId(int ownerId) {
        return findAll().stream()
                .filter(tenantApplication -> tenantApplication.getTenant() !=null && tenantApplication.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
    }



        @Transactional
    public List<Property> getPropertiesByOnEyeStatusAndNoApplication(int tenantId) {
        List<Property> allProperties = propertyService.getAllProperty();

        List<TenantApplication> tenantApplications = ApplicationsByTenantId(tenantId);

            return allProperties.stream().filter(property -> property.getStatus() != null && property.getStatus().equalsIgnoreCase("on eye") &&
                    tenantApplications.stream().noneMatch(application -> application.getProperty().getId() == property.getId() &&
                            application.getStatus() != null)).collect(Collectors.toList());
        }


    @Transactional
    public void processTenantApplicationAction(int appId, String action) {
        TenantApplication tenantApplication = getTenantApplicationById(appId);

        Property property = tenantApplication.getProperty();
        if ("confirm".equalsIgnoreCase(action)) {
            propertyService.saveStatusProperty(property);
            tenantApplication.setStatus(Status.APPROVED);
            tenantApplicationRepository.save(tenantApplication);

        } else if ("reject".equalsIgnoreCase(action)) {
            propertyService.saveProperty(property);
            tenantApplication.setStatus(Status.REJECTED);
            tenantApplicationRepository.save(tenantApplication);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
