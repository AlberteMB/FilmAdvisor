package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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

    public List<Movie> findByPlatformAndGenre(String platform, Genre genre) {
        return movieRepository.findByPlatformAndGenre(platform, genre);
    }

    public List<Movie> getFilteredRandomMovies(@Nullable Genre genre, List<String> platforms, int numMovies) {
        Set<Movie> uniqueMovies = new HashSet<>();

        for (String platform : platforms) {
            List<Movie> result;

            if (genre == null) {
                result = movieRepository.findByPlatform(platform);
            } else {
                result = movieRepository.findByPlatformAndGenre(platform, genre);
            }

            uniqueMovies.addAll(result);
        }
        List<Movie> shuffledMovies = new ArrayList<>(uniqueMovies);
        Collections.shuffle(shuffledMovies);

        return shuffledMovies.stream()
                .limit(Math.min(numMovies, shuffledMovies.size()))
                .toList();
    }




}
