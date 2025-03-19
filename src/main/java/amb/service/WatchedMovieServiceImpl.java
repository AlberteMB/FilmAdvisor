package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.WatchedMovie;
import amb.repository.WatchedMovieRepository;

import java.util.List;
import java.util.Optional;

public class WatchedMovieServiceImpl implements WatchedMovieService {

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Override
    public List<WatchedMovie> getAllWatchedMovies() {
        return watchedMovieRepository.findAll();
    }

    @Override
    public WatchedMovie createWatchedMovie(WatchedMovie watchedMovie) {
        return watchedMovieRepository.save(watchedMovie);
    }

    @Override
    public WatchedMovie getWatchedMovieById(String id) {
        return watchedMovieRepository.findById(id).orElse(null);
    }

    @Override
    public WatchedMovie updateWatchedMovie(String id, WatchedMovie watchedMovieDetails) {
        Optional<WatchedMovie> watchedMovie = watchedMovieRepository.findById(id);
        if(watchedMovie.isPresent()){
            watchedMovie.get().setUser(watchedMovieDetails.getUser());
            watchedMovie.get().setMovie(watchedMovieDetails.getMovie());
            watchedMovie.get().setLiked(watchedMovieDetails.isLiked());
            return watchedMovieRepository.save(watchedMovie.get());
        }
        return null;
    }

    @Override
    public boolean deleteWatchedMovie(String id) {
        watchedMovieRepository.deleteById(id);
        Optional<WatchedMovie> watchedMovie = watchedMovieRepository.findById(id);
        return watchedMovie.isPresent();
    }

    @Override
    public long countWatchedMovie() {
        return watchedMovieRepository.count();
    }
}
