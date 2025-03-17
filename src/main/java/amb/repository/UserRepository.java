package amb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
