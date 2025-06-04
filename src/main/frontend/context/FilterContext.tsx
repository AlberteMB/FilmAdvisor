import { createContext, useContext, useState, ReactNode } from "react";
//import { Genre, genreList } from "../data/genresList";
import Genre from 'Frontend/generated/amb/movie/Genre';

type FilterState = {
    selectedGenre?: Genre;
  selectedPlatforms: string[];
  setSelectedGenre: (genre?: Genre) => void;
  setSelectedPlatforms: React.Dispatch<React.SetStateAction<string[]>>;
};

const FilterContext = createContext<FilterState | undefined>(undefined);

export const FilterProvider = ({ children }: { children: ReactNode }) => {
  const [selectedGenre, setSelectedGenre] = useState<Genre | undefined>(undefined);
  const [selectedPlatforms, setSelectedPlatforms] = useState<string[]>([]);

  return (
    <FilterContext.Provider
      value={{ selectedGenre, selectedPlatforms, setSelectedGenre, setSelectedPlatforms }}
    >
      {children}
    </FilterContext.Provider>
  );
};

export const useFilterContext = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error("useFilterContext must be used within a FilterProvider");
  }
  return context;
};
