package amb.service;

import amb.movie.Movie;
import amb.movie.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(String pk, String sk) {
        return movieRepository.findById(pk, sk);
    }

    @Override
    public Movie createMovie(Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(String pk, String sk, Movie movieDetails) {
        return movieDetails;
    }

    @Override
    public boolean deleteMovie(String pk, String sk) {

        return false;
    }

    @Override
    public long countMovies() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> findMoviesByGenreAndPlatforms(String genre, List<String> platforms) {
        return List.of();
    }

    @Override
    public List<Movie> findRandomMoviesByGenreAndPlatforms(String genre, List<String> platforms, int limit) {
        return List.of();
    }

    @Override
    public List<Movie> findMoviesByYear(int year) {
        return List.of();
    }

    @Override
    public List<Movie> findRecentlyAddedMovies(LocalDate sinceDate) {
        return List.of();
    }

}

