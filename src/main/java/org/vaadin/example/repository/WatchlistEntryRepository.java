package org.vaadin.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.model.WatchlistEntry;

public interface WatchlistEntryRepository extends JpaRepository<WatchlistEntry, String> {
}
