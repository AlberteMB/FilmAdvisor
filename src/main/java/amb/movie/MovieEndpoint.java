package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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


}
