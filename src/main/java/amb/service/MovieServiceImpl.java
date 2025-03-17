package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.Movie;
import amb.repository.MovieRepository;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return List.of();
    }

    @Override
    public Movie getMovieById(String id) {
        return null;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return null;
    }

    @Override
    public Movie updateMovie(String id, Movie movieDetails) {
        return null;
    }

    @Override
    public boolean deleteMovie(String id) {
        return false;
    }

    @Override
    public long countMovie() {
        return 0;
    }
}
