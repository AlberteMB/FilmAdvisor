import amb.movie.Movie;
import amb.user.User;
import amb.user.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class EntityRelationshipsTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WatchlistRepository watchlistRepository;

    private Validator validator;
    private User user;
    private Movie movie;
    private Watchlist watchlist;
    private WatchlistEntry watchlistEntry;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("ValidPass1!");
        user.setRole(User.Role.USER);

        movie = new Movie();
        movie.setId(UUID.randomUUID().toString());
        movie.setTitle("Test Movie");
        movie.setYear(2020);
        movie.setGenres(List.of(Movie.Genre.ACTION));

        watchlist = new Watchlist();
        watchlist.setId(UUID.randomUUID().toString());
        watchlist.setUser(user);
        user.setWatchlist(watchlist);

        watchlistEntry = new WatchlistEntry();
        watchlistEntry.setId(UUID.randomUUID().toString());
        watchlistEntry.setWatchlist(watchlist);
        watchlistEntry.setMovie(movie);

    }

    @Test
    void testUserValidation() {
        User invalidUser = new User();
        invalidUser.setUsername("");
        invalidUser.setEmail("invalidemail.com");
        invalidUser.setPassword("123");
        invalidUser.setRole(User.Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(invalidUser);
        assertFalse(violations.isEmpty(), "El usuario no válido debería generar violaciones de validación");
    }

    @Test
    void testMovieValidation() {
        Movie invalidMovie = new Movie();
        invalidMovie.setTitle("");
        invalidMovie.setYear(1800);
        invalidMovie.setGenres(List.of());

        Set<ConstraintViolation<Movie>> violations = validator.validate(invalidMovie);
        assertFalse(violations.isEmpty(), "La película no válida debería generar violaciones de validación");
    }

    @Test
    void testUserMovieRelationships() {
        assertEquals(user, watchlist.getUser(), "La lista de seguimiento debería estar asociada al usuario");
        assertEquals(watchlist, user.getWatchlist(), "El usuario debería tener su lista de seguimiento");
    }

    @Test
    void testCascadeDeleteWatchlist() {
        user.setWatchlist(null);
        assertNull(watchlist.getUser(), "La relación debería eliminarse correctamente");
    }

    @Test
    void testDuplicateMovieInWatchlist() {
        watchlist.addMovie(movie); // Primera adición

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            watchlist.addMovie(movie); // Intento de añadir duplicado
        });

        assertEquals("La película ya está en la watchlist.", exception.getMessage());
    }

    @Test
    void testInvalidMovieYear() {
        Movie futureMovie = new Movie();
        futureMovie.setTitle("Future Movie");
        futureMovie.setYear(2099);
        futureMovie.setGenres(List.of(Movie.Genre.ACTION));

        Set<ConstraintViolation<Movie>> violations = validator.validate(futureMovie);
        assertFalse(violations.isEmpty(), "No debería permitirse un año inválido");
    }
}