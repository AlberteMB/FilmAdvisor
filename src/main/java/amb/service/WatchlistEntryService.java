package amb.service;

import amb.model.WatchlistEntry;

import java.util.List;

public interface WatchlistEntryService {
    List<WatchlistEntry> getAllWatchlistEntries();
    WatchlistEntry getWatchlistEntry(String id);
    WatchlistEntry createWatchlistEntry(WatchlistEntry watchlistEntry);
    WatchlistEntry updateWatchlistEntry(String id, WatchlistEntry watchlistEntry);
    Boolean deleteWatchlistEntry(String id);
    Long countWatchlistEntries();
}
