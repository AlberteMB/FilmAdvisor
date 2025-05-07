package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Movie> findByPlatformAndGenre(String platform, Movie.Genre genre) {
        return movieRepository.findByPlatformAndGenre(platform, genre);
    }

    public List<Movie> getFilteredRandomMovies(Movie.Genre genre, List<String> platforms) {
        // Aquí haces tu lógica de filtrado usando DynamoDB
        List<Movie> byGenre = movieRepository.findByGenre(genre); // Supón que ya existe

        // Filtrar por plataformas
        List<Movie> filtered = byGenre.stream()
                .filter(m -> m.getPlatforms().stream().anyMatch(platforms::contains))
                .collect(Collectors.toList());

        // Barajar y devolver 9 aleatorias
        Collections.shuffle(filtered);
        return filtered.stream().limit(9).toList();
    }



}
