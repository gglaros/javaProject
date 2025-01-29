package hua.project.Service;


import hua.project.Entities.*;
import hua.project.Repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    @Autowired
TenantRepository tenantRepository;


    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant findByUser(User user) {
        return tenantRepository.findByUser(user);
    }


  public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
  }



    @Transactional
    public void saveTenant(Tenant tenant, User user, String email) {
        tenant.setUser(user);
        tenant.setEmail(user.getEmail());
        tenantRepository.save(tenant);
    }
    @Transactional
    public void changeTenantStatus(Tenant tenant) {
        tenant.setValidation(Validation.PENDING);
        tenantRepository.save(tenant);
    }
    @Transactional
    public void validateTenant(Tenant tenant) {
        tenant.setValidation(Validation.VALIDATED);
        tenantRepository.save(tenant);
    }
    public void invalidateTenant(Tenant tenant) {
        tenant.setValidation(Validation.INVALID);
        tenantRepository.save(tenant);
    }

    @Transactional
    public void processTenantValidationStatus(int tenantId, String action) {
        Tenant tenant = getTenantById(tenantId);

        if ("confirm".equalsIgnoreCase(action)) {
            validateTenant(tenant);
        } else if ("reject".equalsIgnoreCase(action)) {
            invalidateTenant(tenant);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }


    public Tenant getTenantById(int id) {
        return tenantRepository.findById(id).get();
}

    public Tenant getTenantByUserId(int userId) {
        return tenantRepository.findByUserId(userId);
    }

    public void deleteTenantById(int tenantId) {
        tenantRepository.deleteById(tenantId);
    }


}
