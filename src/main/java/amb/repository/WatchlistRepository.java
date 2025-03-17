package amb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
}
