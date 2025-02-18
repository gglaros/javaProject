package hua.project.Repository;

import hua.project.Entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findAllByOwnerId(int ownerId);
//    List<Property> findByRentPriceLessThanEqual(double maxPrice);
    List<Property> findByRentPriceLessThanEqualAndStatus(double maxPrice, String status);
}
