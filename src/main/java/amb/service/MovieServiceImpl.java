package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.Movie;
import amb.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(String id, Movie movieDetails) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()){
            movie.get().setTitle(movieDetails.getTitle());
            movie.get().setYear(movieDetails.getYear());
            movie.get().setGenres(movieDetails.getGenres());
            movie.get().setDirector(movieDetails.getDirector());
            movie.get().setRating(movieDetails.getRating());
            movie.get().setActors(movieDetails.getActors());
            return movieRepository.save(movie.get());
        }
        return null;
    }

    @Override
    public boolean deleteMovie(String id) {
        movieRepository.deleteById(id);
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.isPresent();
    }

    @Override
    public long countMovie() {
        return movieRepository.count();
    }
}
