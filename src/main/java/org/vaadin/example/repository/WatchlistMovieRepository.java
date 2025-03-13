package org.vaadin.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.model.WatchlistMovie;

public interface WatchlistMovieRepository extends JpaRepository<WatchlistMovie, String> {
}
