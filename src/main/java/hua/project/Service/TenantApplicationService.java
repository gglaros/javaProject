package hua.project.Service;

import hua.project.Entities.*;
import hua.project.Repository.OwnerRepository;
import hua.project.Repository.PropertyRepository;
import hua.project.Repository.TenantApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantApplicationService {

    TenantApplicationRepository tenantApplicationRepository;

    OwnerService ownerService;
    PropertyService propertyService;

    public TenantApplicationService(TenantApplicationRepository tenantApplicationRepository, OwnerService ownerService, PropertyService propertyService) {
        this.tenantApplicationRepository = tenantApplicationRepository;
        this.ownerService = ownerService;
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
        return findAll().stream()
                .filter(tenantApplication -> tenantApplication.getTenant() !=null && tenantApplication.getTenant().getId() == tenantId)
                .collect(Collectors.toList());
    }


    @Transactional
    public void processTenantApplicationAction(int appId, String action) {
        TenantApplication tenantApplication = getTenantApplicationById(appId);
        if (tenantApplication == null) {
            throw new IllegalArgumentException("Application not found for ID: " + appId);
        }
        Property property = tenantApplication.getProperty();
        if ("confirm".equalsIgnoreCase(action)) {
            propertyService.saveStatusProperty(property);
            tenantApplication.setStatus(Status.APPROVED);
            tenantApplicationRepository.save(tenantApplication);
        } else if ("reject".equalsIgnoreCase(action)) {
            property.setStatus("not on eye");
            propertyService.saveProperty(property);
            tenantApplication.setStatus(Status.REJECTED);
            tenantApplicationRepository.save(tenantApplication);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
