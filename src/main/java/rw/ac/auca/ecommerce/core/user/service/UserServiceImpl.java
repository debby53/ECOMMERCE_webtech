package rw.ac.auca.ecommerce.core.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.controller.user.UserController;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.service.IRoleService;
import rw.ac.auca.ecommerce.core.user.model.User;
import rw.ac.auca.ecommerce.core.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    // private final PasswordEncoder passwordEncoder;  // Uncomment to enable password encoding

    @Override
    public User createUser(User user, Set<String> roleNames) {
        // user.setPassword(passwordEncoder.encode(user.getPassword())); // Uncomment to encode password on create
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleService.findRoleByNameAndState(roleName, Boolean.TRUE);
            roles.add(role);
        }
        user.setRoles(roles);
        user.setActive(Boolean.TRUE);
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersByState(Boolean active) {
        return userRepository.findByActive(active);
    }

    @Override
    public User findByUsernameAndState(String username, Boolean active) {
        return userRepository.findByUsernameAndActive(username, active)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    // New method: find user by ID
    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    // New method: update user info
    @Override
    public User updateUser(UserController.UserFormDTO userForm, Set<String> roleNames) {
        User user = findById(userForm.getId());

        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setActive(userForm.getActive());

        // Uncomment below to update password only if provided and encode it
        /*
        if (userForm.getPassword() != null && !userForm.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }
        */

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleService.findRoleByNameAndState(roleName, Boolean.TRUE);
            roles.add(role);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
