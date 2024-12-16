package hua.project.Service;


import hua.project.Entities.Tenant;
import hua.project.Repository.TenantRepository;
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


  public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
  }


  public void saveTenant(Tenant tenant) {
        tenantRepository.save(tenant);
  }

public Tenant getTenantById(int id) {
        return tenantRepository.findById(id).get();
}


}
