import { useState } from "react";
import { Grid, Container } from "@mui/material";
import moviesData from "../data/movies.json";
import MovieCard from "../components/MovieCard";
import { Movie } from "../model/Movie";

const movies: Movie[] = moviesData;

export default function MoviesRandomizer() {
  // State for selectedMovies
  const [selectedMovies, setSelectedMovies] = useState<Movie[]>([]);
  // State for numMovies
  const [numMovies, setNumMovies] = useState<number>(3);

  // Get function to get random movies
  const getRandomMovies = (count: number) => {
    const shuffled = [...moviesData].sort(() => Math.random() - 0.5); // Mix array
    setSelectedMovies(shuffled.slice(0, count)); // Take the first "count" movies
  };

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
      <button
        onClick={() => getRandomMovies(numMovies)}
        style={{ padding: "10px", cursor: "pointer" }}
      >
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
}

