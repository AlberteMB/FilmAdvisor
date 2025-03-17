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