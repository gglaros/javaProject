package hua.project.Service;

import hua.project.Entities.TenantApplication;
import hua.project.Repository.OwnerRepository;
import hua.project.Repository.PropertyRepository;
import hua.project.Repository.TenantApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
