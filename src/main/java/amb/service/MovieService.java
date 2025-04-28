package amb.service;

import amb.model.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(String pk, String sk);

    Movie createMovie(Movie movie);

    Movie updateMovie(String pk, String sk, Movie movieDetails);

    boolean deleteMovie(String pk, String sk);

    long countMovies();

    List<Movie> findMoviesByGenreAndPlatforms(String genre, List<String> platforms);

    List<Movie> findRandomMoviesByGenreAndPlatforms(String genre, List<String> platforms, int limit);

    List<Movie> findMoviesByYear(int year);

    List<Movie> findRecentlyAddedMovies(LocalDate sinceDate);


}
