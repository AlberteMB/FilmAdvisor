export interface Movie {
  platform: string;
  movieId: String;
  title: string;
  director: string;
  year: number;
  releasedDate: Date;
  genres: string[];
  actors: string[];
  synopsis: string;
  ageRating: string;
  imdbId: string;
  imdbRating: number;
  imageUrl: string;
  platforms: string[];
}
