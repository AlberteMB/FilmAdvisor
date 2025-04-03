package amb.service;

import amb.model.Movie;
import amb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import amb.model.Watchlist;
import amb.repository.WatchlistRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Override
    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    @Override
    public Watchlist createWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist getWatchlistById(String id) {
        return watchlistRepository.findById(id).orElse(null);
    }

    @Override
    public Watchlist updateWatchlist(String id, Watchlist watchlistDetails) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        if (watchlist.isPresent()) {
            watchlist.get().setUser(watchlistDetails.getUser());
            watchlist.get().setWatchlistEntries(watchlistDetails.getWatchlistEntries());
            return watchlistRepository.save(watchlist.get());
        }
        return null;
    }

    @Override
    public boolean deleteWatchlist(String id) {
        watchlistRepository.deleteById(id);
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        return watchlist.isPresent();
    }

    @Override
    public long countWatchlist() {
        return watchlistRepository.count();
    }

    @Override
    public void addMovieToWatchlist(User user, Movie movie) {
        Watchlist watchlist = user.getWatchlist();

        if (watchlist == null) {
            watchlist = new Watchlist();
            watchlist.setUser(user);
        }

        watchlist.addMovie(movie); // Ahora usamos el método de la entidad
        watchlistRepository.save(watchlist);
    }

}


