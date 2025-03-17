package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import amb.model.Watchlist;
import amb.repository.WatchlistRepository;

import java.util.List;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public List<Watchlist> getAllWatchlists(String id) {
        return List.of();
    }

    @Override
    public Watchlist createWatchlist(Watchlist watchlist) {
        return null;
    }

    @Override
    public Watchlist getWatchlistById(String id) {
        return null;
    }

    @Override
    public Watchlist updateWatchlist(String id, Watchlist watchlistDetails) {
        return null;
    }

    @Override
    public boolean deleteWatchlist(String id) {
        return false;
    }

    @Override
    public long countWatchlist(String id) {
        return 0;
    }
}
