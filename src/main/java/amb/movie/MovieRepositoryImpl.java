package amb.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository  {

    private final DynamoDbEnhancedClient enhancedClient;
    //private final DynamoDbTable<Movie> movieTable;
    private final String tableName = "Movie";

    @Autowired
    public MovieRepositoryImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        //this.movieTable = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

    private DynamoDbTable<Movie> getTable() {
        return enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
    }

     @Override
    public void save(Movie movie) {
        DynamoDbTable<Movie> table = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
        table.putItem(movie);
    }

    @Override
    public List<Movie> findByPlatform(String platform) {
        DynamoDbTable<Movie> table = getTable();

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(platform).build());

        List<Movie> results = new ArrayList<>();

        table.query(r -> r.queryConditional(queryConditional))
                .forEach(page -> results.addAll(page.items()));

        return results;
    }

    @Override
    public List<Movie> findByPlatformAndGenre(String platform, Genre genre) {
        DynamoDbTable<Movie> table = getTable();

        // Usamos el prefix "Genre#"
        String genrePrefix = genre.name() + "#";

        QueryConditional queryConditional = QueryConditional
                .sortBeginsWith(Key.builder()
                        .partitionValue(platform)
                        .sortValue(genrePrefix)
                        .build());

        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = table.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    @Override
    public List<Movie> findByGenre(Genre genre) {
        DynamoDbEnhancedClient enhancedClient = getEnhancedClient();
        DynamoDbTable<Movie> table = enhancedClient.table("Movie", TableSchema.fromBean(Movie.class));

        DynamoDbIndex<Movie> genreIndex = table.index("GenreIndex");

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(genre.name()).build());

        List<Movie> results = new ArrayList<>();
        SdkIterable<Page<Movie>> pages = genreIndex.query(r -> r.queryConditional(queryConditional));

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }
        return results;
    }

    @Override
    public DynamoDbEnhancedClient getEnhancedClient() {
        return enhancedClient;
    }

    @Override
    public List<Movie> findAll() {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();

        SdkIterable<Page<Movie>> pages = table.scan();

        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    @Override
    public Long countMovies() {
        DynamoDbTable<Movie> table = getTable();
        return table.scan()
                .stream()
                .mapToLong(page -> page.items().size())
                .sum();
    }

    @Override
    public List<Movie> findByYear(int year) {
        DynamoDbTable<Movie> table = getTable();
        
        // Usar GSI para buscar por año
        DynamoDbIndex<Movie> yearIndex = table.index("YearIndex");
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(String.valueOf(year)).build());
        
        List<Movie> results = new ArrayList<>();
        SdkIterable<Page<Movie>> pages = yearIndex.query(r -> r.queryConditional(queryConditional));
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByTitle(String title) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por título (búsqueda parcial)
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getTitle() != null && 
                            movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByActor(String actor) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por actor
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getActors() != null && 
                            movie.getActors().stream()
                                    .anyMatch(a -> a.toLowerCase().contains(actor.toLowerCase())))
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByDirector(String director) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por director
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getDirector() != null && 
                            movie.getDirector().toLowerCase().contains(director.toLowerCase()))
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByYearRange(int startYear, int endYear) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por rango de años
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getYear() >= startYear && movie.getYear() <= endYear)
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByMinDuration(int minDuration) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por duración mínima
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getDuration() >= minDuration)
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> findByMinImdbRating(double minRating) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y filtrar por rating IMDb mínimo
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getImdbRating() >= minRating)
                    .toList());
        }
        
        return results;
    }

    @Override
    public List<Movie> searchMovies(String title, Genre genre, List<String> platforms, 
                                  Integer year, Integer minDuration, Double minImdbRating) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y aplicar filtros múltiples
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> {
                        // Filtro por título
                        if (title != null && !title.isEmpty()) {
                            if (movie.getTitle() == null || 
                                !movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                                return false;
                            }
                        }
                        
                        // Filtro por género
                        if (genre != null && movie.getGenre() != genre) {
                            return false;
                        }
                        
                        // Filtro por plataformas
                        if (platforms != null && !platforms.isEmpty()) {
                            if (movie.getPlatforms() == null || 
                                !movie.getPlatforms().stream()
                                    .anyMatch(p -> platforms.contains(p.name()))) {
                                return false;
                            }
                        }
                        
                        // Filtro por año
                        if (year != null && movie.getYear() != year) {
                            return false;
                        }
                        
                        // Filtro por duración mínima
                        if (minDuration != null && movie.getDuration() < minDuration) {
                            return false;
                        }
                        
                        // Filtro por rating IMDb mínimo
                        if (minImdbRating != null && movie.getImdbRating() < minImdbRating) {
                            return false;
                        }
                        
                        return true;
                    })
                    .toList());
        }
        
        return results;
    }

    @Override
    public Optional<Movie> findByMovieId(String movieId) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y buscar por movieId
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items().stream()
                    .filter(movie -> movie.getMovieId() != null && 
                            movie.getMovieId().equals(movieId))
                    .toList());
        }
        
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Movie> getTopRatedMovies(int limit) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y ordenar por rating IMDb
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }
        
        return results.stream()
                .sorted((m1, m2) -> Double.compare(m2.getImdbRating(), m1.getImdbRating()))
                .limit(limit)
                .toList();
    }

    @Override
    public List<Movie> getLatestMovies(int limit) {
        DynamoDbTable<Movie> table = getTable();
        List<Movie> results = new ArrayList<>();
        
        // Escanear todas las películas y ordenar por año
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            results.addAll(page.items());
        }
        
        return results.stream()
                .sorted((m1, m2) -> Integer.compare(m2.getYear(), m1.getYear()))
                .limit(limit)
                .toList();
    }

    @Override
    public List<Movie> findByPlatformAndYear(String platform, int year) {
        DynamoDbTable<Movie> table = getTable();
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(platform).build());
        
        List<Movie> results = new ArrayList<>();
        
        table.query(r -> r.queryConditional(queryConditional))
                .forEach(page -> results.addAll(page.items().stream()
                        .filter(movie -> movie.getYear() == year)
                        .toList()));
        
        return results;
    }

    @Override
    public List<Movie> findByPlatformAndMinDuration(String platform, int minDuration) {
        DynamoDbTable<Movie> table = getTable();
        
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(platform).build());
        
        List<Movie> results = new ArrayList<>();
        
        table.query(r -> r.queryConditional(queryConditional))
                .forEach(page -> results.addAll(page.items().stream()
                        .filter(movie -> movie.getDuration() >= minDuration)
                        .toList()));
        
        return results;
    }

    @Override
    public Map<String, Long> getMovieStatsByPlatform() {
        DynamoDbTable<Movie> table = getTable();
        Map<String, Long> stats = new HashMap<>();
        
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            for (Movie movie : page.items()) {
                if (movie.getPlatforms() != null) {
                    for (Movie.Platform platform : movie.getPlatforms()) {
                        stats.put(platform.name(), stats.getOrDefault(platform.name(), 0L) + 1);
                    }
                }
            }
        }
        
        return stats;
    }

    @Override
    public Map<Genre, Long> getMovieStatsByGenre() {
        DynamoDbTable<Movie> table = getTable();
        Map<Genre, Long> stats = new HashMap<>();
        
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            for (Movie movie : page.items()) {
                if (movie.getGenre() != null) {
                    stats.put(movie.getGenre(), stats.getOrDefault(movie.getGenre(), 0L) + 1);
                }
            }
        }
        
        return stats;
    }

    @Override
    public Map<Integer, Long> getMovieStatsByYear() {
        DynamoDbTable<Movie> table = getTable();
        Map<Integer, Long> stats = new HashMap<>();
        
        SdkIterable<Page<Movie>> pages = table.scan();
        
        for (Page<Movie> page : pages) {
            for (Movie movie : page.items()) {
                stats.put(movie.getYear(), stats.getOrDefault(movie.getYear(), 0L) + 1);
            }
        }
        
        return stats;
    }

}

