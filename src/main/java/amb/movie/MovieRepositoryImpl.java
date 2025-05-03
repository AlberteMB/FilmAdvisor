package amb.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class MovieRepositoryImpl implements MovieRepository  {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Movie> movieTable;
    private final String tableName = "Movies";

    @Autowired
    public MovieRepositoryImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.movieTable = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

     @Override
    public void save(Movie movie) {
        DynamoDbTable<Movie> table = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
        table.putItem(movie);
    }

    @Override
    public List<Movie> findByPlatform(String platform) {
        Key key = Key.builder().partitionValue(platform).build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);

        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = movieTable.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            for (Movie movie : page.items()) {
                results.add(movie);
            }
        }
        return results;
    }

    @Override
    public List<Movie> findByPlatformAndGenre(String platform, Movie.Genre genre) {
        Key key = Key.builder().partitionValue(platform).build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);

        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = movieTable.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            for (Movie movie : page.items()) {
                if (movie.getGenres().contains(genre)) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = movieTable.scan();

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    @Override
    public Long countMovies() {
        return movieTable.scan()
                .stream()
                .mapToLong(page -> page.items().size())
                .sum();
    }

//    @Override
//    public List<Movie> findByYear(int year) {
//        return List.of();
//    }

//    @Override
//    public Optional<Movie> findByTitle(String title) {
//        return Optional.empty();
//    }

//    @Override
//    public List<Movie> findByActor(String actor) {
//        return List.of();
//    }

//    @Override
//    public List<Movie> findByDirector(String director) {
//        return List.of();
//    }

}

