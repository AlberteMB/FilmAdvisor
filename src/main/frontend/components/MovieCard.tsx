import { Card, CardContent, Typography, Box } from "@mui/material";
//import { Movie } from "../model/Movie";
import Movie from "Frontend/generated/amb/movie/Movie";
import "../themes/MovieCard.css";

interface MovieCardProps {
  movie: Movie;
}

const MovieCard = ({ movie }: MovieCardProps) => {
     console.log("Rendering movie:", movie);
  return (
    <Box sx={{ display: "flex", justifyContent: "center", my: 2 }}>
      <Card>
        <CardContent className="card-content">
          <Typography variant="h6" component="h2" className="title">
            {movie.title} ({movie.year})
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Director:</strong> {movie.director}
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Género:</strong> {movie.genre}
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Puntuación:</strong> ⭐ {movie.imdbRating}
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Disponible en:</strong>{" "}
            {Array.isArray(movie.platforms) && movie.platforms.length > 0
              ? movie.platforms.join(", ")
              : movie.platform ?? "No disponible"}
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
};

export default MovieCard;

