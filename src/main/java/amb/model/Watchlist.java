package amb.model;

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
public class Watchlist {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
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

}