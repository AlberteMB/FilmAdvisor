import amb.movie.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilteredRandomMoviesTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieEndpoint movieEndpoint;

    @BeforeEach
    void setUp() {
        movieEndpoint = new MovieEndpoint(movieRepository);
    }

    @Test
    void testGetFilteredRandomMovies_removesDuplicatesById() {
        // Arrange
        Genre genre = Genre.ACTION;
        List<String> platforms = List.of("Netflix", "Amazon");
        int numMovies = 5;

        Movie movie1 = createMovie(UUID.randomUUID(), "Inception", genre);
        Movie movie2 = createMovie(UUID.randomUUID(), "The Matrix", genre);
        Movie duplicateOfMovie1 = createMovie(movie1.getId(), "Inception", genre); // Misma ID

        // Simula respuestas del repositorio
        when(movieRepository.findByPlatformAndGenre("Netflix", genre))
                .thenReturn(List.of(movie1, movie2));
        when(movieRepository.findByPlatformAndGenre("Amazon", genre))
                .thenReturn(List.of(duplicateOfMovie1));

        // Act
        List<Movie> result = movieEndpoint.getFilteredRandomMovies(genre, platforms, numMovies);

        // Assert
        assertEquals(2, result.size()); // No debe haber duplicados
        Set<UUID> uniqueIds = result.stream().map(Movie::getId).collect(Collectors.toSet());
        assertEquals(result.size(), uniqueIds.size(), "Debe haber películas con IDs únicos");

        System.out.println("🎬 Películas devueltas por getFilteredRandomMovies:");
        result.forEach(movie -> System.out.println("• " + movie.getTitle() + " (" + movie.getGenre() + ")"));


    }

    private Movie createMovie(UUID id, String title, Genre genre) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(2010);
        movie.setMovieId(movie.generateMovieId());
        return movie;
    }

    @Test
    void findByPlatformAndGenre_returnsExistingMovies() {
        // Arrange
        String platform = "Netflix";
        Genre genre = Genre.ACTION;

        // Act
        List<Movie> movies = movieRepository.findByPlatformAndGenre(platform, genre);

        System.out.println("Películas encontradas:");
        movies.forEach(m -> System.out.println(m.getTitle() + " - " + m.getGenre()));

        // Assert
        Assertions.assertNotNull(movies, "La lista de películas no debe ser nula");
        Assertions.assertFalse(movies.isEmpty(), "Debe haber al menos una película para la plataforma y género indicados");
        Assertions.assertTrue(movies.stream().allMatch(m -> platform.equals(m.getPlatform())),
                "Todas las películas deben ser de la plataforma indicada");
        Assertions.assertTrue(movies.stream().allMatch(m -> genre.equals(m.getGenre())),
                "Todas las películas deben ser del género indicado");

        // Print movies
        movies.forEach(m -> System.out.println(m.getTitle() + " (" + m.getYear() + ")"));
    }


}
