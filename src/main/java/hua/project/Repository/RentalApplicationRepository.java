package hua.project.Repository;

import hua.project.Entities.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Integer> {
}
