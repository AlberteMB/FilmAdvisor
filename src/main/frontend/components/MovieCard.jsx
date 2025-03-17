import { Box, Card, CardContent, Typography, Container, Grid } from "@mui/material";


export default function MovieCard({ movie }) {
  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4 }}>
        <Grid container justifyContent="center">
          <Grid item xs={12} sm={6}>
            <Card>
              <CardContent>
                <Typography variant="h5" component="h2">
                  {movie.title} ({movie.year})
                  <p><strong>Director:</strong> {movie.director}</p>
                  <p><strong>Género:</strong> {movie.genre.join(", ")}</p>
                  <p><strong>Puntuación:</strong> ⭐ {movie.rating}</p>
                  <p><strong>Disponible en:</strong> {movie.streamingPlatforms.join(", ")}</p>
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
}

