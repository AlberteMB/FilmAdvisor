package amb.watchted;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, String> {
}
