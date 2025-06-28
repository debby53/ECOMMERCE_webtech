package rw.ac.auca.ecommerce.core.user.service;

import rw.ac.auca.ecommerce.controller.user.UserController;
import rw.ac.auca.ecommerce.core.user.model.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IUserService {
    User createUser(User user, Set<String> roleNames);
    List<User> findUsersByState(Boolean active);
    User findByUsernameAndState(String username, Boolean active);
    User findById(UUID id);

    User updateUser(UserController.UserFormDTO userForm, Set<String> roleNames);
}