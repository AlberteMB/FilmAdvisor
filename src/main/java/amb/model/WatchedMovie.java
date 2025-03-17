package amb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

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