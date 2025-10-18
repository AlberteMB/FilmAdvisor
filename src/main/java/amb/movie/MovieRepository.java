package amb.movie;

import software.amazon.awssdk.enhanced.dynamodb.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieRepository {

    void save(Movie movie);

    List<Movie> findByPlatform(String platform);

    List<Movie> findByPlatformAndGenre(String platform, Genre genre);

    List<Movie> findByGenre(Genre genre);

    List<Movie> findByYear(int year);

    List<Movie> findAll();

    Long countMovies();

    DynamoDbEnhancedClient getEnhancedClient();

    List<Movie> findByTitle(String title);

    List<Movie> findByActor(String actor);

    List<Movie> findByDirector(String director);

    List<Movie> findByYearRange(int startYear, int endYear);

    List<Movie> findByMinDuration(int minDuration);

    List<Movie> findByMinImdbRating(double minRating);

    List<Movie> searchMovies(String title, Genre genre, List<String> platforms, 
                           Integer year, Integer minDuration, Double minImdbRating);

    Optional<Movie> findByMovieId(String movieId);

    List<Movie> getTopRatedMovies(int limit);

    List<Movie> getLatestMovies(int limit);

    List<Movie> findByPlatformAndYear(String platform, int year);

    List<Movie> findByPlatformAndMinDuration(String platform, int minDuration);

    Map<String, Long> getMovieStatsByPlatform();

    Map<Genre, Long> getMovieStatsByGenre();

    Map<Integer, Long> getMovieStatsByYear();






}
