import { useState } from "react";
import { Grid, Container } from "@mui/material";
import moviesData from "../data/movies.json";
import MovieCard from "../components/MovieCard";
import { Movie } from "../model/Movie";
import { MovieEndpoint } from 'Frontend/generated/endpoints';
import { useFilterContext } from "../context/FilterContext";

const movies: Movie[] = moviesData;

const MoviesSelector = () => {
    const { selectedGenres, selectedPlatforms } = useFilterContext();
    const [selectedMovies, setSelectedMovies] = useState<Movie[]>([]);
    const [numMovies, setNumMovies] = useState<number>(3);

    const handleSubmit = async () => {
        if (selectedPlatforms.length === 0) {
            alert("Selecciona al menos  una plataforma.");
            return;
          }

        const movies = await MovieEndpoint.getFilteredRandomMovies(
            selectedGenre.toUpperCase(),
            selectedPlatforms
        );
        setSelectedMovies(movies);
    );

  return (
    <div style={{ textAlign: "center", marginTop: "20px" }}>
      {/* Quantity selector*/}
      <label>
        Mostrar:
        <select
          value={numMovies}
          onChange={(e) => setNumMovies(Number(e.target.value))}
          style={{ margin: "10px" }}
        >
          <option value={3}>3 películas</option>
          <option value={6}>6 películas</option>
          <option value={9}>9 películas</option>
        </select>
      </label>

      {/* Button to get random movies */}
      <<button onClick={handleSubmit} style={{ padding: "10px" }}>
               Obtener películas
             </button>

      {/* Container with movies */}
      <Container maxWidth="md" style={{ marginTop: "20px" }}>
        <Grid container spacing={2}>
          {selectedMovies.map((movie) => (
            <Grid item xs={12} sm={6} md={4} key={movie.id}>
              <MovieCard movie={movie} />
            </Grid>
          ))}
        </Grid>
      </Container>
    </div>
  );
};

export default MoviesSelector;

