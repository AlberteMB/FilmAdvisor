package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.model.Movie;
import amb.repository.MovieRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        Optional<Movie> existingMovie = movieRepository.findById(pk, sk);
        if (existingMovie.isPresent()) {
            Movie updated = existingMovie.get();
            updated.setDirector(movieDetails.getDirector());
            updated.setActors(movieDetails.getActors());
            updated.setGenres(movieDetails.getGenres());
            updated.setRating(movieDetails.getRating());
            updated.setYear(movieDetails.getYear());
            updated.setReleasedDate(movieDetails.getReleasedDate());
            updated.setSynopsis(movieDetails.getSynopsis());
            // Añadir más campos si fuera necesario
            movieRepository.save(updated);
            return updated;
        } else {
            throw new RuntimeException("Movie not found with pk=" + pk + " and sk=" + sk);
        }
    }

    @Override
    public boolean deleteMovie(String pk, String sk) {
        Optional<Movie> existingMovie = movieRepository.findById(pk, sk);
        if (existingMovie.isPresent()) {
            movieRepository.delete(pk, sk);
            return true;
        }
        return false;
    }

    @Override
    public long countMovies() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> findMoviesByGenreAndPlatforms(String genre, List<String> platforms) {
        // Para hacerlo más óptimo: usar Query sobre PartitionKey que incluya el Genre
        List<Movie> movies = movieRepository.findByGenre(genre);

        // Luego filtrar por las plataformas
        return movies.stream()
                .filter(movie -> movie.getPlatforms() != null &&
                        movie.getPlatforms().stream().anyMatch(platforms::contains))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> findRandomMoviesByGenreAndPlatforms(String genre, List<String> platforms, int limit) {
        List<Movie> candidates = findMoviesByGenreAndPlatforms(genre, platforms);
        Collections.shuffle(candidates);
        return candidates.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> findMoviesByYear(int year) {
        return movieRepository.findByYear(year);
    }

    @Override
    public List<Movie> findRecentlyAddedMovies(LocalDate sinceDate) {
        return movieRepository.findByReleasedDateAfter(sinceDate);
    }
}

