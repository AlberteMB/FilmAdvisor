package amb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.WatchlistEntry;

public interface WatchlistEntryRepository extends JpaRepository<WatchlistEntry, String> {
}
