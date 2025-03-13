package org.vaadin.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

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
    private String title;

    private int year;

    @ElementCollection
    private List<String> genres;

    private String director;

    @ElementCollection
    private List<String> actors;

    //@OneToOne(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    //private MovieDetails details;

    //@OneToOne(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    //private StreamingAvailability streamingAvailability;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchedMovie> watchedMovies;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchlistMovie> watchlistMovies;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscardedMovie> discardedMovies;

   public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genres=" + genres +
                ", director='" + director + '\'' +
                ", actors=" + actors +
                '}';
    }

}
