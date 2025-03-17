package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.DiscardedMovie;
import amb.repository.DiscardedMovieRepository;

import java.util.List;

public class DiscardedMovieServiceImpl implements DiscardedMovieService {

    @Autowired
    private DiscardedMovieRepository discardedMovieRepository;

    @Override
    public List<DiscardedMovie> getAllDiscardedMovies() {
        return List.of();
    }

    @Override
    public DiscardedMovie createDiscardedMovie(DiscardedMovie discardedMovie) {
        return null;
    }

    @Override
    public DiscardedMovie getDiscardedMovieById(String id) {
        return null;
    }

    @Override
    public DiscardedMovie updateDiscardedMovie(String id, DiscardedMovie discardedMovieDetails) {
        return null;
    }

    @Override
    public boolean deleteDiscardedMovie(String id) {
        return false;
    }

    @Override
    public boolean discardMovie(String id) {
        return false;
    }
}
