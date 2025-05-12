package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    public List<Movie> getFilteredRandomMovies(@Nullable Movie.Genre genre, List<String> platforms, int numMovies) {
        List<Movie> filtered = new ArrayList<>();

        for (String platform : platforms) {
            List<Movie> result;

            if (genre == null) {
                result = movieRepository.findByPlatform(platform);
            } else {
                result = movieRepository.findByPlatformAndGenre(platform, genre);
            }

            filtered.addAll(result);
        }

        Collections.shuffle(filtered);

        return filtered.stream()
                .limit(Math.min(numMovies, filtered.size()))
                .toList();
    }




}
