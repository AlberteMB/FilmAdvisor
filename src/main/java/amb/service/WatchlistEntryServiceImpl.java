package amb.service;

import amb.model.WatchlistEntry;
import amb.repository.WatchlistEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class WatchlistEntryServiceImpl implements WatchlistEntryService {

    @Autowired
    WatchlistEntryRepository watchlistEntryRepository;

    @Override
    public List<WatchlistEntry> getAllWatchlistEntries() {
        return watchlistEntryRepository.findAll();
    }
    @Override
    public WatchlistEntry getWatchlistEntry(String id) {
        return watchlistEntryRepository.findById(id).orElse(null);
    }
    @Override
    public WatchlistEntry createWatchlistEntry(WatchlistEntry watchlistEntry) {
        return watchlistEntryRepository.save(watchlistEntry);
    }
    @Override
    public WatchlistEntry updateWatchlistEntry(String id, WatchlistEntry watchlistEntry) {
        Optional<WatchlistEntry> watchlistEntry1 = watchlistEntryRepository.findById(id);
        if (watchlistEntry1.isPresent()) {
            watchlistEntry.setAddedAt(watchlistEntry1.get().getAddedAt());
            watchlistEntry.setNote(watchlistEntry1.get().getNote());
            return watchlistEntryRepository.save(watchlistEntry);
        }
        return null;
    }

    @Override
    public Boolean deleteWatchlistEntry(String id) {
        watchlistEntryRepository.deleteById(id);
        Optional<WatchlistEntry> watchlistEntry = watchlistEntryRepository.findById(id);
        return watchlistEntry.isPresent();
    }

    @Override
    public Long countWatchlistEntries() {
        return watchlistEntryRepository.count();
    }
}
