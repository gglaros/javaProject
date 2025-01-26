package hua.project.Repository;

import hua.project.Entities.Owner;

import hua.project.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
Optional<Owner> findByUser_Username(String username);
    Owner findByUser(User user);
    Owner findByUserId(Integer userId);
}
