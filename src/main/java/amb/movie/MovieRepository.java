package amb.movie;

import software.amazon.awssdk.enhanced.dynamodb.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository {

    private final DynamoDbTable<Movie> movieTable;
    private final DynamoDbEnhancedClient enhancedClient;

    public MovieRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.movieTable = enhancedClient.table("Movies", TableSchema.fromBean(Movie.class));
    }

    public void save(Movie movie) {

        movieTable.putItem(movie);
    }


    public Optional<Movie> findById(String pk, String sk) {

        return Movie;
    }

    public List<Movie> findByYear(int year) {

        return results;
    }

    public List<Movie> findByReleasedDate(String releasedDate) {

        return results;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        movieTable.scan().items().forEach(movies::add);
        return movies;
    }

    // Delete a movie
    public void delete(String pk, String sk) {

    }

    // Count movies
    public long count() {
        return movieTable.scan().items().stream().count();
    }

    public List<Movie> findByGenre(String genre) {
        return results;
    }

    public List<Movie> findByReleasedDateAfter(LocalDate sinceDate) {

        return result;
    }

}
