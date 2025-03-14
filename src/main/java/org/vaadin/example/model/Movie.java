package org.vaadin.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JoinTable(name = "MOVIE_WATCHLIST_JOIN_TABLE",
            joinColumns = @JoinColumn(name = "MOVIE_FK"),
            inverseJoinColumns = @JoinColumn(name = "WATCHLIST_FK"))
    private List<Watchlist> watchlists = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscardedMovie> discardedMovies = new ArrayList<>();

    @Override
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
