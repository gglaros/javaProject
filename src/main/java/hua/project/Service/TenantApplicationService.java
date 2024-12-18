package hua.project.Service;

import hua.project.Entities.Property;
import hua.project.Entities.TenantApplication;
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


    public void save(TenantApplication tenantApplication) {
        tenantApplicationRepository.save(tenantApplication);
    }

    public List<TenantApplication> findAll() {
        return tenantApplicationRepository.findAll();
    }


    @Transactional
    public List<TenantApplication> ApplicationsByTenantId(int tenantId) {
        return findAll().stream()
                .filter(tenantApplication -> tenantApplication.getTenant() !=null && tenantApplication.getTenant().getId() == tenantId)
                .collect(Collectors.toList());
    }






}
