package hua.project.Repository;

import hua.project.Entities.TenantApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantApplicationRepository extends JpaRepository<TenantApplication, Integer> {
    List<TenantApplication> findAllByTenantId(int tenantId);

}
