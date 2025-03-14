package org.vaadin.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

    @ManyToMany(mappedBy = "watchlists", cascade = CascadeType.ALL,
             fetch = FetchType.EAGER)
    private List<Movie> movies;

    @Override
   public String toString() {
        return "Watchlist{" +
                "id=" + id +
                ", movies=" + movies +
                '}';
    }
}