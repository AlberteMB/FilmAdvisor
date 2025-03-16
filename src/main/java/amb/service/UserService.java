package amb.service;

import amb.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(String id);
    User updateUser(String id, User userDetails);
    boolean deleteUser(String id);
    long countUser();
}
