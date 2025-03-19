package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.User;
import amb.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(String id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setUsername(userDetails.getUsername());
            user.get().setEmail(userDetails.getEmail());
            user.get().setPassword(userDetails.getPassword());
            user.get().setRole(userDetails.getRole());
            return userRepository.save(user.get());
        }
        return null;
    }

    @Override
    public boolean deleteUser(String id) {
        userRepository.deleteById(id);
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }

    @Override
    public long countUser() {
        return userRepository.count();
    }
}
