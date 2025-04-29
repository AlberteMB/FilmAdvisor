package amb.discarded;

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
public class DiscardedMovie {
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

    @Override
    public String toString() {
        return "DiscardedMovie{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", movie=" + movie +
                '}';
    }
}