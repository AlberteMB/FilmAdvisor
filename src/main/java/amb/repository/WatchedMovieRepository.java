package amb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.WatchedMovie;

public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, String> {
}
