package hua.project.Service;


import hua.project.Entities.Owner;
import hua.project.Entities.Tenant;
import hua.project.Entities.User;
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

public Tenant getTenantById(int id) {
        return tenantRepository.findById(id).get();
}


}
