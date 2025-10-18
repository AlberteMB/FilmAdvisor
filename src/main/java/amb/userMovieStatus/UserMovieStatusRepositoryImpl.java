package amb.userMovieStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserMovieStatusRepositoryImpl implements UserMovieStatusRepository {

    private final DynamoDbEnhancedClient enhancedClient;
    private final String tableName = "UserMovieStatus";

    @Autowired
    public UserMovieStatusRepositoryImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    private DynamoDbTable<UserMovieStatus> getTable() {
        return enhancedClient.table(tableName, TableSchema.fromBean(UserMovieStatus.class));
    }

    @Override
    public void save(UserMovieStatus userMovieStatus) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        table.putItem(userMovieStatus);
    }

    @Override
    public Optional<UserMovieStatus> findByUserAndMovie(String userId, String movieId) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        String pk = "USER#" + userId;
        String sk = "MOVIE#" + movieId;
        
        UserMovieStatus result = table.getItem(Key.builder()
                .partitionValue(pk)
                .sortValue(sk)
                .build());
        
        return Optional.ofNullable(result);
    }

    @Override
    public List<UserMovieStatus> findByUserId(String userId) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        String pk = "USER#" + userId;
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(pk).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        
        SdkIterable<Page<UserMovieStatus>> pages = table.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            results.addAll(page.items());
        }
        
        return results;
    }

    @Override
    public List<UserMovieStatus> findByUserIdAndStatus(String userId, UserMovieStatus.Status status) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        // Usar GSI para buscar por status
        DynamoDbIndex<UserMovieStatus> statusIndex = table.index("StatusIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(status.name()).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        SdkIterable<Page<UserMovieStatus>> pages = statusIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            // Filtrar por userId ya que el GSI no incluye userId en la partition key
            results.addAll(page.items().stream()
                    .filter(item -> item.getUserId().equals(userId))
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<UserMovieStatus> findByUserIdAndRating(String userId, UserMovieStatus.Rating rating) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        // Usar GSI para buscar por rating
        DynamoDbIndex<UserMovieStatus> ratingIndex = table.index("RatingIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(rating.name()).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        SdkIterable<Page<UserMovieStatus>> pages = ratingIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            // Filtrar por userId ya que el GSI no incluye userId en la partition key
            results.addAll(page.items().stream()
                    .filter(item -> item.getUserId().equals(userId))
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<UserMovieStatus> findByMovieId(String movieId) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        // Usar GSI para buscar por movieId
        DynamoDbIndex<UserMovieStatus> movieIndex = table.index("MovieIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(movieId).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        SdkIterable<Page<UserMovieStatus>> pages = movieIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            results.addAll(page.items());
        }
        
        return results;
    }

    @Override
    public List<UserMovieStatus> findByStatus(UserMovieStatus.Status status) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        DynamoDbIndex<UserMovieStatus> statusIndex = table.index("StatusIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(status.name()).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        SdkIterable<Page<UserMovieStatus>> pages = statusIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            results.addAll(page.items());
        }
        
        return results;
    }

    @Override
    public List<UserMovieStatus> findByRating(UserMovieStatus.Rating rating) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        DynamoDbIndex<UserMovieStatus> ratingIndex = table.index("RatingIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(rating.name()).build());
        
        List<UserMovieStatus> results = new ArrayList<>();
        SdkIterable<Page<UserMovieStatus>> pages = ratingIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<UserMovieStatus> page : pages) {
            results.addAll(page.items());
        }
        
        return results;
    }

    @Override
    public void deleteByUserAndMovie(String userId, String movieId) {
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        String pk = "USER#" + userId;
        String sk = "MOVIE#" + movieId;
        
        table.deleteItem(Key.builder()
                .partitionValue(pk)
                .sortValue(sk)
                .build());
    }

    @Override
    public void deleteByUserId(String userId) {
        List<UserMovieStatus> userStatuses = findByUserId(userId);
        DynamoDbTable<UserMovieStatus> table = getTable();
        
        for (UserMovieStatus status : userStatuses) {
            table.deleteItem(Key.builder()
                    .partitionValue(status.getPk())
                    .sortValue(status.getSk())
                    .build());
        }
    }

    @Override
    public boolean existsByUserAndMovie(String userId, String movieId) {
        return findByUserAndMovie(userId, movieId).isPresent();
    }

    @Override
    public Long countByUserId(String userId) {
        return (long) findByUserId(userId).size();
    }

    @Override
    public Long countByUserIdAndStatus(String userId, UserMovieStatus.Status status) {
        return (long) findByUserIdAndStatus(userId, status).size();
    }

    @Override
    public Long countByMovieId(String movieId) {
        return (long) findByMovieId(movieId).size();
    }

    @Override
    public DynamoDbEnhancedClient getEnhancedClient() {
        return enhancedClient;
    }
}


