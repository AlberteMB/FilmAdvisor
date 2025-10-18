export interface Movie {
  id: string;
  platform: string;
  movieId: String;
  title: string;
  director: string;
  year: number;
  duration: number;
  releasedDate: Date;
  genre: string;
  actors: string[];
  synopsis: string;
  ageRating: string;
  imdbId: string;
  imdbRating: number;
  imageUrl: string;
  platforms: string[];
}
