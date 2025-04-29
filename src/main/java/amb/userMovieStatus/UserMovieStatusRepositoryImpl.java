package amb.userMovieStatus;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserMovieStatusRepositoryImpl implements WatchedMovieService {

    @Autowired
    private UserMovieStatusRepository watchedMovieRepository;

    @Override
    public List<UserMovieStatus> getAllWatchedMovies() {
        return watchedMovieRepository.findAll();
    }

    @Override
    public UserMovieStatus createWatchedMovie(UserMovieStatus watchedMovie) {
        return watchedMovieRepository.save(watchedMovie);
    }

    @Override
    public UserMovieStatus getWatchedMovieById(String id) {
        return watchedMovieRepository.findById(id).orElse(null);
    }

    @Override
    public UserMovieStatus updateWatchedMovie(String id, UserMovieStatus watchedMovieDetails) {
        Optional<UserMovieStatus> watchedMovie = watchedMovieRepository.findById(id);
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
        Optional<UserMovieStatus> watchedMovie = watchedMovieRepository.findById(id);
        return watchedMovie.isPresent();
    }

    @Override
    public long countWatchedMovie() {
        return watchedMovieRepository.count();
    }
}
