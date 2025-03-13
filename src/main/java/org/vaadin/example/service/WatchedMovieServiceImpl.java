package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.model.WatchedMovie;
import org.vaadin.example.repository.WatchedMovieRepository;

import java.util.List;

public class WatchedMovieServiceImpl implements WatchedMovieService {

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Override
    public List<WatchedMovie> getAllWatchedMovies(String id) {
        return List.of();
    }

    @Override
    public WatchedMovie createWatchedMovie(WatchedMovie watchedMovie) {
        return null;
    }

    @Override
    public WatchedMovie getWatchedMovieById(String id) {
        return null;
    }

    @Override
    public WatchedMovie updateWatchedMovie(String id, WatchedMovie watchedMovieDetails) {
        return null;
    }

    @Override
    public boolean deleteWatchedMovie(String id) {
        return false;
    }

    @Override
    public long countWatchedMovie(String id) {
        return 0;
    }
}
