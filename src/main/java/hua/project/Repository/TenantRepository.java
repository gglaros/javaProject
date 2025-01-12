package hua.project.Repository;

import hua.project.Entities.Owner;
import hua.project.Entities.Tenant;
import hua.project.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByUser_Username(String username);
    Tenant findByUser(User user);
}
