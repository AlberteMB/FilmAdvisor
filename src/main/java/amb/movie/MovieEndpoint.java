package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.annotation.Nullable;
import org.apache.commons.configuration2.tree.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;


import java.util.*;

@Endpoint
@AnonymousAllowed
public class MovieEndpoint {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieEndpoint(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Save movie
    public void save(Movie movie) {
        System.out.println("Saving movie: " + movie);
        movieRepository.save(movie);
    }

    public List<Movie> findByPlatform(String platform) {
        return movieRepository.findByPlatform(platform);
    }

    public List<Movie> findByPlatformAndGenre(String platform, Genre genre) {
        return movieRepository.findByPlatformAndGenre(platform, genre);
    }

    public List<Movie> getFilteredRandomMovies(@Nullable Genre genre, List<String> platforms, int numMovies) {

        System.out.println("Parámetros recibidos: platform=" + platforms + ", genres=" + genre + ", count=" + numMovies);

        Set<Movie> uniqueMovies = new HashSet<>();

        for (String platform : platforms) {
            List<Movie> movies = movieRepository.findByPlatformAndGenre(platform, genre);
            uniqueMovies.addAll(movies);
        }

        System.out.println("🎬 Películas filtradas por platform y genre: " + uniqueMovies.size());

        List<Movie> shuffled = new ArrayList<>(uniqueMovies);
        Collections.shuffle(shuffled);

        return shuffled.stream().limit(numMovies).toList();
    }




}
