package org.vaadin.example.service;

import org.vaadin.example.model.WatchedMovie;

import java.util.List;

public interface WatchedMovieService {
    List<WatchedMovie> getAllWatchedMovies(String id);
    WatchedMovie createWatchedMovie(WatchedMovie watchedMovie);
    WatchedMovie getWatchedMovieById(String id);
    WatchedMovie updateWatchedMovie(String id, WatchedMovie watchedMovieDetails);
    boolean deleteWatchedMovie(String id);
    long countWatchedMovie(String id);
}
