package amb.service;

import amb.model.WatchlistEntry;

import java.util.List;

public interface WatchlistEntryService {
    List<WatchlistEntry> getAllWatchlistEntries(String id);
    WatchlistEntry getWatchlistEntry(String id);
    WatchlistEntry createWatchlistEntry(String id, WatchlistEntry watchlistEntry);
    WatchlistEntry updateWatchlistEntry(String id, WatchlistEntry watchlistEntry);
    Boolean deleteWatchlistEntry(String id);
    Long countWatchlistEntries(String id);
}
