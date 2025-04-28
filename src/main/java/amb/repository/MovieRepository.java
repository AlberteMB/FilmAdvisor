package amb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import amb.model.Movie;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Key key = Key.builder()
                .partitionValue(pk
                .sortValue(sk)
                .build();
        Movie movie = movieTable.getItem(r -> r.key(key));
        return Optional.ofNullable(movie);
    }

    public List<Movie> findByYear(int year) {
        DynamoDbIndex<Movie> yearIndex = movieTable.index("YearIndex");

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(year).build());

        List<Movie> results = new ArrayList<>();
        yearIndex.query(queryConditional)
                .items()
                .forEach(results::add);

        return results;
    }

    public List<Movie> findByReleasedDate(String releasedDate) {
        DynamoDbIndex<Movie> releasedDateIndex = movieTable.index("ReleasedDateIndex");

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(releasedDate).build());

        List<Movie> results = new ArrayList<>();
        releasedDateIndex.query(queryConditional)
                .items()
                .forEach(results::add);

        return results;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        movieTable.scan().items().forEach(movies::add);
        return movies;
    }

    // Delete a movie
    public void delete(String pk, String sk) {
        Key key = Key.builder()
                .partitionValue(pk)
                .sortValue(sk)
                .build();
        movieTable.deleteItem(DeleteItemEnhancedRequest.builder().key(key).build());
    }

    // Count movies
    public long count() {
        return movieTable.scan().items().stream().count();
    }

    public List<Movie> findByGenre(String genre) {
        // Construir la KeyCondition para buscar todas las películas que tengan "#GENRE"
        String genreSuffix = "#" + genre.toUpperCase(); // ejemplo: "#ACTION"

        // Hacemos un query usando una condición "begins_with"
        QueryConditional queryConditional = QueryConditional.sortBeginsWith(b ->
                b.partitionValue(genreSuffix)
        );

        return movieTable.query(r -> r.queryConditional(queryConditional))
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public List<Movie> findByReleasedDateAfter(LocalDate sinceDate) {
        // Hay que usar el GSI de ReleasedDate
        String sinceDateString = sinceDate.toString(); // "2025-04-28"

        return movieTable.index("ReleasedDateIndex") // O el nombre real de tu GSI
                .query(r -> r.queryConditional(
                                QueryConditional.sortGreaterThanOrEqualTo(b ->
                                        b.partitionValue("RELEASED_DATE")
                                                .sortValue(sinceDateString)
                                )
                        )
                )
                .items()
                .stream()
                .collect(Collectors.toList());
    }

}
