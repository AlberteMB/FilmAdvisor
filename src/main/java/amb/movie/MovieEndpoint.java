package amb.movie;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

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


}
