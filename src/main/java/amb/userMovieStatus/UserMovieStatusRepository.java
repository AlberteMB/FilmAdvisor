package amb.userMovieStatus;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.util.List;
import java.util.Optional;

public interface UserMovieStatusRepository {

    void save(UserMovieStatus userMovieStatus);

    Optional<UserMovieStatus> findByUserAndMovie(String userId, String movieId);

    List<UserMovieStatus> findByUserId(String userId);

    List<UserMovieStatus> findByUserIdAndStatus(String userId, UserMovieStatus.Status status);

    List<UserMovieStatus> findByUserIdAndRating(String userId, UserMovieStatus.Rating rating);

    List<UserMovieStatus> findByMovieId(String movieId);

    List<UserMovieStatus> findByStatus(UserMovieStatus.Status status);

    List<UserMovieStatus> findByRating(UserMovieStatus.Rating rating);

    void deleteByUserAndMovie(String userId, String movieId);

    void deleteByUserId(String userId);

    boolean existsByUserAndMovie(String userId, String movieId);

    Long countByUserId(String userId);

    Long countByUserIdAndStatus(String userId, UserMovieStatus.Status status);

    Long countByMovieId(String movieId);

    DynamoDbEnhancedClient getEnhancedClient();
}
