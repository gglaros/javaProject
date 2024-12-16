package hua.project.Repository;

import hua.project.Entities.TenantApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantApplicationRepository extends JpaRepository<TenantApplication, Integer> {
}
