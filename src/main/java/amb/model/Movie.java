package amb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Min(1900)
    @Max(2025)
    private int year;

    public enum Genre {
        ACTION, ADVENTURE, SCI_FI, COMEDY, DRAMA, FANTASY, HORROR, ROMANCE,
        THRILLER, CRIME, DOCUMENTARY, ANIMATION, FAMILY, HISTORICAL, WAR,
        WESTERN, SPORTS, BIOPIC, MYSTERY, MUSICAL, SERIES, ANIME, LGBT;
    }
    public enum Rating{
        G, PG, PG_13, R
    }

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Enumerated(EnumType.STRING) // Guarda el enum como String en la BD
    @Column(name = "genre")
    private List<Genre> genres = new ArrayList<>();

    private String director;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @ElementCollection
    private List<String> actors = new ArrayList<>();;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REFRESH)
    private List<WatchedMovie> watchedMovies = new ArrayList<>();

    //@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<WatchlistEntry> watchlistEntries = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REFRESH)
    private List<DiscardedMovie> discardedMovies = new ArrayList<>();

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genres=" + genres +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", actors=" + actors +
                '}';
    }


}
