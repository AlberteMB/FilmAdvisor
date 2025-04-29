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




}
