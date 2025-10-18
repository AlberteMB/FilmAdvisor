package amb.userMovieStatus;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class UserMovieStatusEndpoint {

    private final UserMovieStatusRepository userMovieStatusRepository;

    @Autowired
    public UserMovieStatusEndpoint(UserMovieStatusRepository userMovieStatusRepository) {
        this.userMovieStatusRepository = userMovieStatusRepository;
    }

    // Marcar película como vista
    public UserMovieStatus markAsWatched(String userId, String movieId, String title, int year) {
        UserMovieStatus userMovieStatus = new UserMovieStatus();
        userMovieStatus.setPk("USER#" + userId);
        userMovieStatus.setSk("MOVIE#" + movieId);
        userMovieStatus.setUserId(userId);
        userMovieStatus.setMovieId(movieId);
        userMovieStatus.setTitle(title);
        userMovieStatus.setYear(year);
        userMovieStatus.setStatus(UserMovieStatus.Status.WATCHED);
        userMovieStatus.setTimestamp(Instant.now());
        
        userMovieStatusRepository.save(userMovieStatus);
        return userMovieStatus;
    }

    // Marcar película como descartada
    public UserMovieStatus markAsDiscarded(String userId, String movieId, String title, int year) {
        UserMovieStatus userMovieStatus = new UserMovieStatus();
        userMovieStatus.setPk("USER#" + userId);
        userMovieStatus.setSk("MOVIE#" + movieId);
        userMovieStatus.setUserId(userId);
        userMovieStatus.setMovieId(movieId);
        userMovieStatus.setTitle(title);
        userMovieStatus.setYear(year);
        userMovieStatus.setStatus(UserMovieStatus.Status.DISCARDED);
        userMovieStatus.setTimestamp(Instant.now());
        
        userMovieStatusRepository.save(userMovieStatus);
        return userMovieStatus;
    }

    // Dar rating a una película vista
    public UserMovieStatus rateMovie(String userId, String movieId, UserMovieStatus.Rating rating) {
        Optional<UserMovieStatus> existingStatus = userMovieStatusRepository.findByUserAndMovie(userId, movieId);
        
        if (existingStatus.isEmpty()) {
            throw new RuntimeException("Movie status not found. Please mark the movie as watched first.");
        }
        
        UserMovieStatus userMovieStatus = existingStatus.get();
        userMovieStatus.setRating(rating);
        userMovieStatus.setTimestamp(Instant.now());
        
        userMovieStatusRepository.save(userMovieStatus);
        return userMovieStatus;
    }

    // Obtener estado de una película específica para un usuario
    public Optional<UserMovieStatus> getMovieStatus(String userId, String movieId) {
        return userMovieStatusRepository.findByUserAndMovie(userId, movieId);
    }

    // Obtener todas las películas de un usuario
    public List<UserMovieStatus> getUserMovies(String userId) {
        return userMovieStatusRepository.findByUserId(userId);
    }

    // Obtener películas vistas por un usuario
    public List<UserMovieStatus> getWatchedMovies(String userId) {
        return userMovieStatusRepository.findByUserIdAndStatus(userId, UserMovieStatus.Status.WATCHED);
    }

    // Obtener películas descartadas por un usuario
    public List<UserMovieStatus> getDiscardedMovies(String userId) {
        return userMovieStatusRepository.findByUserIdAndStatus(userId, UserMovieStatus.Status.DISCARDED);
    }

    // Obtener películas que le gustaron a un usuario
    public List<UserMovieStatus> getLikedMovies(String userId) {
        return userMovieStatusRepository.findByUserIdAndRating(userId, UserMovieStatus.Rating.LIKE);
    }

    // Obtener películas que no le gustaron a un usuario
    public List<UserMovieStatus> getDislikedMovies(String userId) {
        return userMovieStatusRepository.findByUserIdAndRating(userId, UserMovieStatus.Rating.DISLIKE);
    }

    // Obtener estadísticas de un usuario
    public UserMovieStats getUserStats(String userId) {
        Long totalMovies = userMovieStatusRepository.countByUserId(userId);
        Long watchedMovies = userMovieStatusRepository.countByUserIdAndStatus(userId, UserMovieStatus.Status.WATCHED);
        Long discardedMovies = userMovieStatusRepository.countByUserIdAndStatus(userId, UserMovieStatus.Status.DISCARDED);
        Long likedMovies = (long) userMovieStatusRepository.findByUserIdAndRating(userId, UserMovieStatus.Rating.LIKE).size();
        Long dislikedMovies = (long) userMovieStatusRepository.findByUserIdAndRating(userId, UserMovieStatus.Rating.DISLIKE).size();
        
        return new UserMovieStats(totalMovies, watchedMovies, discardedMovies, likedMovies, dislikedMovies);
    }

    // Eliminar estado de una película
    public void removeMovieStatus(String userId, String movieId) {
        userMovieStatusRepository.deleteByUserAndMovie(userId, movieId);
    }

    // Eliminar todos los estados de un usuario
    public void removeAllUserStatuses(String userId) {
        userMovieStatusRepository.deleteByUserId(userId);
    }

    // Verificar si una película ya fue marcada por el usuario
    public boolean hasUserSeenMovie(String userId, String movieId) {
        return userMovieStatusRepository.existsByUserAndMovie(userId, movieId);
    }

    // Obtener estadísticas de una película específica
    public MovieStats getMovieStats(String movieId) {
        List<UserMovieStatus> movieStatuses = userMovieStatusRepository.findByMovieId(movieId);
        
        long totalViews = movieStatuses.size();
        long likes = movieStatuses.stream().mapToLong(status -> status.getRating() == UserMovieStatus.Rating.LIKE ? 1 : 0).sum();
        long dislikes = movieStatuses.stream().mapToLong(status -> status.getRating() == UserMovieStatus.Rating.DISLIKE ? 1 : 0).sum();
        
        return new MovieStats(totalViews, likes, dislikes);
    }

    // Clase interna para estadísticas de usuario
    public static class UserMovieStats {
        private final Long totalMovies;
        private final Long watchedMovies;
        private final Long discardedMovies;
        private final Long likedMovies;
        private final Long dislikedMovies;

        public UserMovieStats(Long totalMovies, Long watchedMovies, Long discardedMovies, Long likedMovies, Long dislikedMovies) {
            this.totalMovies = totalMovies;
            this.watchedMovies = watchedMovies;
            this.discardedMovies = discardedMovies;
            this.likedMovies = likedMovies;
            this.dislikedMovies = dislikedMovies;
        }

        // Getters
        public Long getTotalMovies() { return totalMovies; }
        public Long getWatchedMovies() { return watchedMovies; }
        public Long getDiscardedMovies() { return discardedMovies; }
        public Long getLikedMovies() { return likedMovies; }
        public Long getDislikedMovies() { return dislikedMovies; }
    }

    // Clase interna para estadísticas de película
    public static class MovieStats {
        private final Long totalViews;
        private final Long likes;
        private final Long dislikes;

        public MovieStats(Long totalViews, Long likes, Long dislikes) {
            this.totalViews = totalViews;
            this.likes = likes;
            this.dislikes = dislikes;
        }

        // Getters
        public Long getTotalViews() { return totalViews; }
        public Long getLikes() { return likes; }
        public Long getDislikes() { return dislikes; }
    }
}
