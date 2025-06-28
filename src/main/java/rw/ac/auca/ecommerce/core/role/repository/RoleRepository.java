package rw.ac.auca.ecommerce.core.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.ecommerce.core.role.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Optional<Role> findByNameAndActive(String name, Boolean active);
    List<Role> findByActive(Boolean active);
}
