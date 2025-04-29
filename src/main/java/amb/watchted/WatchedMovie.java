package amb.watchted;

import amb.movie.Movie;
import amb.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class WatchedMovie {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private boolean liked; // true = "me gusta", false = "no me gusta"

    @Override
    public String toString() {
        return "WatchedMovie{" +
                "id=" + id +
                ", user=" + user +
                ", movie=" + movie +
                ", liked=" + liked +
                '}';
    }
}