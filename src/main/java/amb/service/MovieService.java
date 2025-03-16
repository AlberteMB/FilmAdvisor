package amb.service;

import amb.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(String id);
    Movie createMovie(Movie movie);
    Movie updateMovie(String id, Movie movieDetails);
    boolean deleteMovie(String id);
    long countMovie();
}
