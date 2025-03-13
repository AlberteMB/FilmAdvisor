package org.vaadin.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.model.DiscardedMovie;

public interface DiscardedMovieRepository extends JpaRepository<DiscardedMovie, String> {
}
