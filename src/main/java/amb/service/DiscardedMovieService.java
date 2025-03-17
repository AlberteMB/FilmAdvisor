package amb.service;

import amb.model.DiscardedMovie;

import java.util.List;

public interface DiscardedMovieService {
    List<DiscardedMovie> getAllDiscardedMovies();
    DiscardedMovie createDiscardedMovie(DiscardedMovie discardedMovie);
    DiscardedMovie getDiscardedMovieById(String id);
    DiscardedMovie updateDiscardedMovie(String id, DiscardedMovie discardedMovieDetails);
    boolean deleteDiscardedMovie(String id);
    boolean discardMovie(String id);
}
