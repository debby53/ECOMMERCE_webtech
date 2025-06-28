package rw.ac.auca.ecommerce.core.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findRolesByState(Boolean active) {
        return roleRepository.findByActive(active);
    }

    @Override
    public Role findRoleByNameAndState(String name, Boolean active) {
        return roleRepository.findByNameAndActive(name, active)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + name));
    }

}