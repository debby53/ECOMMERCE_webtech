package rw.ac.auca.ecommerce.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.user.model.User;
import rw.ac.auca.ecommerce.core.role.service.IRoleService;
import rw.ac.auca.ecommerce.core.user.service.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The class UserController.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/user/")
public class UserController {

    private final IUserService userService;
    private final IRoleService roleService;

    @GetMapping
    public String getUserManagementPage(Model model) {
        model.addAttribute("userForm", new UserFormDTO());
        model.addAttribute("users", userService.findUsersByState(Boolean.TRUE));
        model.addAttribute("roles", roleService.findRolesByState(Boolean.TRUE));
        model.addAttribute("searchTerm", "");
        return "customer/user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("userForm") UserFormDTO userForm, Model model) {
        if (Objects.nonNull(userForm) && userForm.getUsername() != null && userForm.getPassword() != null &&
                userForm.getEmail() != null && userForm.getRoleIds() != null && !userForm.getRoleIds().isEmpty()) {
            User user = new User();
            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());
            user.setEmail(userForm.getEmail());
            user.setActive(Boolean.TRUE);

            Set<String> roleNames = new HashSet<>(userForm.getRoleIds()); // convert to Set<String>
            userService.createUser(user, roleNames); // pass both arguments

            model.addAttribute("message", "User Created Successfully");
        } else {
            model.addAttribute("error", "User Creation Failed: Invalid form data");
        }

        model.addAttribute("userForm", userForm != null ? userForm : new UserFormDTO());
        model.addAttribute("users", userService.findUsersByState(Boolean.TRUE));
        model.addAttribute("roles", roleService.findRolesByState(Boolean.TRUE));
        model.addAttribute("searchTerm", "");
        return "customer/user";
    }

    // ----------- New methods added here -------------

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") UUID userId, Model model) {
        User user = userService.findById(userId); // You need to add findById(UUID) in your IUserService and impl
        UserFormDTO dto = new UserFormDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setRoleIds(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        model.addAttribute("userForm", dto);
        model.addAttribute("roles", roleService.findRolesByState(Boolean.TRUE));
        model.addAttribute("users", userService.findUsersByState(Boolean.TRUE));
        return "customer/user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("userForm") UserFormDTO userForm, Model model) {
        try {
            Set<String> roleNames = new HashSet<>(userForm.getRoleIds());
            userService.updateUser(userForm, roleNames); // You need to create this method in IUserService & impl
            model.addAttribute("message", "User updated successfully");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating user: " + e.getMessage());
        }
        model.addAttribute("roles", roleService.findRolesByState(Boolean.TRUE));
        model.addAttribute("users", userService.findUsersByState(Boolean.TRUE));
        return "customer/user";
    }

    // ----------- DTO updated -------------

    public static class UserFormDTO {
        private UUID id;
        private String username;
        private String password; // password optional on update maybe
        private String email;
        private Boolean active;
        private List<String> roleIds;

        public UUID getId() {
            return id;
        }
        public void setId(UUID id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public Boolean getActive() {
            return active;
        }
        public void setActive(Boolean active) {
            this.active = active;
        }
        public List<String> getRoleIds() {
            return roleIds;
        }
        public void setRoleIds(List<String> roleIds) {
            this.roleIds = roleIds;
        }

    }
}
