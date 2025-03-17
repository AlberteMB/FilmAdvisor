import { Box, Card, CardContent, Typography, Container, Grid } from "@mui/material";


export default function MovieCard({ movie }) {
return (
  <Container maxWidth="md" style={{ marginTop: "20px", maxHeight: "80vh", overflow: "hidden" }}>
    <Box sx={{ my: 4 }}>
      <Grid container justifyContent="center">
        <Grid item xs={12} sm={6} md={4} key={movie.id}>
          {/* Contenedor con control de ancho */}
          <div
            style={{
              minWidth: "250px", // Ancho mínimo de la card
              maxWidth: "350px", // Ancho máximo de la card
              width: "100%",     // La card ocupa todo el espacio disponible, pero dentro del rango
            }}
          >
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
          </div>
        </Grid>
      </Grid>
    </Box>
  </Container>
);
}

