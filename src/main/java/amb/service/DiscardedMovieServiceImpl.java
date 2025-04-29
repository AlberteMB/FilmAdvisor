package amb.service;

import org.springframework.beans.factory.annotation.Autowired;
import amb.discarded.DiscardedMovie;
import amb.discarded.DiscardedMovieRepository;

import java.util.List;
import java.util.Optional;

public class DiscardedMovieServiceImpl implements DiscardedMovieService {

    @Autowired
    private DiscardedMovieRepository discardedMovieRepository;

    @Override
    public List<DiscardedMovie> getAllDiscardedMovies() {
        return discardedMovieRepository.findAll();
    }

    @Override
    public DiscardedMovie createDiscardedMovie(DiscardedMovie discardedMovie) {
        return discardedMovieRepository.save(discardedMovie);
    }

    @Override
    public DiscardedMovie getDiscardedMovieById(String id) {
        return discardedMovieRepository.findById(id).orElse(null);
    }

    @Override
    public DiscardedMovie updateDiscardedMovie(String id, DiscardedMovie discardedMovieDetails) {
        Optional<DiscardedMovie> discardedMovie = discardedMovieRepository.findById(id);
        if(discardedMovie.isPresent()){
            discardedMovie.get().setUser(discardedMovieDetails.getUser());
            discardedMovie.get().setMovie(discardedMovieDetails.getMovie());
            return discardedMovieRepository.save(discardedMovie.get());
        }
        return null;
    }

    @Override
    public boolean deleteDiscardedMovie(String id) {
        discardedMovieRepository.deleteById(id);
        Optional<DiscardedMovie> discardedMovie = discardedMovieRepository.findById(id);
        return  discardedMovie.isPresent();
    }

    @Override
    public Long countDiscardedMovie() {
        return discardedMovieRepository.count();
    }

}
