package amb.movie;

import software.amazon.awssdk.enhanced.dynamodb.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    void save(Movie movie);

    List<Movie> findByPlatform(String platform);

    List<Movie> findByPlatformAndGenre(String platform, Movie.Genre genre);

    List<Movie> findByGenre(Movie.Genre genre);

    //List<Movie> findByYear(int year);

    List<Movie> findAll();

    Long countMovies();

    DynamoDbEnhancedClient getEnhancedClient();

    //Optional<Movie> findByMovieId(String movieId);

    //Optional<Movie> findByTitle(String title);

    //List<Movie> findByActor(String actor);

    //List<Movie> findByDirector(String director);






}
