package hua.project.Repository;

import hua.project.Entities.OwnerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerApplicationRepository extends JpaRepository<OwnerApplication, Integer> {
    List<OwnerApplication> findByOwnerId(Integer ownerId);
}
