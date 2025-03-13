package org.vaadin.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
}
