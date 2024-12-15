package hua.project.Service;


import hua.project.Repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {


TenantRepository tenantRepository;

    @Autowired
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }
}
