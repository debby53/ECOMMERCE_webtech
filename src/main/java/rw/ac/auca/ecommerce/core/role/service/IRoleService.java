package rw.ac.auca.ecommerce.core.role.service;

import rw.ac.auca.ecommerce.core.role.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role createRole(Role role);
    List<Role> findRolesByState(Boolean active);
    Role findRoleByNameAndState(String name, Boolean active);
}