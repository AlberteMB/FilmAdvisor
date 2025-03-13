package org.vaadin.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
