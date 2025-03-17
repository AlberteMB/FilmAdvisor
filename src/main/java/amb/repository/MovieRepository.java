package amb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
