package rw.ac.auca.ecommerce.core.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.ecommerce.core.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByActive(Boolean active);
    Optional<User> findByUsernameAndActive(String username, Boolean active);

}