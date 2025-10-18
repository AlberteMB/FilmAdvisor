package amb.user;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEndpoint(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar nuevo usuario
    public User register(String username, String email, String password) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Crear nuevo usuario
        User user = new User(username, email, passwordEncoder.encode(password), User.Role.USER);
        userRepository.save(user);
        
        // No devolver la contraseña
        user.setPassword(null);
        return user;
    }

    // Buscar usuario por ID
    public Optional<User> findById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setPassword(null); // No devolver la contraseña
        }
        return user;
    }

    // Buscar usuario por username
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setPassword(null); // No devolver la contraseña
        }
        return user;
    }

    // Buscar usuario por email
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setPassword(null); // No devolver la contraseña
        }
        return user;
    }

    // Obtener todos los usuarios (solo para admin)
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        // No devolver contraseñas
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    // Actualizar usuario
    public User updateUser(String id, String username, String email) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();
        
        // Verificar si el nuevo username ya existe (si es diferente)
        if (!user.getUsername().equals(username) && userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        // Verificar si el nuevo email ya existe (si es diferente)
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
        
        // No devolver la contraseña
        user.setPassword(null);
        return user;
    }

    // Cambiar contraseña
    public void changePassword(String id, String oldPassword, String newPassword) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();
        
        // Verificar contraseña actual
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Actualizar contraseña
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // Eliminar usuario
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // Verificar si existe usuario por username
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Verificar si existe usuario por email
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Login (verificar credenciales)
    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            user.get().setPassword(null); // No devolver la contraseña
            return user;
        }
        return Optional.empty();
    }

    // Obtener usuarios por rol
    public List<User> getUsersByRole(User.Role role) {
        List<User> users = userRepository.findByRole(role);
        users.forEach(user -> user.setPassword(null)); // No devolver contraseñas
        return users;
    }

    // Contar usuarios por rol
    public Long countUsersByRole(User.Role role) {
        return userRepository.countByRole(role);
    }

    // Buscar usuario por username o email
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
        if (user.isPresent()) {
            user.get().setPassword(null); // No devolver la contraseña
        }
        return user;
    }

    // Obtener estadísticas de usuarios
    public UserStats getUserStats() {
        Long totalUsers = userRepository.count();
        Long adminUsers = userRepository.countByRole(User.Role.ADMIN);
        Long regularUsers = userRepository.countByRole(User.Role.USER);
        
        return new UserStats(totalUsers, adminUsers, regularUsers);
    }

    // Clase interna para estadísticas de usuarios
    public static class UserStats {
        private final Long totalUsers;
        private final Long adminUsers;
        private final Long regularUsers;

        public UserStats(Long totalUsers, Long adminUsers, Long regularUsers) {
            this.totalUsers = totalUsers;
            this.adminUsers = adminUsers;
            this.regularUsers = regularUsers;
        }

        // Getters
        public Long getTotalUsers() { return totalUsers; }
        public Long getAdminUsers() { return adminUsers; }
        public Long getRegularUsers() { return regularUsers; }
    }
}
