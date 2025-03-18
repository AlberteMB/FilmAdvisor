import { Box, Card, CardContent, Typography, Container, Grid } from "@mui/material";
import { Movie } from "../models/movie";


interface MovieCardProps {
  movie: Movie;
}

export default function MovieCard({ movie }: MovieCardProps) {
  return (
    <Container maxWidth="md" style={{ marginTop: "20px", maxHeight: "80vh", overflow: "hidden" }}>
      <Box sx={{ my: 4 }}>
        <Grid container justifyContent="center">
          <Grid item xs={12} sm={6} md={4} key={movie.id}>
            {/* Contenedor con control de ancho */}
            <div
              style={{
                minWidth: "250px",
                maxWidth: "350px",
                width: "100%",
              }}
            >
              <Card>
                <CardContent>
                  <Typography variant="h5" component="h2">
                    {movie.title} ({movie.year})
                  </Typography>
                  <p><strong>Director:</strong> {movie.director}</p>
                  <p><strong>Género:</strong> {movie.genre.join(", ")}</p>
                  <p><strong>Puntuación:</strong> ⭐ {movie.rating}</p>
                  <p><strong>Disponible en:</strong> {movie.streamingPlatforms.join(", ")}</p>
                </CardContent>
              </Card>
            </div>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
}

