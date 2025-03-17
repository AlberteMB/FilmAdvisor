package amb.repository;

import amb.model.DiscardedMovie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscardedMovieRepository extends JpaRepository<DiscardedMovie, String> {
}
