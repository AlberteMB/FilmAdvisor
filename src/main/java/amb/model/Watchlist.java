package amb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Watchlist {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "watchlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchlistEntry> watchlistEntries = new ArrayList<>();

    @Override
    public String toString() {
       return "Watchlist{" +
               "id=" + id +
               ", user=" + user +
               ", watchlistEntries=" + watchlistEntries +
               '}';
   }

    public void addMovie(Movie movie) {
        if (movie == null || movie.getId() == null) {
            throw new IllegalArgumentException("La película no puede ser nula y debe tener un ID.");
        }

        if (watchlistEntries == null) {
            watchlistEntries = new ArrayList<>();
        }

        boolean alreadyExists = watchlistEntries.stream()
                .map(WatchlistEntry::getMovie)
                .filter(Objects::nonNull) // Evitar posibles NPEs
                .anyMatch(m -> m.getId().equals(movie.getId()));

        if (alreadyExists) {
            throw new IllegalArgumentException("La película ya está en la watchlist.");
        }

        WatchlistEntry entry = new WatchlistEntry(this, movie);
        watchlistEntries.add(entry);
    }

    public void removeMovie(Movie movie) {
        if (movie == null || movie.getId() == null) {
            throw new IllegalArgumentException("La película no puede ser nula y debe tener un ID.");
        }

        watchlistEntries.removeIf(entry -> entry.getMovie().getId().equals(movie.getId()));
    }

}