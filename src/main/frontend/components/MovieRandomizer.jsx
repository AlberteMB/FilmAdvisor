import { useState } from "react";
import { Grid, Container } from "@mui/material"; // Asegúrate de importar los componentes necesarios
import moviesData from "../data/movies.json";
import MovieCard from "../components/MovieCard";

export default function MoviesRandomizer() {
  const [selectedMovies, setSelectedMovies] = useState([]);
  const [numMovies, setNumMovies] = useState(3); // Valor inicial

  // Función para obtener películas aleatorias
  const getRandomMovies = (count) => {
    const shuffled = [...moviesData].sort(() => Math.random() - 0.5); // Mezcla el array
    setSelectedMovies(shuffled.slice(0, count)); // Toma las primeras 'count' películas
  };

  return (
    <div style={{ textAlign: "center", marginTop: "20px" }}>
      {/* Selector de cantidad */}
      <label>
        Mostrar:
        <select value={numMovies} onChange={(e) => setNumMovies(Number(e.target.value))} style={{ margin: "10px" }}>
          <option value={3}>3 películas</option>
          <option value={6}>6 películas</option>
          <option value={9}>9 películas</option>
        </select>
      </label>

      {/* Botón para obtener películas aleatorias */}
      <button onClick={() => getRandomMovies(numMovies)} style={{ padding: "10px", cursor: "pointer" }}>
        Obtener películas
      </button>

      {/* Contenedor de películas */}
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
