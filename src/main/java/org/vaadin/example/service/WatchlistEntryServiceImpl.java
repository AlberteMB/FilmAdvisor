package org.vaadin.example.service;

import org.vaadin.example.model.WatchlistEntry;

import java.util.List;

public class WatchlistEntryServiceImpl implements WatchlistEntryService {

    @Override
    public List<WatchlistEntry> getAllWatchlistEntries(String id) {
        return List.of();
    }

    @Override
    public WatchlistEntry getWatchlistEntry(String id) {
        return null;
    }

    @Override
    public WatchlistEntry createWatchlistEntry(String id, WatchlistEntry watchlistEntry) {
        return null;
    }

    @Override
    public WatchlistEntry updateWatchlistEntry(String id, WatchlistEntry watchlistEntry) {
        return null;
    }

    @Override
    public Boolean deleteWatchlistEntry(String id) {
        return null;
    }

    @Override
    public Long countWatchlistEntries(String id) {
        return 0L;
    }
}
