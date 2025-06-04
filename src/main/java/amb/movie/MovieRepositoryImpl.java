package amb.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public class MovieRepositoryImpl implements MovieRepository  {

    private final DynamoDbEnhancedClient enhancedClient;
    //private final DynamoDbTable<Movie> movieTable;
    private final String tableName = "Movie";

    @Autowired
    public MovieRepositoryImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        //this.movieTable = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

    private DynamoDbTable<Movie> getTable() {
        return enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

     @Override
    public void save(Movie movie) {
        DynamoDbTable<Movie> table = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
        table.putItem(movie);
    }

    @Override
    public List<Movie> findByPlatform(String platform) {
        DynamoDbTable<Movie> table = getTable();

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(platform).build());

        List<Movie> results = new ArrayList<>();

        table.query(r -> r.queryConditional(queryConditional))
                .forEach(page -> results.addAll(page.items()));

        return results;
    }

    @Override
    public List<Movie> findByPlatformAndGenre(String platform, Genre genre) {
        DynamoDbTable<Movie> table = getTable();

        // Usamos el prefix "Genre#"
        String genrePrefix = genre.name() + "#";

        QueryConditional queryConditional = QueryConditional
                .sortBeginsWith(Key.builder()
                        .partitionValue(platform)
                        .sortValue(genrePrefix)
                        .build());

        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = table.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    @Override
    public List<Movie> findByGenre(Genre genre) {
        DynamoDbEnhancedClient enhancedClient = getEnhancedClient();
        DynamoDbTable<Movie> table = enhancedClient.table("Movie", TableSchema.fromBean(Movie.class));

        DynamoDbIndex<Movie> genreIndex = table.index("GenreIndex");

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(genre.name()).build());

        List<Movie> results = new ArrayList<>();
        SdkIterable<Page<Movie>> pages = genreIndex.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }
        return results;
    }

    @Override
    public DynamoDbEnhancedClient getEnhancedClient() {
        return enhancedClient;
    }

    @Override
    public List<Movie> findAll() {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = table.scan();

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    @Override
    public Long countMovies() {
        DynamoDbTable<Movie> table = getTable();
        return table.scan()
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

