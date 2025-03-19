package amb.service;

import amb.model.Watchlist;

import java.util.List;

public interface WatchlistService {

    List<Watchlist> getAllWatchlists();
    Watchlist createWatchlist(Watchlist watchlist);
    Watchlist getWatchlistById(String id);
    Watchlist updateWatchlist(String id, Watchlist watchlistDetails);
    boolean deleteWatchlist(String id);
    long countWatchlist();
}
