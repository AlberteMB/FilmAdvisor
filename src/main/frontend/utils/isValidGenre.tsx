import { genreList } from "../data/genreList";
import Genre from "Frontend/generated/amb/movie/Genre";

const isValidGenre = (value: string): value is Genre => {
  return genreList.includes(value as Genre);
};

export default isValidGenre;