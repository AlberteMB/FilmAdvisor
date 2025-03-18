import { Card, CardContent, Typography, Box } from "@mui/material";
import { Movie } from "../models/movie.ts";
import "../themes/MovieCard.css";

interface MovieCardProps {
  movie: Movie;
}

export default function MovieCard({ movie }: MovieCardProps) {
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
            <strong>Género:</strong> {movie.genre.join(", ")}
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Puntuación:</strong> ⭐ {movie.rating}
          </Typography>
          <Typography variant="body2" color="text.secondary" className="text">
            <strong>Disponible en:</strong> {movie.streamingPlatforms.join(", ")}
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
}

