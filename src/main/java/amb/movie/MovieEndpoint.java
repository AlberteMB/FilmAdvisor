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

        System.out.println("Parámetros recibidos: platform=" + platforms + ", genres=" + genre + ", count=" + numMovies);

        Set<Movie> uniqueMovies = new HashSet<>();

        for (String platform : platforms) {
            List<Movie> movies = movieRepository.findByPlatformAndGenre(platform, genre);
            uniqueMovies.addAll(movies);
        }

        System.out.println("🎬 Películas filtradas por platform y genre: " + uniqueMovies.size());

        List<Movie> shuffled = new ArrayList<>(uniqueMovies);
        Collections.shuffle(shuffled);

        return shuffled.stream().limit(numMovies).toList();
    }

    // Buscar películas por título (búsqueda parcial)
    public List<Movie> findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    // Buscar películas por actor
    public List<Movie> findByActor(String actor) {
        return movieRepository.findByActor(actor);
    }

    // Buscar películas por director
    public List<Movie> findByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    // Buscar películas por año
    public List<Movie> findByYear(int year) {
        return movieRepository.findByYear(year);
    }

    // Buscar películas por género
    public List<Movie> findByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

    // Buscar películas por rango de años
    public List<Movie> findByYearRange(int startYear, int endYear) {
        return movieRepository.findByYearRange(startYear, endYear);
    }

    // Buscar películas por duración mínima
    public List<Movie> findByMinDuration(int minDuration) {
        return movieRepository.findByMinDuration(minDuration);
    }

    // Buscar películas por rating IMDb mínimo
    public List<Movie> findByMinImdbRating(double minRating) {
        return movieRepository.findByMinImdbRating(minRating);
    }

    // Buscar películas por múltiples criterios
    public List<Movie> searchMovies(@Nullable String title, @Nullable Genre genre, 
                                  @Nullable List<String> platforms, @Nullable Integer year,
                                  @Nullable Integer minDuration, @Nullable Double minImdbRating) {
        return movieRepository.searchMovies(title, genre, platforms, year, minDuration, minImdbRating);
    }

    // Obtener todas las películas
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    // Contar total de películas
    public Long countMovies() {
        return movieRepository.countMovies();
    }

    // Obtener películas más populares (por rating IMDb)
    public List<Movie> getTopRatedMovies(int limit) {
        return movieRepository.getTopRatedMovies(limit);
    }

    // Obtener películas más recientes
    public List<Movie> getLatestMovies(int limit) {
        return movieRepository.getLatestMovies(limit);
    }

    // Obtener películas por plataforma y año
    public List<Movie> findByPlatformAndYear(String platform, int year) {
        return movieRepository.findByPlatformAndYear(platform, year);
    }

    // Obtener películas por plataforma y duración mínima
    public List<Movie> findByPlatformAndMinDuration(String platform, int minDuration) {
        return movieRepository.findByPlatformAndMinDuration(platform, minDuration);
    }

    // Obtener estadísticas de películas por plataforma
    public Map<String, Long> getMovieStatsByPlatform() {
        return movieRepository.getMovieStatsByPlatform();
    }

    // Obtener estadísticas de películas por género
    public Map<Genre, Long> getMovieStatsByGenre() {
        return movieRepository.getMovieStatsByGenre();
    }

    // Obtener estadísticas de películas por año
    public Map<Integer, Long> getMovieStatsByYear() {
        return movieRepository.getMovieStatsByYear();
    }
}
