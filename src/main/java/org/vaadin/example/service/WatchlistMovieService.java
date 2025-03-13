package org.vaadin.example.service;

import org.vaadin.example.model.WatchlistMovie;

import java.util.List;

public interface WatchlistMovieService {

    List<WatchlistMovie> getAllWatchlistMovies(String id);
    WatchlistMovie createWatchlistMovie(WatchlistMovie watchlistMovie);
    WatchlistMovie getWatchlistMovieById(String id);
    WatchlistMovie updateWatchlistMovie(String id, WatchlistMovie watchlistMovieDetails);
    boolean deleteWatchlistMovie(String id);
    long countWatchlistMovie(String id);
}
